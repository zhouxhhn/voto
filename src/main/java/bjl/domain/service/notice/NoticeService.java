package bjl.domain.service.notice;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.notice.INoticeRepository;
import bjl.domain.model.notice.Notice;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/17.
 */
@Service("noticeService")
public class NoticeService implements INoticeService{

    @Autowired
    private INoticeRepository<Notice, String> noticeRepository;

    /**
     * 创建公告
     */
    @Override
    public void create(String title, String content, String image) {

        if(content != null && content.length()>500){
            return;
        }
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setImage(image);
        notice.setCreateDate(new Date());
        noticeRepository.save(notice);
    }

    /**
     * 查询公告
     * @param command
     * @return
     */
    @Override
    public Pagination<Notice> pagination(ListNoticeCommand command) {

        List<Criterion> criterionList = new ArrayList<>();

        if(command.getType() != null && command.getType() != 0){
            criterionList.add(Restrictions.eq("type",command.getType()));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return noticeRepository.pagination(command.getPage(),command.getPageSize(),criterionList,orderList);
    }

    /**
     * 删除公告
     * @param id
     */
    @Override
    public void delete(String id) {

        Notice notice = noticeRepository.getById(id);
        if(notice != null){
            noticeRepository.delete(notice);
        }
    }

    @Override
    public List<Notice> list() {
        return noticeRepository.findAll();
    }
}
