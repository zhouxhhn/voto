package bjl.application.guide.reprensentation.mapping;

import bjl.application.guide.reprensentation.ApiGuideRepresentation;
import bjl.domain.model.guide.Guide;
import ma.glasnost.orika.CustomMapper;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/6/21
 */
@Component
public class ApiGuideRepresentationMapper extends CustomMapper<Guide, ApiGuideRepresentation> {

}
