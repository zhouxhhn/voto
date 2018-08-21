package bjl.application.carousel;

import bjl.application.carousel.representation.ApiCarouselRepresentation;
import bjl.application.guide.reprensentation.ApiGuideRepresentation;
import bjl.application.notice.command.ListNoticeCommand;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.carousel.Carousel;
import bjl.domain.model.guide.Guide;
import bjl.domain.service.carousel.ICarouselService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
@Service("carouselAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CarouselAppService implements ICarouselAppService{

    @Autowired
    private ICarouselService carouselService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public void create(String image) {
        carouselService.create(image);
    }

    @Override
    public Pagination<Carousel> pagination(ListNoticeCommand command) {
        command.verifyPage();
        command.setPageSize(18);
        return carouselService.pagination(command);
    }

    @Override
    public void delete(String id) {
        carouselService.delete(id);
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {

        List<Carousel> list = carouselService.list();
        List<ApiCarouselRepresentation> data = mappingService.mapAsList(list,ApiCarouselRepresentation.class);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取轮播图成功");
        jsonObject.put("data",data);

        return jsonObject;
    }
}
