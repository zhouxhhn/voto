package bjl.application.gamedetailed;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2018/1/3.
 */
public interface IGameDetailedAppService {

    User save(CreateGameDetailedCommand command);

    User update(GameDetailed gameDetailed, BigDecimal changeScore);

    User update(CreateGameDetailedCommand command, BigDecimal changeScore);

    Map<String, Object> apiList(String[] strings);

    Pagination<GameDetailedRepresentation> pagination(ListGameDetailedCommand  command);

    TotalGameDetailedCommand total(ListGameDetailedCommand command);

    GameDetailed getMax(Integer roomType);

    List<CountGameDetailedCommand> list(Date date);

    List<GameDetailed> getLast(Integer roomType,Integer boots, Integer games);
}
