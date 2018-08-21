package bjl.application.spreadprofit.representation.mapping;

import bjl.application.spreadprofit.representation.SpreadProfitRepresentation;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.domain.service.spreadprofit.ISpreadProfitService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/5/3
 */
@Component
public class SpreadProfitRepresentationMapper extends CustomMapper<SpreadProfit,SpreadProfitRepresentation> {

    @Autowired
    private ISpreadProfitService spreadProfitService;

    public void mapAtoB(SpreadProfit spreadProfit, SpreadProfitRepresentation representation, MappingContext context) {

        representation.setName(spreadProfit.getAccount().getName());
        representation.setUserId(spreadProfit.getAccount().getUserName());
        //统计有效推广人数
        representation.setEffective(spreadProfitService.effective(spreadProfit.getAccount().getId()));
        //统计总推广人数
        representation.setTotal(spreadProfitService.total(spreadProfit.getAccount().getId()));

    }
}
