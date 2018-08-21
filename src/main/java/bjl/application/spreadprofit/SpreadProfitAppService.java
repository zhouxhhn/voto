package bjl.application.spreadprofit;

import bjl.application.spreadprofit.command.ListSpreadProfitCommand;
import bjl.application.spreadprofit.command.ProfitDetailedCommand;
import bjl.application.spreadprofit.representation.ApiSpreadProfitRepresentation;
import bjl.application.spreadprofit.representation.SpreadProfitRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.account.Account;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.User;
import bjl.domain.service.spreadprofit.ISpreadProfitService;
import bjl.domain.service.systemconfig.ISystemConfigService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjin on 2018/4/16
 */
@Service("spreadProfitAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SpreadProfitAppService implements ISpreadProfitAppService {

    @Autowired
    private ISpreadProfitService spreadProfitService;
    @Autowired
    private ISystemConfigService systemConfigService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<SpreadProfitRepresentation> pagination(ListSpreadProfitCommand command) {

        command.verifyPage();
        command.setPageSize(18);

        Pagination<SpreadProfit> pagination = spreadProfitService.pagination(command);
        List<SpreadProfitRepresentation> data = mappingService.mapAsList(pagination.getData(),SpreadProfitRepresentation.class);

        return new Pagination<>(data,pagination.getCount(),pagination.getPage(),pagination.getPageSize());
    }

    @Override
    public List<Object[]> report(String username) {
        List<Object[]> list = new ArrayList<>();
        //今日推广人数
        Object[] objects1 = spreadProfitService.spreadSum(1,username);
        list.add(objects1);
        //历史总推广人数
        Object[] objects2 = spreadProfitService.spreadSum(2,username);
        list.add(objects2);
        //今日推广收益
        Object[] objects3 = spreadProfitService.todayProfit(username);
        list.add(objects3);
        //昨日推广收益
        Object[] objects4 = spreadProfitService.yesterdayProfit(username);
        list.add(objects4);
        //本周推广收益
        Object[] objects5 = spreadProfitService.weekProfit(username);
        list.add(objects5);
        //上周推广收益
        Object[] objects6 = spreadProfitService.lastWeekProfit(username);
        list.add(objects6);
        //本月推广收益
        Object[] objects7 = spreadProfitService.monthProfit(username);
        list.add(objects7);

        return list;
    }

    @Override
    public List<ProfitDetailedCommand> profitDetailed(String username) {
        return spreadProfitService.profitDetailed(username);
    }

    @Override
    public JSONObject getSpread(JSONObject jsonObject) {

        SystemConfig systemConfig = systemConfigService.get();
        jsonObject.put("code", 0);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("open",systemConfig.getOpenPump());
        jsonObject1.put("pump_1",systemConfig.getPump_1());
        jsonObject1.put("pump_2",systemConfig.getPump_2());
        jsonObject1.put("pump_3",systemConfig.getPump_3());
        jsonObject1.put("pump_4",systemConfig.getPump_4());
        jsonObject1.put("pump_5",systemConfig.getPump_5());
        jsonObject.put("data",jsonObject1);

        return jsonObject;
    }

    @Override
    public JSONObject getByUsername(JSONObject jsonObject) {
        ApiSpreadProfitRepresentation representation = mappingService.map(spreadProfitService.getByUsername(jsonObject.getString("userid")),ApiSpreadProfitRepresentation.class,false);
        jsonObject.put("code",0);
        jsonObject.put("data",representation);
        return jsonObject;
    }

    @Override
    public SpreadProfit getByAccount(Account account) {
        return spreadProfitService.getByAccount(account);
    }

    @Override
    public void create(Account account) {
        spreadProfitService.create(account);
    }

    @Override
    public void count(Integer type) {
        spreadProfitService.count(type);
    }

    @Override
    public JSONObject receive(JSONObject jsonObject) {

        return spreadProfitService.receive(jsonObject);
    }

    @Override
    public JSONObject spreadSum(Integer type,JSONObject jsonObject) {
        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.spreadSum(type,jsonObject.getString("userid")));

        return jsonObject;
    }

    @Override
    public JSONObject todayProfit(JSONObject jsonObject) {

        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.todayProfit(jsonObject.getString("userid")));

        return jsonObject;
    }

    @Override
    public JSONObject yesterdayProfit(JSONObject jsonObject) {

        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.yesterdayProfit(jsonObject.getString("userid")));

        return jsonObject;
    }

    @Override
    public JSONObject weekProfit(JSONObject jsonObject) {

        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.weekProfit(jsonObject.getString("userid")));
        return jsonObject;
    }

    @Override
    public JSONObject lastWeekProfit(JSONObject jsonObject) {

        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.lastWeekProfit(jsonObject.getString("userid")));

        return jsonObject;
    }

    @Override
    public JSONObject monthProfit(JSONObject jsonObject) {

        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.monthProfit(jsonObject.getString("userid")));

        return jsonObject;
    }

    @Override
    public JSONObject profitDetailed(JSONObject jsonObject) {

        jsonObject.put("code",0);
        jsonObject.put("data",spreadProfitService.profitDetailed(jsonObject.getString("userid")));

        return jsonObject;
    }

}
