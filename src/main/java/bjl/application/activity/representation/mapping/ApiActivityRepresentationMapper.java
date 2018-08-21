package bjl.application.activity.representation.mapping;

import bjl.application.activity.representation.ApiActivityRepresentation;
import bjl.core.util.CoreDateUtils;
import bjl.domain.model.activity.Activity;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;


/**
 * Created by zhangjin on 2018/6/21
 */
@Component
public class ApiActivityRepresentationMapper extends CustomMapper<Activity, ApiActivityRepresentation> {

    public void mapAtoB(Activity activity, ApiActivityRepresentation representation, MappingContext context) {

        representation.setTime(CoreDateUtils.formatDateTime(activity.getCreateDate()));
    }
}
