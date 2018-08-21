package bjl.application.logger.representation.mapping;

import bjl.application.logger.representation.LoggerRepresentation;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.logger.Logger;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/6/9.
 */
@Component
public class LoggerRepresentationMapper extends CustomMapper<Logger, LoggerRepresentation> {

    public void mapAtoB(Logger logger, LoggerRepresentation representation, MappingContext context) {

        if (logger.getOperationUser() != null) {
            if(!CoreStringUtils.isEmpty(logger.getOperationUser().getPhoneNo())){
                representation.setUserName(logger.getOperationUser().getPhoneNo());
            }else {
                representation.setUserName(logger.getOperationUser().getUserName());
            }
        }
        if (logger.getId() != null) {
            representation.setId(logger.getId());
        }
        if (logger.getCreateDate() != null) {
            representation.setCreateDate(logger.getCreateDate());
        }
    }
}
