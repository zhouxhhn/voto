package bjl.domain.service.account;


import bjl.application.account.command.*;
import bjl.application.auth.command.LoginCommand;
import bjl.application.shared.command.SharedCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.role.Role;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
public interface IAccountService {

    Pagination<Account> pagination(ListAccountCommand command);

    List<Account> list(ListAccountCommand command);

    Account searchByID(String id);

    Account searchByAccountName(String userName);

    Account searchByName(String name);

    Account updateStatus(SharedCommand command);

    Account resetPassword(ResetPasswordCommand command);

    Account authorized(String id,String role);

    Account login(LoginCommand command);

    boolean updateHead(String url,String username);

    JSONObject modifyName(JSONObject jsonObject);

    JSONObject modifyPassword(JSONObject jsonObject);

    JSONObject modifyBankPwd(JSONObject jsonObject);

    JSONObject checkCode(JSONObject jsonObject);

    JSONObject findPassword(JSONObject jsonObject);

    JSONObject findTwoPwd(JSONObject jsonObject);

    JSONObject playerList(JSONObject jsonObject);

    JSONObject bindSecurity(JSONObject jsonObject);

    String getGameUrl(JSONObject jsonObject);

    Account gag(String id);

    List<Account> list();

}
