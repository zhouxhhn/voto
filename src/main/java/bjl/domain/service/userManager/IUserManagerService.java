package bjl.domain.service.userManager;



import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.userManager.command.*;

import bjl.domain.model.account.Account;
import bjl.domain.model.financialSummary.FinancialSummary;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyp on 2017-12-14.
 */

public interface IUserManagerService {

    void create(CreateUserCommand user);

    JSONObject register(RegisterUserCommand command);

    JSONObject login(RegisterUserCommand command);

    JSONObject info(JSONObject jsonObject);

    void delete(User user);

    List<User> list(ListUserCommand command);

    void update(ModifyUserCommand user);

    void listUpdate(ModifyUserCommand command);

    User searchByUsername(String username);

    Pagination<User> pagination(ListUserCommand command);

    Object[] sum(ListUserCommand command);

    List<User> apiList(String[] strings);

    List<User> list(String[] strings);

    User setTop(String user);

    User searchByName(String name);

    User setVirtual(String id);

    User setPrintScreen(String id);

    User update(User user);

    List<User> listAll();

    User searchByAccount(Account account);

    User changeScore(String id, BigDecimal score, Integer type);

    User getById(String id);
}
