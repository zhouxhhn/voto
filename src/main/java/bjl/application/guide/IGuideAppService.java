package bjl.application.guide;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.guide.Guide;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface IGuideAppService {

    Pagination<Guide> pagination(ListNoticeCommand command);

    void create(String title,String content);

    void delete(String id);

    JSONObject list(JSONObject jsonObject);
}
