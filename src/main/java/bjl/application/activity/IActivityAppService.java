package bjl.application.activity;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.activity.Activity;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface IActivityAppService {

    Pagination<Activity> pagination(ListNoticeCommand command);

    void create(String title,String content,String image);

    void delete(String id);

    JSONObject list(JSONObject jsonObject);
}
