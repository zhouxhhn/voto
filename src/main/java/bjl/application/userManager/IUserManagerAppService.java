package bjl.application.userManager;

import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.userManager.command.CreateUserCommand;
import bjl.application.userManager.command.ListUserCommand;
import bjl.application.userManager.command.ModifyUserCommand;

import bjl.application.userManager.command.RegisterUserCommand;
import bjl.application.userManager.representation.UserManagerRepresentation;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyp on 2017-12-14.
 */
public interface IUserManagerAppService {
    List<User> list(ListUserCommand command);

   void create(CreateUserCommand command);

    JSONObject register(RegisterUserCommand command);

    JSONObject login(RegisterUserCommand command);

    JSONObject info(JSONObject jsonObject);

    void delete(User user);

    void update(ModifyUserCommand command);

    void updateALL(ModifyUserCommand command);

    Pagination<UserManagerRepresentation> pagination(ListUserCommand command);

    Object[] sum(ListUserCommand command);

    User setTop(String userId);

    User setVirtual(String id);

    User setPrintScreen(String id);

    User update(User user);

    User searchByAccount(Account account);

    User searchByUsername(String username);

    List<User> listAll();

    User changeScore(String id, BigDecimal score,Integer type);

    User getById(String id);
}
