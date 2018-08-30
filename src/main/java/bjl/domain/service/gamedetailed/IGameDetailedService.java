package bjl.domain.service.gamedetailed;

import com.alibaba.fastjson.JSONObject;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/3.
 */
public interface IGameDetailedService {

    User save(CreateGameDetailedCommand command);

    User update(GameDetailed gameDetailed, BigDecimal changeScore);

    User update(CreateGameDetailedCommand command, BigDecimal changeScore);

    List<GameDetailed> apiList(String[] strings);

    Pagination<GameDetailed> pagination(ListGameDetailedCommand command);

    TotalGameDetailedCommand total(ListGameDetailedCommand command);

    List<GameDetailed> getMax(Integer roomType);

    List<CountGameDetailedCommand> list(Date date);

    List<GameDetailed> getLast(Integer roomType,Integer boots, Integer games);

    JSONObject list(ListGameDetailedCommand listGameDetailedCommand);
}
