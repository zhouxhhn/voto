package bjl.domain.service.guide;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.guide.Guide;
import bjl.domain.model.guide.IGuideRepository;
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
@Service("guideService")
public class GuideService implements IGuideService{

    @Autowired
    private IGuideRepository<Guide,String> guideRepository;

    @Override
    public Pagination<Guide> pagination(ListNoticeCommand command) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return guideRepository.pagination(command.getPage(),command.getPageSize(),null,orderList);
    }

    @Override
    public void create(String title, String content,String imageUrl) {

        Guide guide = new Guide();
        guide.setContent(content);
        guide.setTitle(title);
        guide.setCreateDate(new Date());
        guide.setImageUrl(imageUrl);
        guideRepository.save(guide);
    }

    @Override
    public void delete(String id) {
        Guide guide = guideRepository.getById(id);
        if(guide != null){
            guideRepository.delete(guide);
        }
    }

    @Override
    public List<Guide> list() {
        return guideRepository.findAll();
    }
}
