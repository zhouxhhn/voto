package bjl.domain.service.upDownPoint;

import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dyp on 2017-12-25.
 */
public interface IUpDownPointService {

   void create(ModifyUserCommand createUpDownPoint);

    Pagination<UpDownPoint> pagination(UpDownPointCommand command);

    Map<String, BigDecimal> sum(Date date);


}
