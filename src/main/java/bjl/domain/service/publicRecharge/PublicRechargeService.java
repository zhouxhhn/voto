package bjl.domain.service.publicRecharge;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.activity.Activity;
import bjl.domain.model.activity.IActivityRepository;
import bjl.domain.model.publicRecharge.IPublicRechargeRepository;
import bjl.domain.model.publicRecharge.PublicRecharge;
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
@Service("publicRechargeService")
public class PublicRechargeService implements IPublicRechargeService {

    @Autowired
    private IPublicRechargeRepository<PublicRecharge,String> publicRechargeRepository;

    @Override
    public Pagination<PublicRecharge> pagination(ListNoticeCommand command) {

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return publicRechargeRepository.pagination(command.getPage(),command.getPageSize(),null,orderList);
    }

    @Override
    public void create(String title, String content, String image) {

        PublicRecharge publicRecharge = new PublicRecharge();
        publicRecharge.setTitle(title);
        publicRecharge.setContent(content);
        publicRecharge.setImage(image);
        publicRecharge.setCreateDate(new Date());
        publicRechargeRepository.save(publicRecharge);
    }

    @Override
    public void delete(String id) {

        PublicRecharge publicRecharge = publicRechargeRepository.getById(id);
        if (publicRecharge != null){
            publicRechargeRepository.delete(publicRecharge);
        }
    }

    @Override
    public List<PublicRecharge> list() {
        return publicRechargeRepository.findAll();
    }
}
