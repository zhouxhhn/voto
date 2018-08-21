package bjl.domain.service.guide;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.guide.Guide;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface IGuideService {

    Pagination<Guide> pagination(ListNoticeCommand command);

    void create(String title,String content);

    void delete(String id);

    List<Guide> list();
}
