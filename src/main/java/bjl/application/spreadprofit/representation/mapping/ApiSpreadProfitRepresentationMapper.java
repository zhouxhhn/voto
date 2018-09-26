package bjl.application.spreadprofit.representation.mapping;

import bjl.application.spreadprofit.representation.ApiSpreadProfitRepresentation;
import bjl.core.upload.FileUploadConfig;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.domain.service.spreadprofit.ISpreadProfitService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/4/16
 */
@Component
public class ApiSpreadProfitRepresentationMapper extends CustomMapper<SpreadProfit,ApiSpreadProfitRepresentation>{

    @Autowired
    private ISpreadProfitService spreadProfitService;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    public void mapAtoB(SpreadProfit spreadProfit, ApiSpreadProfitRepresentation representation, MappingContext context) {

        representation.setUserId(spreadProfit.getAccount().getUserName());
        //统计有效推广人数
        representation.setEffective(spreadProfitService.effective(spreadProfit.getAccount().getId()));
        //统计总推广人数
        representation.setTotal(spreadProfitService.total(spreadProfit.getAccount().getId()));
        //
        String fileUrl = "http://www.ying906.com/" + fileUploadConfig.getQRCode()+spreadProfit.getAccount().getUserName()+".png";
        representation.setQRCode(fileUrl);
    }

}
