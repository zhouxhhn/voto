package bjl.domain.service.phonebet;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.application.phonebet.command.ListPhoneBetCommand;
import bjl.domain.model.phonebet.PhoneBet;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/9.
 */
public interface IPhoneBetService {

    JSONObject create(JSONObject jsonObject);

    Pagination<PhoneBet> pagination(ListPhoneBetCommand command);

    Object[] total(ListPhoneBetCommand command);

    void jobStart(String id,String jobNum);

    void jobEnd(String id, BigDecimal score, BigDecimal lose);

    PhoneBet searchByUsername(String username);

    List<CountGameDetailedCommand> count(Date date);
}
