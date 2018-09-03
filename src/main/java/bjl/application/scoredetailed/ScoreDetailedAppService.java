package bjl.application.scoredetailed;

import com.alibaba.fastjson.JSONObject;

import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedResponse;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.constants.VotoContants;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.User;
import bjl.domain.service.scoredetailed.IScoreDetailedService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("scoreDetailedAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ScoreDetailedAppService implements IScoreDetailedAppService{

    @Autowired
    private IScoreDetailedService scoreDetailedService;

    @Override
    public User create(CreateScoreDetailedCommand command) {
        synchronized (command.getUserId()){
            return scoreDetailedService.create(command);
        }
    }

    @Override
    public JSONObject list(ListGameDetailedCommand command) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = new JSONObject();

        command.verifyPage();
        command.verifyPageSize(18);

        Pagination<ScoreDetailed> pagination =  scoreDetailedService.pagination(command);
        List<ScoreDetailed> scoreDetailedList = pagination.getData();


        BigDecimal transactionTotal = new BigDecimal(0); //交易总金额
        BigDecimal balanceTotal = new BigDecimal(0); //总余额
        if(scoreDetailedList != null && scoreDetailedList.size() > 0){
            int size = scoreDetailedList.size();
            for (int i = 0; i<size;i++){
                ScoreDetailed scoreDetailed = scoreDetailedList.get(i);
                GameDetailedResponse response = new GameDetailedResponse();
                response.setTriratnaProfit(scoreDetailed.getScore());
                response.setBalancee(scoreDetailed.getNewScore());
                if(scoreDetailed.getCreateDate() != null && !"".equals(scoreDetailed.getCreateDate())){
                    response.setCreateDate(scoreDetailed.getCreateDate());
                    response.setTime(sdf.format(scoreDetailed.getCreateDate()));
                }

                response.setHallType(scoreDetailed.getActionType());

                transactionTotal = transactionTotal.add(scoreDetailed.getScore());

                scoreDetailedList.set(i,scoreDetailed);
            }
            balanceTotal = scoreDetailedList.get(size-1).getNewScore();
        }

        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取个人流水成功");
        jsonObject.put("data",scoreDetailedList);
        jsonObject.put("transactionTotal",transactionTotal);
        jsonObject.put("balanceTotal",balanceTotal);
        jsonObject.put("count",pagination.getCount());
        jsonObject.put("page",pagination.getPage());
        jsonObject.put("pageSize",pagination.getPageSize());
        if(command.getCbid() != null){
            jsonObject.put("cbid",command.getCbid());
        }
        return jsonObject;
    }
}
