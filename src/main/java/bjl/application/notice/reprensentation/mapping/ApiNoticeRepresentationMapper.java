package bjl.application.notice.reprensentation.mapping;

import bjl.application.notice.reprensentation.ApiNoticeRepresentation;
import bjl.core.util.CoreDateUtils;
import bjl.domain.model.notice.Notice;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/1/17.
 */
@Component
public class ApiNoticeRepresentationMapper extends CustomMapper<Notice, ApiNoticeRepresentation> {

    public void mapAtoB(Notice notice, ApiNoticeRepresentation representation, MappingContext context) {

        representation.setTime(CoreDateUtils.formatDateTime(notice.getCreateDate()));
    }
}
