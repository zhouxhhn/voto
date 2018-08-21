package bjl.domain.service.notice;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.notice.Notice;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by zhangjin on 2018/1/17.
 */
public interface INoticeService {

    void create(String title, String content, String image);

    Pagination<Notice> pagination(ListNoticeCommand command);

    void delete(String id);

    List<Notice> list();
}
