package bjl.application.phonebet;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.phonebet.command.BetData;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.application.phonebet.command.ListPhoneBetCommand;
import bjl.application.phonebet.representation.PhoneBetRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.phonebet.PhoneBet;
import bjl.domain.service.phonebet.IPhoneBetService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("phoneBetAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PhoneBetAppService implements IPhoneBetAppService{


    @Autowired
    private IPhoneBetService phoneBetService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public JSONObject getBetData(JSONObject jsonObject) {

        if(CoreStringUtils.isEmpty(jsonObject.getString("userid"))){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        jsonObject.put("code",0);

        PhoneBet phoneBet = phoneBetService.searchByUsername(jsonObject.getString("userid"));

        if(phoneBet != null){
            if(phoneBet.getStatus() == 1){
                jsonObject.put("errmsg","等待客服服务中....");
            }else if(phoneBet.getStatus() == 2){
                jsonObject.put("errmsg","客服"+phoneBet.getJobNum()+"正在为您服务中....");
            }
            jsonObject.put("isbet",1);
            jsonObject.put("configs",new ArrayList<>());
        }else {
            jsonObject.put("errmsg","请求下注数据成功");
            BetData betData = new BetData(1,"卡卡湾一楼");
            BetData betData1 = new BetData(2,"卡卡湾二楼");
            BetData betData2 = new BetData(3,"卡卡湾三楼");
            List<BetData> list = new ArrayList<>();
            list.add(betData);
            list.add(betData1);
            list.add(betData2);
            jsonObject.put("isbet",0);
            jsonObject.put("configs",list);
        }
        return jsonObject;
    }

    @Override
    public JSONObject applyPhoneBet(JSONObject jsonObject) {
        if(CoreStringUtils.isEmpty(jsonObject.getString("userid"))){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        return phoneBetService.create(jsonObject);

    }

    @Override
    public Pagination<PhoneBetRepresentation> pagination(ListPhoneBetCommand command) {

        command.verifyPage();
        command.setPageSize(18);
        Pagination<PhoneBet> pagination = phoneBetService.pagination(command);
        List<PhoneBetRepresentation> list = mappingService.mapAsList(pagination.getData(),PhoneBetRepresentation.class);

        return new Pagination<>(list,pagination.getCount(),command.getPage(),command.getPageSize());
    }

    @Override
    public Object[] total(ListPhoneBetCommand command) {
        return phoneBetService.total(command);
    }

    @Override
    public void jobStart(String id, String jobNum) {

        phoneBetService.jobStart(id,jobNum);
    }

    @Override
    public void jobEnd(String id, BigDecimal score, BigDecimal lose) {
        phoneBetService.jobEnd(id,score,lose);
    }

    @Override
    public List<CountGameDetailedCommand> count(Date date) {
        return phoneBetService.count(date);
    }
}
