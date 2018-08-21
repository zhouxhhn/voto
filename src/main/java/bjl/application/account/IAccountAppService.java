package bjl.application.account;


import bjl.application.account.command.*;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.auth.command.LoginCommand;
import bjl.application.shared.command.SharedCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.role.Role;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
public interface IAccountAppService {

    Pagination<Account> pagination(ListAccountCommand command);

    List<AccountRepresentation> list(ListAccountCommand command);

    AccountRepresentation searchByID(String id);

    AccountRepresentation searchByAccountName(String userName);

    Account updateStatus(SharedCommand command);

    Account resetPassword(ResetPasswordCommand command);

    Account authorized(String id,String role);

    AccountRepresentation login(LoginCommand command);

    Pagination<AccountRepresentation> paginationJSON(ListAccountCommand command);

    boolean updateHead(String url,String username);

    Account searchByUsername(String username);

    Account searchByName(String name);

    JSONObject modifyName(JSONObject jsonObject);

    JSONObject modifyPassword(JSONObject jsonObject);

    JSONObject modifyBankPwd(JSONObject jsonObject);

    JSONObject checkCode(JSONObject jsonObject);

    JSONObject findPassword(JSONObject jsonObject);

    JSONObject findTwoPwd(JSONObject jsonObject);

    JSONObject playerList(JSONObject jsonObject);

    JSONObject getGameUrl(JSONObject jsonObject);

    JSONObject isBindSecurity(JSONObject jsonObject);

    JSONObject bindSecurity(JSONObject jsonObject);

    Account gag(String id);

    List<Account> listAll();

}
