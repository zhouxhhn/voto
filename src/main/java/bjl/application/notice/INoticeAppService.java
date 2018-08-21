package bjl.application.notice;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.notice.Notice;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2018/1/17.
 */
public interface INoticeAppService {

    void create(String title, String content, String image);

    Pagination<Notice> pagination(ListNoticeCommand command);

    void delete(String id);

    JSONObject list(JSONObject jsonObject);

}
