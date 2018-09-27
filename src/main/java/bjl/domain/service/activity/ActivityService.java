package bjl.domain.service.activity;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.activity.Activity;
import bjl.domain.model.activity.IActivityRepository;
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
@Service("activityService")
public class ActivityService implements IActivityService{

    @Autowired
    private IActivityRepository<Activity,String> activityRepository;

    @Override
    public Pagination<Activity> pagination(ListNoticeCommand command) {

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return activityRepository.pagination(command.getPage(),command.getPageSize(),null,orderList);
    }

    @Override
    public void create(String title, String content, String image,String compressImage) {

        Activity activity = new Activity();
        activity.setTitle(title);
        activity.setContent(content);
        activity.setImage(image);
        activity.setCompress(compressImage);
        activity.setCreateDate(new Date());
        activityRepository.save(activity);
    }

    @Override
    public void delete(String id) {

        Activity activity = activityRepository.getById(id);
        if (activity != null){
            activityRepository.delete(activity);
        }
    }

    @Override
    public List<Activity> list() {
        return activityRepository.findAll();
    }
}
