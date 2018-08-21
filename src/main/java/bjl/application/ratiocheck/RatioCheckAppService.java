package bjl.application.ratiocheck;

import bjl.application.ratiocheck.command.ListRatioCheckCommand;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.domain.service.ratiocheck.IRatioCheckService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/3/1
 */
@Service("ratioCheckAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RatioCheckAppService implements IRatioCheckAppService{

    @Autowired
    private IRatioCheckService ratioCheckService;

    @Override
    public Pagination<RatioCheck> pagination(ListRatioCheckCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return ratioCheckService.pagination(command);
    }

    @Override
    public RatioCheck pass(String id) {
        return ratioCheckService.pass(id);
    }

    @Override
    public RatioCheck refuse(String id) {
        return ratioCheckService.refuse(id);
    }
}
