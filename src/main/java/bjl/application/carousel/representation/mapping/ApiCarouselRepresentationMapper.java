package bjl.application.carousel.representation.mapping;

import bjl.application.carousel.representation.ApiCarouselRepresentation;
import bjl.application.notice.reprensentation.ApiNoticeRepresentation;
import bjl.domain.model.carousel.Carousel;
import bjl.domain.model.guide.Guide;
import ma.glasnost.orika.CustomMapper;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/6/21
 */
@Component
public class ApiCarouselRepresentationMapper extends CustomMapper<Carousel, ApiCarouselRepresentation> {

}
