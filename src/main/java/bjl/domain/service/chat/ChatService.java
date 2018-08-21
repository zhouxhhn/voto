package bjl.domain.service.chat;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.chat.command.ListChatCommand;
import bjl.application.robot.command.CreateRobotChatCommand;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.chat.Chat;
import bjl.domain.model.chat.IChatRepository;
import bjl.domain.service.account.IAccountService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("chatService")
public class ChatService implements IChatService{

    @Autowired
    private IChatRepository<Chat, String> chatRepository;
    @Autowired
    private IAccountService accountService;

    /**
     * 保存聊天信息
     * @param command
     * @return
     */
    @Override
    public Chat create(CreateChatCommand command) {

        if(CoreStringUtils.isEmpty(command.getUserid())){
            return null;
        }

        Account account = accountService.searchByAccountName(command.getUserid());

        if(account == null){
            //用户不存在
            return null;
        }

        Chat chat = new Chat();
        chat.setIndex(System.currentTimeMillis());
        chat.setRoomType(command.getRoomtype());
        chat.setText(command.getText());
        chat.setTextType(command.getTexttype());
        chat.setCreateDate(new Date());
        chat.setUsername(account.getUserName());
        if("filipino".equals(account.getRoles().get(0).getName()) ||
                "vietnam".equals(account.getRoles().get(0).getName()) ||
                "macao".equals(account.getRoles().get(0).getName())){
            chat.setName("管理员");
        }else {
            chat.setName(account.getName());
        }
        chat.setHead(account.getHead());
        chatRepository.save(chat);
        return chat;
    }

    /**
     * 保存机器人聊天信息
     * @param command
     * @return
     */
    @Override
    public Chat create(CreateRobotChatCommand command) {

        Chat chat = new Chat();
        chat.setText(command.getBetType()+":"+command.getScore());
        chat.setHead(command.getHead());
        chat.setName(command.getName());
        chat.setUsername(command.getUsername());
        chat.setIndex(System.currentTimeMillis());
        chat.setRoomType(command.getRoomtype());
        chat.setTextType(command.getTexttype());
        chat.setCreateDate(new Date());
        chatRepository.save(chat);

        return chat;
    }

    /**
     * 获取聊天记录
     * @param command
     * @return
     */
    @Override
    public List<Chat> getChatList(ListChatCommand command) {

        if(command.getCount() == null || command.getIndex() == null || command.getRoomtype() == null){
            return new ArrayList<>();
        }

        List<Criterion> criterionList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        criterionList.add(Restrictions.eq("roomType",command.getRoomtype()));

        if(command.getIndex() > 0){
            if(command.getGettype() == 2){
                criterionList.add(Restrictions.gt("index",command.getIndex()));

                orderList.add(Order.asc("createDate"));
            }else if(command.getGettype() == 1){
                criterionList.add(Restrictions.lt("index",command.getIndex()));
                orderList.add(Order.desc("createDate"));
            }
        }else {
            orderList.add(Order.desc("createDate"));
        }

        return chatRepository.list(criterionList,orderList,null,null,null,command.getCount());
    }
}
