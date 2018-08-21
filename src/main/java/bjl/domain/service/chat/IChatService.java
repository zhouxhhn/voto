package bjl.domain.service.chat;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.chat.command.ListChatCommand;
import bjl.application.robot.command.CreateRobotChatCommand;
import bjl.domain.model.chat.Chat;

import java.util.List;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface IChatService {


    Chat create(CreateChatCommand command);

    Chat create(CreateRobotChatCommand command);

    List<Chat> getChatList(ListChatCommand command);
}
