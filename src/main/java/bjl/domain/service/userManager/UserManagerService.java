package bjl.domain.service.userManager;


import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.IAgentAppService;
import bjl.application.ip.IIpAppService;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.systemconfig.ISystemConfigAppService;
import bjl.application.upDownPoint.IUpDownPointAppService;
import bjl.application.userManager.command.*;
import bjl.core.api.MessageID;
import bjl.core.common.PasswordHelper;
import bjl.core.enums.EnableStatus;
import bjl.core.enums.LoggerType;
import bjl.core.enums.Sex;
import bjl.core.exception.NoFoundException;
import bjl.core.message.PushMessage;
import bjl.core.upload.FileUploadConfig;
import bjl.core.upload.IFileUploadService;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreHttpUtils;
import bjl.core.util.CoreStringUtils;
import bjl.core.util.QRCodeUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.account.IAccountRepository;
import bjl.domain.model.ip.Ip;
import bjl.domain.model.role.Role;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.upDownPoint.IUpDownPointRepository;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.ip.IIpService;
import bjl.domain.service.ratio.IRatioService;
import bjl.domain.service.role.IRoleService;
import bjl.domain.service.spreadprofit.ISpreadProfitService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.WSMessage;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dyp on 2017-12-14.
 */
@Service("userManagerService")
public class UserManagerService implements IUserManagerService {

    @Autowired
    private IAccountRepository<Account,String> accountRepository;
    @Autowired
    private IUserRepository<User,String> userRepository;
    @Autowired
    private IUpDownPointRepository<UpDownPoint,String> upDownPointRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private FileUploadConfig fileUploadConfig;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private ISystemConfigAppService systemConfigAppService;
    @Autowired
    private ILoggerAppService loggerAppService;
    @Autowired
    private IRatioService ratioService;
    @Autowired
    private ISpreadProfitService spreadProfitService;
    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private IIpService iIpService;

    @Override
    public void create(CreateUserCommand command) {

        //先存ACCOUNT
        //设置密码
        String salt = PasswordHelper.getSalt();
        String password = PasswordHelper.encryptPassword("123456", salt);
        String bankPwd= PasswordHelper.encryptPassword("123456", salt);
        //角色权限
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleService.searchByName("user"));

        //设置userName
        String userName = null;
        while (userName == null) {
            Random random = new Random();
            int number = random.nextInt(89999999) + 10000000;
            if (null == accountRepository.searchByAccountName("" + number)) {
                userName = "" + number;
            }
        }

        //设置昵称 ,生成带6位随机数字的昵称
        String name = null;
        while (name == null){
            Random random = new Random();
            String name_ = "JC"+(random.nextInt(899999) + 100000);
            if (null == this.searchByName(name_)) {
                name = name_;
            }
        }
        //设置用户默认头像
        String head = fileUploadConfig.getDomainName()+fileUploadConfig.getFolder()+(new Random().nextInt(6)+1)+".png";
        //获取系统默认配置
        SystemConfig systemConfig = systemConfigAppService.get();

        Account account = new Account(userName,head,userName,password,bankPwd,salt,null,null,null,roleList,EnableStatus.ENABLE, Sex.MAN, name);
        account.setR(systemConfig.getPlayerR());
        account.setRatio(BigDecimal.valueOf(0));
        account.setQuestion(9);
        account.setAnswer("123456");
        accountRepository.save(account);

        //再存USER
        User user=new User();
        //创建资金账户
        user.setAccount(account);

        user.setPlayerAlias(command.getPlayerAlias());
        user.setAgentAlias(command.getAgentAlias());
        //额度、占比
        BigDecimal bigDecimal = BigDecimal.valueOf(0);
        user.setBankerPlayerProportion(bigDecimal);
        user.setBankerPlayerCredit(bigDecimal);
        user.setTriratnaProportion(bigDecimal);
        user.setTriratnaCredit(bigDecimal);
        user.setBankerPlayerMix(systemConfig.getBankerPlayerMix());
        user.setBankerPlayerMax(systemConfig.getBankerPlayerMax());
        user.setTriratnaMix(systemConfig.getTriratnaMix());
        user.setTriratnaMax(systemConfig.getTriratnaMax());
        user.setCreateDate(new Date());
        //积分
        user.setBankScore(bigDecimal);
        user.setScore(bigDecimal);
        user.setPrimeScore(bigDecimal);
        user.setDateScore(bigDecimal);
        user.setTotalScore(bigDecimal);

        user.setVirtual(2);
        user.setSetTop(2);
        user.setPrintScreen(1);
        //获取最大序号
        Integer serial = userRepository.getMaxSerial();
        if(serial == null){
            serial = 0;
        }else {
            serial++;
        }
        user.setSerial(serial);
        //生成二维码,以username.png 为图片名称
        QRCodeUtils.createQRCode(account.getUserName(),fileUploadConfig);
        //创建推广收益
        spreadProfitService.create(account);

        userRepository.save(user);
    }

    /**
     * 用户注册
     * @param command
     */
    @Override
    public JSONObject register(RegisterUserCommand command) {

        JSONObject jsonObject = new JSONObject();
        if(command.getCbid() != null){
            jsonObject.put("cbid",command.getCbid());
        }
        Object[] code = GlobalVariable.getCodeMap().get(command.getFlag());

        if(code == null || System.currentTimeMillis()-(long)code[1] >120000){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","验证码已过期");
            return jsonObject;
        }

        if(CoreStringUtils.isEmpty(command.getIcode()) || !command.getIcode().toUpperCase().equals(code[0])){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","验证码错误");
            return jsonObject;
        }

        if(CoreStringUtils.isEmpty(command.getAcc()) || command.getAcc().getBytes().length>15){
            //由6-20位数字或这字母组成
            //账号不和规范
            jsonObject.put("code",1);
            jsonObject.put("errmsg","账号不符合规范");
            return jsonObject;
        }

        if(accountRepository.searchByToken(command.getAcc()) != null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","该账号已存在");
            return jsonObject;
        }

        if(CoreStringUtils.isEmpty(command.getPwd()) || !command.getPwd().matches("[\\x00-\\x7f]{6,15}")){
            //密码不合规范

            jsonObject.put("code",1);
            jsonObject.put("errmsg","密码不符合规范");
            return jsonObject;
        }

        if(!CoreStringUtils.isEmpty(command.getPara())){
            Account accountP = accountRepository.searchByAccountName(command.getPara());
            //不存在或者不是代理
            if(accountP == null || (!accountP.getRoles().get(0).getName().equals("firstAgent") && !accountP.getRoles().get(0).getName().equals("secondAgent"))){
                jsonObject.put("code",1);
                jsonObject.put("errmsg","该代理不存在");
                return jsonObject;
            }

        }
//        if(command.getQuestion() == null || CoreStringUtils.isEmpty(command.getAnswer())){
//            //密保问题不能为空
//            jsonObject.put("code",1);
//            jsonObject.put("errmsg","密保问题不能为空");
//            return jsonObject;
//        }

        //先存ACCOUNT
        //设置密码
        String salt = PasswordHelper.getSalt();
        String password = PasswordHelper.encryptPassword(command.getPwd(), salt);
        String bankPwd= PasswordHelper.encryptPassword(command.getPwd(), salt);
        //角色权限
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleService.searchByName("user"));

        //设置userName
        String userName = null;
        while (userName == null) {
            Random random = new Random();
            int number = random.nextInt(89999999) + 10000000;
            if (null == accountRepository.searchByAccountName("" + number)) {
                userName = "" + number;
            }
        }

        //设置昵称
        String name  = "BY"+(int)((Math.random()*9+1)*100000);//生成带6位随机数字的昵称
        //设置用户默认头像
        String head = fileUploadConfig.getDomainName()+fileUploadConfig.getFolder()+(new Random().nextInt(6)+1)+".png";
        //获取系统默认配置
        SystemConfig systemConfig = systemConfigAppService.get();
        Account account = new Account(command.getAcc(),head,userName,password,bankPwd,salt,null,null,null,roleList,EnableStatus.ENABLE, Sex.MAN, name);
        account.setR(systemConfig.getPlayerR());
        account.setRatio(BigDecimal.valueOf(0));
        account.setQuestion(command.getQuestion());
        account.setAnswer(command.getAnswer());
        account.setMail(command.getMail());
        accountRepository.save(account);

        //再存USER
        User user=new User();

        user.setAccount(account);
        //获取系统默认配置
        BigDecimal decimal = BigDecimal.valueOf(0);
        user.setBankerPlayerProportion(decimal);
        user.setBankerPlayerCredit(decimal);
        user.setTriratnaProportion(decimal);
        user.setTriratnaCredit(decimal);
        user.setBankerPlayerMix(systemConfig.getBankerPlayerMix());
        user.setBankerPlayerMax(systemConfig.getBankerPlayerMax());
        user.setTriratnaMix(systemConfig.getTriratnaMix());
        user.setTriratnaMax(systemConfig.getTriratnaMax());

        user.setCreateDate(new Date());
        //积分
        user.setBankScore(decimal);
        user.setScore(decimal);
        user.setPrimeScore(decimal);
        user.setDateScore(decimal);
        user.setTotalScore(decimal);

        user.setVirtual(2);
        user.setSetTop(2);
        user.setPrintScreen(1);
        //获取最大序号
        Integer serial = userRepository.getMaxSerial();
        if(serial == null){
            serial = 0;
        }else {
            serial++;
        }
        user.setSerial(serial);
        userRepository.save(user);

        Account accountP = null;
        if (!CoreStringUtils.isEmpty(command.getPara())) {
            accountP = accountRepository.searchByAccountName(command.getPara());
        }else {
            //获取IP对应关系
            Ip ip = iIpService.getIp(command.getIp());
            if(ip != null){
                User user1 = userRepository.getById(ip.getParentId());
                if(user1 != null){
                    accountP = user1.getAccount();
                    //绑定推荐人
                    account.setReferee(accountP);
                    accountRepository.update(account);
                }
            }
        }
        //是否绑定代理
        if (accountP != null) {
            Role role = accountP.getRoles().get(0);
            if (role.getName().equals("firstAgent") || role.getName().equals("secondAgent")) {
                //绑定代理关系
                agentAppService.create(account, accountP);
                //创建收益表
                ratioService.create(account);
            } else if (role.getName().equals("user")) {
                //如果推荐人是玩家，则绑定其上级代理
                Account parent = agentAppService.getAgent(accountP);
                if (parent != null) {
                    if (parent.getRoles().get(0).getName().equals("firstAgent") || parent.getRoles().get(0).getName().equals("secondAgent")) {
                        //绑定代理关系
                        agentAppService.create(account, parent);
                        //创建收益表
                        ratioService.create(account);
                    }
                }
            }
            //绑定收益代理
            account.setParent(accountP);
        }

        //生成二维码,以username.png 为图片名称
        QRCodeUtils.createQRCode(account.getUserName(),fileUploadConfig);
        //创建推广收益
        spreadProfitService.create(account);
        //清除验证码
        fileUploadService.deleteCode(command.getFlag());

        jsonObject.put("code",0);
        jsonObject.put("errmsg","注册成功");
        return jsonObject;
    }

    /**
     * 用户登录
     * @return
     */
    @Override
    public JSONObject login(RegisterUserCommand command) {

        JSONObject jsonObject = new JSONObject();
        if(command.getCbid() != null){
            jsonObject.put("cbid",command.getCbid());
        }

        Account account = accountRepository.searchByToken(command.getAcc());
        if(account == null){
            //账号不存在
            jsonObject.put("code",1);
            jsonObject.put("errmsg","该账号不存在");
            return jsonObject;
        }

        if(account.getStatus() == EnableStatus.DISABLE){
            //账号被禁用
            jsonObject.put("code",1);
            jsonObject.put("errmsg","该账号已被禁用");
            return jsonObject;
        }

//        Role role = account.getRoles().get(0);

//        if("firstAgent".equals(role.getName()) || "secondAgent".equals(role.getName())){
//            //代理不能登录游戏
//            jsonObject.put("code",1);
//            jsonObject.put("errmsg","无登录权限");
//            return jsonObject;
//        }

        //验证密码
        if (!PasswordHelper.equalsPassword(account, command.getPassword())) {
            jsonObject.put("code", 1);
            jsonObject.put("errmsg","密码错误");
            return jsonObject;
        }

        User user = userRepository.searchByAccount(account);
        if(user == null){
            jsonObject.put("code", 1);
            jsonObject.put("errmsg","数据异常");
            return jsonObject;
        }
        //返回登录信息
        if("filipino".equals(account.getRoles().get(0).getName()) ||
                "vietnam".equals(account.getRoles().get(0).getName()) ||
                "macao".equals(account.getRoles().get(0).getName())){
            account.changeHead(fileUploadConfig.getDomainName()+fileUploadConfig.getFolder()+"1.png");
        }
        accountRepository.update(account);
        //日志记录
        loggerAppService.create(account,LoggerType.APP_LOGGER,"用户登录成功");

        System.out.println("isFrontAdmin="+account.getIsFrontAdmin());
        jsonObject.put("code",0);
        jsonObject.put("errmsg","登录成功");
        jsonObject.put("userid",account.getUserName());
        jsonObject.put("head",account.getHead());
        jsonObject.put("gold",user.getScore());
        jsonObject.put("name",account.getName());
        jsonObject.put("isFrontAdmin",account.getIsFrontAdmin());
        jsonObject.put("roles",account.getRoles());
        return jsonObject;
    }

    /**
     * 个人信息
     * @return
     */
    @Override
    public JSONObject info(JSONObject jsonObject) {

        User user = this.searchByUsername(jsonObject.getString("userid"));
        if(user == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
        }else {
            jsonObject.put("head",user.getAccount().getHead());
            jsonObject.put("isFrontAdmin",user.getAccount().getIsFrontAdmin());
            jsonObject.put("gold",user.getScore());
            jsonObject.put("name",user.getAccount().getName());
            jsonObject.put("qrcode",fileUploadConfig.getDomainName()+fileUploadConfig.getQRCode()+user.getAccount().getUserName()+".png");
            jsonObject.put("spread",fileUploadConfig.getDomainName()+"spread/"+user.getId());
            jsonObject.put("code",0);
        }
        return jsonObject;
    }


    public User searchByID(String id) {
        User user = userRepository.getById(id);
        if (null == user) {
            throw new NoFoundException("没有找到ID[" + id + "]的玩家");
        }
        return user;
    }
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> list(ListUserCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("serial"));
        return userRepository.list(criterionList, orderList);
    }

    @Override
    public void update(ModifyUserCommand command) {

        User user = this.searchByID(command.getId());
        // 玩家、代理
        user.setPlayerAlias(command.getPlayerAlias());

        user.setAgentAlias(command.getAgentAlias());

//        //上下分
//        if(command.getUpDownPoint() != null && command.getUpDownPoint().compareTo(BigDecimal.valueOf(0)) != 0) {
//            if(user.getScore().add(command.getUpDownPoint()).compareTo(BigDecimal.valueOf(0)) < 0){
//                command.setUpDownPoint(BigDecimal.valueOf(-1).multiply(user.getScore()));
//            }
//
//            user.setScore(user.getScore().add(command.getUpDownPoint()));
//            user.setPrimeScore(user.getPrimeScore().add(command.getUpDownPoint()));
//            if(user.getVirtual() !=1){
//                upDownPointAppService.create(command);
//            }
//
//        }

        //额度、占比
        if(command.getBankerPlayerCredit()!=null&&!CoreStringUtils.isEmpty(command.getBankerPlayerCredit()+"")){
            user.setBankerPlayerCredit(command.getBankerPlayerCredit());
        }
        if(command.getBankerPlayerProportion()!=null){
            user.setBankerPlayerProportion(command.getBankerPlayerProportion());
        }

        if(command.getTriratnaProportion()!=null){
            user.setTriratnaProportion(command.getTriratnaProportion());
        }
        if(command.getTriratnaCredit()!=null){
            user.setTriratnaCredit(command.getTriratnaCredit());
        }
        if(command.getBankerPlayerMix()!=null){
            user.setBankerPlayerMix(command.getBankerPlayerMix());
        }
        if(command.getBankerPlayerMax()!=null){
            user.setBankerPlayerMax(command.getBankerPlayerMax());
        }
        if(command.getTriratnaMix()!=null){
            user.setTriratnaMix(command.getTriratnaMix());
        }
        if(command.getTriratnaMax()!=null){
            user.setTriratnaMax(command.getTriratnaMax());
        }
        userRepository.update(user);
    }


    public void listUpdate(ModifyUserCommand command) {
        List<User> list = userRepository.findAll();
        for (User user: list) {
            if(command.getBankerPlayerCredit()!=null){
                user.setBankerPlayerCredit(command.getBankerPlayerCredit());
            }
            if(command.getBankerPlayerProportion()!=null){
                user.setBankerPlayerProportion(command.getBankerPlayerProportion());
            }
            if(command.getTriratnaProportion()!=null){
                user.setTriratnaProportion(command.getTriratnaProportion());
            }
            if(command.getTriratnaCredit()!=null){
                user.setTriratnaCredit(command.getTriratnaCredit());
            }
            if(command.getBankerPlayerMix()!=null){
                user.setBankerPlayerMix(command.getBankerPlayerMix());
            }
            if(command.getBankerPlayerMax()!=null){
                user.setBankerPlayerMax(command.getBankerPlayerMax());
            }
            if(command.getTriratnaMix()!=null){
                user.setTriratnaMix(command.getTriratnaMix());
            }
            if(command.getTriratnaMax()!=null){
                user.setTriratnaMax(command.getTriratnaMax());
            }
            userRepository.update(user);
        }
    }

    @Override
    public User searchByUsername(String username) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(username)) {
            criterionList.add(Restrictions.eq("account.userName", username));
        }
        aliasMap.put("account","account");
        List<User> list = userRepository.list(criterionList,null,null,null,aliasMap);

        return list.size() <1 ? null : list.get(0);
    }

    public Pagination<User> pagination(ListUserCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            aliasMap.put("account","account");
            criterionList.add(Restrictions.like("account.name",command.getUserName(),MatchMode.ANYWHERE));
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }

        List<Order> orderList = new ArrayList<>();
            orderList.add(Order.desc("serial"));
        return userRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public Object[] sum(ListUserCommand command) {
        return userRepository.sum(criteria(command));
    }


    private List<Criterion> criteria(ListUserCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if(!CoreStringUtils.isEmpty(command.getUserName())){
            criterionList.add(Restrictions.like("playerAlias",command.getUserName(),MatchMode.ANYWHERE));
        }
        if(command.getAccount() != null && !"".equals(command.getAccount())){
            criterionList.add(Restrictions.eq("account",command.getAccount()));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.lt("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }

        return criterionList;
    }

    public List<User> list(String[] strings) {
        List<Criterion> criterionList=new ArrayList<>();
        criterionList.add(Restrictions.eq("playerAlias",strings[2]));
        criterionList.add(Restrictions.eq("account",strings[3]));
        //开始时间
        Date start = CoreDateUtils.parseDate(strings[2],"yy-MM-dd");
        //结束时间
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);
        Date end=calendar.getTime();

        criterionList.add(Restrictions.ge("createDate",start));
        criterionList.add(Restrictions.lt("createDate",end));

        List<Order> orderList=new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return userRepository.list(criterionList,orderList);
    }

    /**
     * 置顶玩家
     * @param userId
     */
    @Override
    public User setTop(String userId) {
        User user = userRepository.getById(userId);
        if(user != null){
            Integer serial = userRepository.getMaxSerial();
            user.setSerial(++serial);
            userRepository.update(user);
            return user;
        }
        return null;
    }

    @Override
    public User searchByName(String name) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(name)) {
            criterionList.add(Restrictions.like("account.name", name,MatchMode.ANYWHERE));
        }
        aliasMap.put("account","account");
        List<User> list = userRepository.list(criterionList,null,null,null,aliasMap);

        return list.size() <1 ? null : list.get(0);
    }

    @Override
    public User setVirtual(String id) {

        User user = userRepository.getById(id);
        if(user != null){
            if(user.getVirtual() == 1){
                user.setVirtual(2);
            } else {
                user.setVirtual(1);
            }
            userRepository.update(user);
            return user;
        }
        return null;
    }

    @Override
    public User setPrintScreen(String id) {
        User user = userRepository.getById(id);
        if(user != null){
            if(user.getPrintScreen() == 1){
                user.setPrintScreen(2);
            }else {
                user.setPrintScreen(1);
            }
            userRepository.update(user);
            return user;
        }
        return null;
    }

    @Override
    public User update(User user) {
        userRepository.update(user);
        return user;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User searchByAccount(Account account) {
        return userRepository.searchByAccount(account);
    }

    @Override
    public User changeScore(String id, BigDecimal score, Integer type) {

        if(score == null || score.compareTo(BigDecimal.valueOf(0)) <=0){
            return null;
        }
        User user = userRepository.getById(id);

        if(user != null){

            if(user.getVirtual() != 1){
                //不是虚拟玩家
                AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

                UpDownPoint upDownPoint=new UpDownPoint();
                upDownPoint.setCreateDate(new Date());
                upDownPoint.setOperationUser(sessionUser.getUserName());
                upDownPoint.setUserName(user.getAccount().getUserName());
                upDownPoint.setName(user.getAccount().getName());
                if(type == 1){  //上分
                    upDownPoint.setUpDownPointType(1);
                    upDownPoint.setUpDownPoint(score);

                }else {  //下分
                    if(user.getScore().compareTo(score) < 0){
                        score = user.getScore();
                    }
                    upDownPoint.setUpDownPointType(2);
                    upDownPoint.setUpDownPoint(BigDecimal.valueOf(-1).multiply(score));
                }
                //则创建上下分记录
                upDownPointRepository.save(upDownPoint);

            }

            if(type == 1){
                // 上分
                user.setScore(user.getScore().add(score));
                user.setPrimeScore(user.getPrimeScore().add(score));
            }else {
                //下分
                user.setScore(user.getScore().subtract(score));
                user.setPrimeScore(user.getPrimeScore().subtract(score));
            }

            //更新用户积分
            userRepository.update(user);


            //推送玩家积分
            List<Map<String, Object>> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            Map<String, Object> map = new HashMap<>();
            map.put("userid", user.getAccount().getUserName());
            map.put("gold", user.getScore());
            list.add(map);
            jsonObject.put("data", list);
            PushMessage.ordinaryPush(jsonObject, MessageID.PUSHGOLD_);
            return user;
        }
        return null;
    }

    @Override
    public User getById(String id) {
        return userRepository.getById(id);
    }


    @Override

    public List<User> apiList(String[] strings) {

        List<Criterion> criterionList = new ArrayList<>();

        criterionList.add(Restrictions.eq("playerAlias",strings[2]));

        criterionList.add(Restrictions.eq("account",strings[3]));

        Date date = CoreDateUtils.parseDate(strings[0], "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());   //设置当前日期
        c.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        Date after = c.getTime(); //结果

        criterionList.add(Restrictions.ge("createDate",date));
        criterionList.add(Restrictions.lt("createDate",after));
        return userRepository.list(criterionList,null);
    }

}
