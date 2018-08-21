package bjl.application.chat;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.chat.command.ListChatCommand;
import bjl.application.chat.representation.ApiChatRepresentation;
import bjl.application.chat.representation.ApiPushChatRepresentation;
import bjl.application.robot.command.CreateRobotChatCommand;
import bjl.application.userManager.IUserManagerAppService;
import bjl.core.mapping.IMappingService;
import bjl.core.message.PushMessage;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.chat.Chat;
import bjl.domain.model.user.User;
import bjl.domain.service.chat.IChatService;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("chatAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChatAppService implements IChatAppService{

    @Autowired
    private IChatService chatService;
    @Autowired
    private IMappingService mappingService;
    @Autowired
    private IUserManagerAppService userManagerAppService;

    @Override
    public void create(CreateChatCommand command) {

        Chat chat = chatService.create(command);
        if(chat != null){
            ApiPushChatRepresentation representation = mappingService.map(chat,ApiPushChatRepresentation.class,false);
            //推送聊天信息
            PushMessage.pushChat(representation);
        }
    }

    @Override
    public void create(CreateRobotChatCommand command) {

        Chat chat = chatService.create(command);
        if(chat != null){
            ApiPushChatRepresentation representation = mappingService.map(chat,ApiPushChatRepresentation.class,false);
            //推送聊天信息
            PushMessage.pushChat(representation);
        }
    }

    @Override
    public JSONObject getChatList(ListChatCommand command) {
        JSONObject jsonObject = new JSONObject();
        if(!CoreStringUtils.isEmpty(command.getCbid())){
            jsonObject.put("cbid",command.getCbid());
        }
        jsonObject.put("roomtype",command.getRoomtype());
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取聊天记录成功");
        jsonObject.put("records",mappingService.mapAsList(chatService.getChatList(command), ApiChatRepresentation.class));

        return jsonObject;
    }

    @Override
    public JSONObject roomCheck(JSONObject jsonObject) {

        GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(jsonObject.getInteger("roomtype"));

        if (gameStatus == null || gameStatus.getStatus() == -1){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","房间未开启");
            jsonObject.put("gold","");

        }else {
            User user = userManagerAppService.searchByUsername(jsonObject.getString("userid"));
            if(user == null){
                jsonObject.put("code",1);
                jsonObject.put("errmsg","用户不存在");
                jsonObject.put("gold","");
            }else {
                jsonObject.put("code",0);
                jsonObject.put("errmsg","");
                jsonObject.put("gold",user.getScore());
            }
        }

        return jsonObject;
    }
}
