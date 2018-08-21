package bjl.application.gamebet;

import bjl.application.chat.command.CreateChatCommand;
import bjl.domain.model.user.User;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/12/28.
 */
public interface IGameBetAppService {

    Object[] gameBet(CreateChatCommand command);

    Object[] repeatBet(CreateChatCommand command,BigDecimal changeScore);

    User revokeBet(String username,BigDecimal betScore);

    Object[] editBet(String[] strings,String username,BigDecimal changeScore);

}
