package bjl.infrastructure.persistence.hibernate.notice;

import bjl.domain.model.notice.INoticeRepository;
import bjl.domain.model.notice.Notice;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/5/6.
 */
@Repository("noticeRepository")
public class NoticeRepository extends AbstractHibernateGenericRepository<Notice, String>
        implements INoticeRepository<Notice, String> {
}
