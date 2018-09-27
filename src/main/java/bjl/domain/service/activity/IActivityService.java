package bjl.domain.service.activity;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.activity.Activity;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface IActivityService {

    Pagination<Activity> pagination(ListNoticeCommand command);

    void create(String title,String content,String image,String compressImage);

    void delete(String id);

    List<Activity> list();
}
