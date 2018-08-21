package bjl.application.carousel;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.carousel.Carousel;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface ICarouselAppService {

    void create(String image);

    Pagination<Carousel> pagination(ListNoticeCommand command);

    void delete(String id);

    JSONObject list(JSONObject jsonObject);
}
