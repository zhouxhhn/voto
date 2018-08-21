package bjl.domain.service.spreadprofit;

import bjl.application.spreadprofit.command.ListSpreadProfitCommand;
import bjl.application.spreadprofit.command.ProfitDetailedCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/4/16
 */
public interface ISpreadProfitService {

    Pagination<SpreadProfit> pagination(ListSpreadProfitCommand command);

    SpreadProfit create(Account account);

    SpreadProfit getByUsername(String username);

    SpreadProfit getByAccount(Account account);

    int effective(String userId);

    int total(String userId);

    void count(Integer type);

    JSONObject receive(JSONObject jsonObject);

    Object[] spreadSum(Integer type, String username);

    Object[] todayProfit(String username);

    Object[] yesterdayProfit(String username);

    Object[] weekProfit(String username);

    Object[] lastWeekProfit(String username);

    Object[] monthProfit(String username);

    List<ProfitDetailedCommand> profitDetailed(String username);
}
