package bjl.application.chat.representation.mapping;

import bjl.application.chat.representation.ApiChatRepresentation;
import bjl.application.chat.representation.ApiPushChatRepresentation;
import bjl.domain.model.chat.Chat;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Component
public class ApiChatRepresentationMapper extends CustomMapper<Chat,ApiChatRepresentation> {

    public void mapAtoB(Chat chat, ApiChatRepresentation representation, MappingContext context) {
        representation.setTexttype(chat.getTextType());
        representation.setUserid(chat.getUsername());
        representation.setTime(chat.getCreateDate().getTime());
    }
}
