package bjl.application.publicRecharge.representation.mapping;

import bjl.application.publicRecharge.representation.ApiPublicRechargeRepresentation;
import bjl.core.util.CoreDateUtils;
import bjl.domain.model.activity.Activity;
import bjl.domain.model.publicRecharge.PublicRecharge;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;


/**
 * Created by zhangjin on 2018/6/21
 */
@Component
public class ApiPublicRechargeRepresentationMapper extends CustomMapper<PublicRecharge, ApiPublicRechargeRepresentation> {

    public void mapAtoB(PublicRecharge publicRecharge, ApiPublicRechargeRepresentation representation, MappingContext context) {

        representation.setTime(CoreDateUtils.formatDateTime(publicRecharge.getCreateDate()));
    }
}
