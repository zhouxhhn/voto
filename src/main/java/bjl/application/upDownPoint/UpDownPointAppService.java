package bjl.application.upDownPoint;


import bjl.application.upDownPoint.command.CreateUpDownPoint;
import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.domain.service.upDownPoint.IUpDownPointService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by dyp on 2017-12-26.
 */
@Service("upDownPointAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UpDownPointAppService implements IUpDownPointAppService{

    @Autowired
    private IUpDownPointService upDownPointService;

    @Override
    public Pagination<UpDownPoint> pagination(UpDownPointCommand command) {
        command.verifyPage();
        command.verifyPageSize(18);

        return upDownPointService.pagination(command);
    }

    @Override
    public Map<String, BigDecimal> sum(Date date) {
        return upDownPointService.sum(date);
    }

    @Override
    public void create(ModifyUserCommand command) {
        upDownPointService.create(command);

    }
}
