package bjl.domain.service.account;


import bjl.application.account.command.AuthorizeAccountCommand;
import bjl.application.account.command.ListAccountCommand;
import bjl.application.account.command.ResetPasswordCommand;
import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.auth.command.LoginCommand;
import bjl.application.logger.ILoggerAppService;
import bjl.application.robot.IRobotAppService;
import bjl.application.robot.representation.ApiRobotRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.core.common.PasswordHelper;
import bjl.core.enums.EnableStatus;
import bjl.core.enums.LoggerType;
import bjl.core.enums.Sex;
import bjl.core.exception.NoFoundException;
import bjl.core.mapping.IMappingService;
import bjl.core.util.CoreHttpUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.account.IAccountRepository;
import bjl.domain.model.robot.Robot;
import bjl.domain.model.role.Role;

import bjl.domain.model.user.User;
import bjl.domain.service.role.IRoleService;

import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by pengyi on 2016/3/30.
 */
@Service("accountService")
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository<Account, String> accountRepository;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAgentConfigAppService agentConfigAppService;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private ILoggerAppService loggerAppService;
    @Autowired
    private IRobotAppService robotAppService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<Account> pagination(ListAccountCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return accountRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public List<Account> list(ListAccountCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return accountRepository.list(criterionList, orderList);
    }

    @Override
    public Account searchByID(String id) {
        Account account = accountRepository.getById(id);
        if (null == account) {
            throw new NoFoundException("没有找到ID[" + id + "]的Account数据");
        }
        return account;
    }

    @Override
    public Account searchByAccountName(String userName) {
        return accountRepository.searchByAccountName(userName);
    }

    @Override
    public Account searchByName(String name) {
        List<Criterion> criterionList = new ArrayList<>();
        if(!CoreStringUtils.isEmpty(name)){
            criterionList.add(Restrictions.like("name",name,MatchMode.ANYWHERE));
        }
        List<Account> list = accountRepository.list(criterionList,null);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Account updateStatus(SharedCommand command) {
        Account account = this.searchByID(command.getId());
        account.fainWhenConcurrencyViolation(command.getVersion());
        if (account.getStatus() == EnableStatus.DISABLE) {
            account.changeStatus(EnableStatus.ENABLE);
        } else {
            account.changeStatus(EnableStatus.DISABLE);
        }
        accountRepository.update(account);
        return account;
    }

    @Override
    public Account resetPassword(ResetPasswordCommand command) {
        Account account = this.searchByID(command.getId());
        String password = PasswordHelper.encryptPassword(command.getPassword(), account.getSalt());
        account.changePassword(password);
        accountRepository.update(account);
        return account;
    }

    @Override
    public Account authorized(String id,String roleName) {
        if(!CoreStringUtils.isEmpty(roleName)){
            Account account = this.searchByID(id);
            if(account != null){
                Role role = roleService.searchByID(roleName);
                if(role != null){
                    //用户权限只能从其他角色到代理，不能逆向
//                    if("user".equals(account.getRoles().get(0).getName())){
//                        //现在授权代理，则添加代理配置
//                        if("firstAgent".equals(role.getName()) || "secondAgent".equals(role.getName())){
//                            agentConfigAppService.create(account,role);
//                        }
//                        List<Role> list = new ArrayList<>();
//                        list.add(role);
//                        account.changeRoles(list);
//                        accountRepository.update(account);
//                    }

                    //首先看用户原来的权限
                    if(!"firstAgent".equals(account.getRoles().get(0).getName()) && !"secondAgent".equals(account.getRoles().get(0).getName())){
                        //如果原来不是代理，现在授权代理，则添加代理配置
                        if("firstAgent".equals(role.getName()) || "secondAgent".equals(role.getName())){
                            agentConfigAppService.create(account,role);
                        }
                        List<Role> list = new ArrayList<>();
                        list.add(role);
                        account.changeRoles(list);
                        accountRepository.update(account);
                        return account;
                    }else {
                        //如果原来是代理，则不做操作
//                        if(!"firstAgent".equals(role.getName()) && !"secondAgent".equals(role.getName())){
//                            //则取消，原来的代理配置，并删除代理绑定
//                            agentConfigAppService.delete(account);
//                            //删除代理绑定
//                            agentAppService.delete(account);
//                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Account login(LoginCommand command) {


        Account account = this.searchByAccountName(command.getUserName());
        if (null == account) {
            throw new UnknownAccountException();
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                command.getUserName(),
                command.getPassword());
        subject.login(token);

        account.changeLastLoginIP(command.getLoginIP());
        account.changeLastLoginPlatform(command.getLoginPlatform());
        account.changeLastLoginDate(new Date());

        accountRepository.update(account);

        return account;
    }

    /**
     * 更新头像
     * @param url 头像地址
     * @param username 用户数字ID
     */
    @Override
    public boolean updateHead(String url, String username) {
        Account account = accountRepository.searchByAccountName(username);
        if (null == account) {
            return false;
        }
        account.changeHead(url);
        accountRepository.update(account);
        return true;
    }

    /**
     * 修改昵称
     * @param jsonObject 用户昵称
     * @return
     */
    @Override
    public JSONObject modifyName(JSONObject jsonObject) {

        Account account = accountRepository.searchByAccountName(jsonObject.getString("userid"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        if(CoreStringUtils.isEmpty(jsonObject.getString("newname")) || jsonObject.getString("newname").length()>6){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","昵称不合规范");
            return jsonObject;
        }
        User user = userManagerService.searchByName(jsonObject.getString("newname"));
        if(user != null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","该昵称已存在");
            return jsonObject;
        }
        account.setName(jsonObject.getString("newname"));
        accountRepository.update(account);
        //日志记录
        loggerAppService.create(account, LoggerType.APP_LOGGER,"修改昵称["+account.getName()+"]成功");

        jsonObject.put("code",0);
        jsonObject.put("errmsg","修改昵称成功");
        jsonObject.put("newname",account.getName());
        return jsonObject;
    }

    /**
     * 修改密码
     * @param jsonObject 请求数据
     * @return
     */
    @Override
    public JSONObject modifyPassword(JSONObject jsonObject) {

        Account account = accountRepository.searchByAccountName(jsonObject.getString("userid"));
        if(account == null ){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        //验证原密码
        if (!PasswordHelper.equalsPassword(account, jsonObject.getString("oldpwd"))) {
            jsonObject.put("code", 1);
            jsonObject.put("errmsg","原密码错误");
            return jsonObject;
        }
        //验证新密码
        String newPassword = jsonObject.getString("newpwd");
        if(CoreStringUtils.isEmpty(newPassword) || !newPassword.matches("[\\x00-\\x7f]{6,15}")){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","新密码不符合规范");
            return jsonObject;
        }

        //设置密码
        String password = PasswordHelper.encryptPassword(newPassword, account.getSalt());
        account.changePassword(password);
        accountRepository.update(account);
        //日志记录
        loggerAppService.create(account, LoggerType.APP_LOGGER,"修改登录密码成功");

        jsonObject.put("code",0);
        jsonObject.put("errmsg","密码修改成功");
        return jsonObject;
    }

    @Override
    public JSONObject modifyBankPwd(JSONObject jsonObject) {

        Account account = accountRepository.searchByAccountName(jsonObject.getString("userid"));
        if(account == null ){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        //验证原密码
        if (!PasswordHelper.equalsBankPwd(account, jsonObject.getString("oldpwd"))) {
            jsonObject.put("code", 1);
            jsonObject.put("errmsg","原密码错误");
            return jsonObject;
        }
        //验证新密码
        String newPassword = jsonObject.getString("newpwd");
        if(CoreStringUtils.isEmpty(newPassword) || !newPassword.matches("[\\x00-\\x7f]{6,15}")){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","新密码不符合规范");
            return jsonObject;
        }

        //设置密码
        String password = PasswordHelper.encryptPassword(newPassword, account.getSalt());
        account.setBankPwd(password);
        accountRepository.update(account);
        //日志记录
        loggerAppService.create(account, LoggerType.APP_LOGGER,"修改保险箱密码成功");
        jsonObject.put("code",0);
        jsonObject.put("errmsg","密码修改成功");
        return jsonObject;
    }

    @Override
    public JSONObject checkCode(JSONObject jsonObject) {

        Account account = accountRepository.searchByAccountName(jsonObject.getString("para"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","检测失败");
        }else {
            jsonObject.put("code",0);
            jsonObject.put("errmsg","检测成功");
        }

        return jsonObject;
    }

    @Override
    public JSONObject findPassword(JSONObject jsonObject) {

        Account account = accountRepository.searchByToken(jsonObject.getString("acc"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        if(jsonObject.getInteger("question") == null || !Objects.equals(jsonObject.getInteger("question"), account.getQuestion())){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","问题错误");
            return jsonObject;
        }
        if(CoreStringUtils.isEmpty(jsonObject.getString("answer")) || !jsonObject.getString("answer").equals(account.getAnswer())){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","答案错误");
            return jsonObject;
        }

        String newPassword = jsonObject.getString("newpwd");
        if(CoreStringUtils.isEmpty(newPassword) || !newPassword.matches("[\\x00-\\x7f]{6,15}")){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","新密码不符合规范");
            return jsonObject;
        }
        //设置密码
        String password = PasswordHelper.encryptPassword(newPassword, account.getSalt());
        account.changePassword(password);
        accountRepository.update(account);
        //日志记录
        loggerAppService.create(account, LoggerType.APP_LOGGER,"找回登录密码成功");

        jsonObject.put("code",0);
        jsonObject.put("errmsg","密码修改成功");

        return jsonObject;
    }

    @Override
    public JSONObject findTwoPwd(JSONObject jsonObject) {

        Account account = accountRepository.searchByAccountName(jsonObject.getString("userid"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        if(jsonObject.getInteger("question") == null || !Objects.equals(jsonObject.getInteger("question"), account.getQuestion())){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","问题错误");
            return jsonObject;
        }
        if(CoreStringUtils.isEmpty(jsonObject.getString("answer")) || !jsonObject.getString("answer").equals(account.getAnswer())){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","答案错误");
            return jsonObject;
        }

        String newPassword = jsonObject.getString("newpwd");
        if(CoreStringUtils.isEmpty(newPassword) || !newPassword.matches("[\\x00-\\x7f]{6,15}")){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","新密码不符合规范");
            return jsonObject;
        }
        //设置密码
        String bankPwd = PasswordHelper.encryptPassword(newPassword, account.getSalt());
        account.setBankPwd(bankPwd);
        accountRepository.update(account);
        //日志记录
        loggerAppService.create(account, LoggerType.APP_LOGGER,"找回保险箱密码成功");
        jsonObject.put("code",0);
        jsonObject.put("errmsg","密码修改成功");

        return jsonObject;
    }

    @Override
    public JSONObject playerList(JSONObject jsonObject) {

        String[] strings = jsonObject.getObject("data",String[].class);
        JSONObject object = new JSONObject();
        if(strings.length >0){
            List<Criterion> criterionList = new ArrayList<>();
            criterionList.add(Restrictions.in("userName",strings));

            List<Account> accounts = accountRepository.list(criterionList,null);
            for(Account account : accounts){
                Map<String,String> map = new HashMap<>();
                map.put("name",account.getName());
                map.put("head",account.getHead());
                object.put(account.getUserName(),map);
            }
        }
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取成功");
        jsonObject.put("data",object);
        //获取机器人
        List<Robot> list = robotAppService.list(jsonObject.getInteger("roomtype"));
        jsonObject.put("data2",mappingService.mapAsList(list, ApiRobotRepresentation.class));

        return jsonObject;
    }

    @Override
    public JSONObject bindSecurity(JSONObject jsonObject) {

        Account account = accountRepository.searchByAccountName(jsonObject.getString("userid"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        if(jsonObject.getInteger("question") == null || jsonObject.getString("answer") == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","密保问题不能为空");
            return jsonObject;
        }
        account.setQuestion(jsonObject.getInteger("question"));
        account.setAnswer(jsonObject.getString("answer"));
        accountRepository.update(account);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","绑定密保问题成功");
        return jsonObject;
    }

    /**
     * 获取游戏URL
     * @param jsonObject
     * @return
     */
    @Override
    public String getGameUrl(JSONObject jsonObject) {

        try {
            String userId = jsonObject.getString("userid");
            Account account = this.searchByAccountName(userId);
            if(account != null){
                if(account.getPlayName() == null){
                    //首先注册用户
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("username","JCWT"+account.getUserName());
                    jsonObject1.put("password",account.getUserName());
                    JSONObject result  = JSONObject.parseObject(CoreHttpUtils.postAuthorization("https://api.gmaster8.com/register",jsonObject1.toString()));

                    if(result.getString("player_name") != null){
                        //激活玩家
                        result  = JSONObject.parseObject(CoreHttpUtils.postAuthorization("https://api.gmaster8.com/IBC/player/active",jsonObject1.toString()));

                        if("success".equals(result.getString("status"))){
                            account.setPlayName(result.getString("playerName"));
                            accountRepository.update(account);
                        }
                    }
                }

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("username",account.getPlayName());
                jsonObject1.put("game_code","h5");
                jsonObject1.put("mobile","Yes");
                JSONObject result  = JSONObject.parseObject(CoreHttpUtils.postAuthorization("https://api.gmaster8.com/IBC/game/open",jsonObject1.toString()));
                System.out.println(result);
                return result.getString("url");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Account gag(String id) {
        Account account = accountRepository.getById(id);
        if(account != null){
            if(account.getGag() != null && account.getGag() == 1){
                //解除禁言
                account.setGag(0);
            }else {
                //禁言
                account.setGag(1);
            }
            return account;
        }
        return null;
    }

    @Override
    public List<Account> list() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateFontAdmin(SharedCommand command) {
        Account account = this.searchByID(command.getId());
        account.fainWhenConcurrencyViolation(command.getVersion());
        if (account.getIsFrontAdmin() == 0) {
            account.setIsFrontAdmin(1);
        } else {
            account.setIsFrontAdmin(0);
        }
        accountRepository.update(account);
        return account;
    }
}
