package bjl.application.account;

import bjl.application.account.command.*;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.auth.command.LoginCommand;
import bjl.application.shared.command.SharedCommand;
import bjl.core.enums.EnableStatus;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.account.Account;
import bjl.domain.model.permission.Permission;
import bjl.domain.model.role.Role;
import bjl.domain.service.account.IAccountService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
@Service("accountAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AccountAppService implements IAccountAppService {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMappingService mappingService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<Account> pagination(ListAccountCommand command) {
        command.verifyPage();
        command.verifyPageSize(18);
        Pagination<Account> pagination = accountService.pagination(command);
//        List<AccountRepresentation> data = mappingService.mapAsList(pagination.getData(), AccountRepresentation.class);

        return pagination;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountRepresentation> list(ListAccountCommand command) {
        return mappingService.mapAsList(accountService.list(command), AccountRepresentation.class);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountRepresentation searchByID(String id) {
        return mappingService.map(accountService.searchByID(id), AccountRepresentation.class, false);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountRepresentation searchByAccountName(String userName) {

        Account account = accountService.searchByAccountName(userName);
        AccountRepresentation representation = mappingService.map(account, AccountRepresentation.class, false);
        if (null == representation.getRoles()) {
            representation.setRoles(new ArrayList<>());
        } else {
            representation.getRoles().clear();
        }
        if (null == representation.getPermissions()) {
            representation.setPermissions(new ArrayList<>());
        } else {
            representation.getPermissions().clear();
        }
        if (null != account.getRoles()) {
            for (Role role : account.getRoles()) {
                representation.getRoles().add(role.getName());
                if (role.getStatus().compareTo(EnableStatus.ENABLE) == 0) {
                    for (Permission permission : role.getPermissions()) {
                        if (permission.getStatus().compareTo(EnableStatus.ENABLE) == 0) {
                            representation.getPermissions().add(permission.getName());
                        }
                    }
                }
            }
        }
        return representation;
    }

    @Override
    public Account updateStatus(SharedCommand command) {
        return accountService.updateStatus(command);
    }

    @Override
    public Account updateFontAdmin(SharedCommand command) {
        return accountService.updateFontAdmin(command);
    }

    @Override
    public Account resetPassword(ResetPasswordCommand command) {
        return accountService.resetPassword(command);
    }

    @Override
    public Account authorized(String id,String role) {
        return accountService.authorized(id,role);
    }

    @Override
    public AccountRepresentation login(LoginCommand command) {
        return mappingService.map(accountService.login(command), AccountRepresentation.class, false);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<AccountRepresentation> paginationJSON(ListAccountCommand command) {
        command.verifyPage();
        command.setUserName(command.getAccountUserName());
        Pagination<Account> pagination = accountService.pagination(command);
        List<AccountRepresentation> data = mappingService.mapAsList(pagination.getData(), AccountRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public boolean updateHead(String url, String username) {
        return accountService.updateHead(url,username);
    }

    @Override
    public Account searchByUsername(String username) {
        return accountService.searchByAccountName(username);
    }

    @Override
    public Account searchByName(String name) {
        return accountService.searchByName(name);
    }

    @Override
    public JSONObject modifyName(JSONObject jsonObject) {
        return accountService.modifyName(jsonObject);
    }

    @Override
    public JSONObject modifyPassword(JSONObject jsonObject) {
        return accountService.modifyPassword(jsonObject);
    }

    @Override
    public JSONObject modifyBankPwd(JSONObject jsonObject) {
        return accountService.modifyBankPwd(jsonObject);
    }

    @Override
    public JSONObject checkCode(JSONObject jsonObject) {
        return accountService.checkCode(jsonObject);
    }

    @Override
    public JSONObject findPassword(JSONObject jsonObject) {
        return accountService.findPassword(jsonObject);
    }

    @Override
    public JSONObject findTwoPwd(JSONObject jsonObject) {
        return accountService.findTwoPwd(jsonObject);
    }

    @Override
    public JSONObject playerList(JSONObject jsonObject) {
        return accountService.playerList(jsonObject);
    }

    @Override
    public JSONObject getGameUrl(JSONObject jsonObject) {

        String url = accountService.getGameUrl(jsonObject);
        if(url != null){
            jsonObject.put("code",0);
            jsonObject.put("data",url);
            jsonObject.put("errmsg","获取游戏链接成功");
        }else {
            jsonObject.put("code",1);
            jsonObject.put("errmsg","获取游戏链接失败");
        }

        return jsonObject;
    }

    @Override
    public JSONObject isBindSecurity(JSONObject jsonObject) {

        Account account = accountService.searchByAccountName(jsonObject.getString("userid"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        if(account.getQuestion() != null && account.getAnswer() != null){
            jsonObject.put("code",0);
            jsonObject.put("status",1);
            jsonObject.put("errmsg","已绑定");
        }else {
            jsonObject.put("code",0);
            jsonObject.put("status",0);
            jsonObject.put("errmsg","未绑定");
        }
        return jsonObject;
    }

    @Override
    public JSONObject bindSecurity(JSONObject jsonObject) {

        return accountService.bindSecurity(jsonObject);
    }

    @Override
    public Account gag(String id) {
        return accountService.gag(id);
    }

    @Override
    public List<Account> listAll() {
        return accountService.list();
    }

}
