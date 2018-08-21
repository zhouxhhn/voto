package bjl.domain.service.carousel;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.carousel.Carousel;
import bjl.domain.model.carousel.ICarouselRepository;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
@Service("carouselService")
public class CarouselService implements ICarouselService{

    @Autowired
    private ICarouselRepository<Carousel,String> carouselRepository;

    @Override
    public void create(String image) {

        Carousel carousel = new Carousel();
        carousel.setImage(image);
        carousel.setCreateDate(new Date());
        carouselRepository.save(carousel);
    }

    @Override
    public Pagination<Carousel> pagination(ListNoticeCommand command) {

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return carouselRepository.pagination(command.getPage(),command.getPageSize(),null,orderList);
    }

    @Override
    public void delete(String id) {
        Carousel carousel = carouselRepository.getById(id);
        if(carousel != null){
            carouselRepository.delete(carousel);
        }

    }

    @Override
    public List<Carousel> list() {
        return carouselRepository.findAll();
    }
}
