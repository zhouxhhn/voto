package bjl.application.spreadprofit;

import bjl.application.spreadprofit.command.ListSpreadProfitCommand;
import bjl.application.spreadprofit.command.ProfitDetailedCommand;
import bjl.application.spreadprofit.representation.SpreadProfitRepresentation;
import bjl.domain.model.account.Account;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * Created by zhangjin on 2018/4/16
 */
public interface ISpreadProfitAppService {

    Pagination<SpreadProfitRepresentation> pagination(ListSpreadProfitCommand command);

    List<Object[]> report(String username);

    List<ProfitDetailedCommand> profitDetailed(String username);

    JSONObject getSpread(JSONObject jsonObject);

    JSONObject getByUsername(JSONObject jsonObject);

    SpreadProfit getByAccount(Account account);

    void create(Account account);

    void count(Integer type);

    JSONObject receive(JSONObject jsonObject);

    JSONObject spreadSum(Integer type, JSONObject jsonObject);

    JSONObject todayProfit(JSONObject jsonObject);

    JSONObject yesterdayProfit(JSONObject jsonObject);

    JSONObject weekProfit(JSONObject jsonObject);

    JSONObject lastWeekProfit(JSONObject jsonObject);

    JSONObject monthProfit(JSONObject jsonObject);

    JSONObject profitDetailed(JSONObject jsonObject);

}
