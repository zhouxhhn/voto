package bjl.application.notice;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.application.notice.reprensentation.ApiNoticeRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.notice.Notice;
import bjl.domain.service.notice.INoticeService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangjin on 2018/1/17.
 */
@Service("noticeAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NoticeAppService implements INoticeAppService{

    @Autowired
    private INoticeService noticeService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public void create(String title, String content, String image) {
        noticeService.create(title,content,image);
    }

    @Override
    public Pagination<Notice> pagination(ListNoticeCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return noticeService.pagination(command);
    }

    @Override
    public void delete(String id) {
        noticeService.delete(id);
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {

        List<Notice> list = noticeService.list();
        List<ApiNoticeRepresentation> data = mappingService.mapAsList(list,ApiNoticeRepresentation.class);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取公告成功");
        jsonObject.put("data",data);

        return jsonObject;
    }
}
