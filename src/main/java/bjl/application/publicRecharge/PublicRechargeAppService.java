package bjl.application.publicRecharge;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.application.publicRecharge.representation.ApiPublicRechargeRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.activity.Activity;
import bjl.domain.model.publicRecharge.PublicRecharge;
import bjl.domain.service.activity.IActivityService;
import bjl.domain.service.publicRecharge.IPublicRechargeService;
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
@Service("publicRechargeAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PublicRechargeAppService implements IPublicRechargeAppService {

    @Autowired
    private IPublicRechargeService publicRechargeService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<PublicRecharge> pagination(ListNoticeCommand command) {
        command.verifyPage();
        command.setPageSize(18);
        return publicRechargeService.pagination(command);
    }

    @Override
    public void create(String title, String content, String image) {
        publicRechargeService.create(title,content,image);
    }

    @Override
    public void delete(String id) {
        publicRechargeService.delete(id);
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {

        List<PublicRecharge> list = publicRechargeService.list();
        List<ApiPublicRechargeRepresentation> data = mappingService.mapAsList(list,ApiPublicRechargeRepresentation.class);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取充值信息成功");
        jsonObject.put("data",data);

        return jsonObject;
    }
}
