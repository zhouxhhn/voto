package bjl.domain.service.carousel;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.carousel.Carousel;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface ICarouselService {

    void create(String image);

    Pagination<Carousel> pagination(ListNoticeCommand command);

    void delete(String id);

    List<Carousel> list();
}
