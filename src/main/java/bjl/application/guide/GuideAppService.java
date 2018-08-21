package bjl.application.guide;

import bjl.application.guide.reprensentation.ApiGuideRepresentation;
import bjl.application.notice.command.ListNoticeCommand;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.guide.Guide;
import bjl.domain.service.guide.IGuideService;
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
@Service("guideAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GuideAppService implements IGuideAppService{

    @Autowired
    private IGuideService guideService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<Guide> pagination(ListNoticeCommand command) {
        command.verifyPage();
        command.setPageSize(18);
        return guideService.pagination(command);
    }

    @Override
    public void create(String title, String content) {
        guideService.create(title,content);
    }

    @Override
    public void delete(String id) {
        guideService.delete(id);
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {

        List<Guide> list = guideService.list();
        List<ApiGuideRepresentation> data = mappingService.mapAsList(list,ApiGuideRepresentation.class);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取公告成功");
        jsonObject.put("data",data);

        return jsonObject;
    }
}
