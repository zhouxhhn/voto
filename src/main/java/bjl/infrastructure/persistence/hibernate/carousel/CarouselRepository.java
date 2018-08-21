package bjl.infrastructure.persistence.hibernate.carousel;

import bjl.domain.model.carousel.Carousel;
import bjl.domain.model.carousel.ICarouselRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/6/21
 */
@Repository("carouselRepository")
public class CarouselRepository extends AbstractHibernateGenericRepository<Carousel,String>
        implements ICarouselRepository<Carousel,String> {
}
