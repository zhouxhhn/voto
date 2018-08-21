package bjl.application.activity;

import bjl.application.activity.representation.ApiActivityRepresentation;
import bjl.application.notice.command.ListNoticeCommand;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.activity.Activity;
import bjl.domain.service.activity.IActivityService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
@Service("activityAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

public class ActivityAppService implements IActivityAppService{

    @Autowired
    private IActivityService activityService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<Activity> pagination(ListNoticeCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return activityService.pagination(command);
    }

    @Override
    public void create(String title, String content, String image) {

        activityService.create(title,content,image);
    }

    @Override
    public void delete(String id) {
        activityService.delete(id);
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {

        List<Activity> list = activityService.list();
        List<ApiActivityRepresentation> data = mappingService.mapAsList(list,ApiActivityRepresentation.class);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取活动列表成功");
        jsonObject.put("data",data);

        return jsonObject;
    }
}
