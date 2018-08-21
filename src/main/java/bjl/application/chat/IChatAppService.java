package bjl.application.chat;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.chat.command.ListChatCommand;
import bjl.application.robot.command.CreateRobotChatCommand;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface IChatAppService {

    void create(CreateChatCommand command);

    void create(CreateRobotChatCommand command);

    JSONObject getChatList(ListChatCommand command);

    JSONObject roomCheck(JSONObject jsonObject);
}
