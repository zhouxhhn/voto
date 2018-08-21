package bjl.application.phonebet;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.application.phonebet.command.ListPhoneBetCommand;
import bjl.application.phonebet.representation.PhoneBetRepresentation;
import bjl.domain.model.phonebet.PhoneBet;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface IPhoneBetAppService {

    JSONObject getBetData(JSONObject jsonObject);

    JSONObject applyPhoneBet(JSONObject jsonObject);

    Pagination<PhoneBetRepresentation> pagination(ListPhoneBetCommand command);

    Object[] total(ListPhoneBetCommand command);

    void jobStart(String id,String jobNum);

    void jobEnd(String id, BigDecimal score, BigDecimal lose);

    List<CountGameDetailedCommand> count(Date date);
}
