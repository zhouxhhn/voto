package bjl.domain.service.bank;


import bjl.application.bank.command.CreateBankCommand;
import bjl.application.bank.command.ListBankCommand;
import bjl.application.bank.representation.ApiBankRepresentation;
import bjl.core.exception.NoFoundException;
import bjl.core.mapping.IMappingService;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.bank.BankDtl;
import bjl.domain.model.bank.IBankRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangjin on 2017/9/6.
 */
@Service("bankService")
public class BankService implements IBankService {

    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IBankRepository<Bank,String> bankRepository;
    @Autowired
    private IMappingService mappingService;

    /**
     * 创建用户银行信息
     * @param command
     */
    @Override
    public JSONObject create(CreateBankCommand command) {

        JSONObject jsonObject = new JSONObject();
        if(!CoreStringUtils.isEmpty(command.getCbid())){
            jsonObject.put("cbid",command.getCbid());
            jsonObject.put("userid",command.getUserid());
        }

        if(CoreStringUtils.isEmpty(command.getUsername()) || CoreStringUtils.isEmpty(command.getCardnum())
                || CoreStringUtils.isEmpty(command.getProvince()) || CoreStringUtils.isEmpty(command.getBankname()) ||
                CoreStringUtils.isEmpty(command.getCity()) || CoreStringUtils.isEmpty(command.getSubbranch())){
            //卡号为空
            jsonObject.put("code",1);
            jsonObject.put("errmsg","信息项不能为空");
            return jsonObject;
        }
        User user = userManagerService.searchByUsername(command.getUserid());
        if(user == null){
            //用户不存在
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        Bank bank = bankRepository.searchByUser(user);

        if(bank != null){
            //用户已绑定银行卡
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户已绑定银行卡");
            return jsonObject;
        }
        bank = bankRepository.searchByNo(command.getCardnum());
        if(bank != null){
            //用户已绑定银行卡
            jsonObject.put("code",1);
            jsonObject.put("errmsg","该银行卡已被绑定");
            return jsonObject;
        }
        bank = new Bank();
        bank.setAccount(user.getAccount());
        bank.setBankAccountName(command.getUsername());
        bank.setBankAccountNo(command.getCardnum());
        bank.setBankAccountType("01");
        bank.setBankCardType("借记卡");
        bank.setBankCity(command.getCity());
        bank.setBankDeposit(command.getSubbranch());
        bank.setBankName(command.getBankname());
        bank.setBankProvince(command.getProvince());
        bank.setCreateDate(new Date());
        bankRepository.save(bank);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","银行卡绑定成功");
        return jsonObject;

    }

    /**
     * 分页条件查询
     * @param command
     * @return
     */
    @Override
    public Pagination<Bank> pagination(ListBankCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if(!CoreStringUtils.isEmpty(command.getUsername())){
            criterionList.add(Restrictions.like("account.name",command.getUsername(), MatchMode.ANYWHERE));
            aliasMap.put("account","account");
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return bankRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    /**
     * 解绑银行卡
     * @param id
     */
    @Override
    public Bank unbound(String id) {
        Bank bank =  bankRepository.getById(id);
        if(bank == null){
            throw new NoFoundException();
        }
        bankRepository.delete(bank);
        return bank;
    }

    /**
     * 获取用户银行信息
     * @param username
     * @return
     */
    @Override
    public Bank info(String username) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if(CoreStringUtils.isEmpty(username)){
            return null;
        }
        criterionList.add(Restrictions.like("account.userName",username, MatchMode.ANYWHERE));
        aliasMap.put("account","account");
        Pagination pagination = bankRepository.pagination(1, 1, criterionList, aliasMap, null, null);
        if(pagination.getCount() != 1){
            return null;
        }
        return (Bank) pagination.getData().get(0);
    }

    /**
     * 获取用户银行信息
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject info(JSONObject jsonObject) {

        String userId = jsonObject.getString("userid");
        if(CoreStringUtils.isEmpty(userId)){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","账号不存在");
            return jsonObject;
        }
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        criterionList.add(Restrictions.eq("account.userName",userId));
        aliasMap.put("account","account");
        List<Bank> list = bankRepository.list(criterionList,null,null,null,aliasMap);
        //返回信息
        User user = userManagerService.searchByUsername(userId);
        jsonObject.put("paysum",user.getScore());
        jsonObject.put("cashsum",user.getScore());
        if(list.size() > 0){
            jsonObject.put("bankcard",1);
            List<ApiBankRepresentation> representations = new ArrayList<>();
            representations.add(mappingService.map(list.get(0), ApiBankRepresentation.class,false));
            jsonObject.put("cards",representations);
        }else {
            jsonObject.put("bankcard",0);
            jsonObject.put("cards",new ArrayList<>());
        }
        jsonObject.put("code",0);
        jsonObject.put("errmsg","查询银行信息成功");

        return jsonObject;
    }

    @Override
    public List<BankDtl> bankDtl(String username) {
        User user = userManagerService.searchByUsername(username);
        if(user == null){
            throw new NoFoundException();
        }
        return bankRepository.getBankDtl(user.getId());
    }
}
