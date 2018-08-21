package bjl.infrastructure.persistence.hibernate.chat;

import bjl.domain.model.chat.Chat;
import bjl.domain.model.chat.IChatRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Repository("chatRepository")
public class ChatRepository extends AbstractHibernateGenericRepository<Chat, String>
        implements IChatRepository<Chat, String> {
}
