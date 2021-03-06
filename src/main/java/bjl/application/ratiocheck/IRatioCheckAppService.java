package bjl.application.ratiocheck;

import bjl.application.ratiocheck.command.ListRatioCheckCommand;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/3/1
 */
public interface IRatioCheckAppService {

    Pagination<RatioCheck> pagination(ListRatioCheckCommand command);

    RatioCheck pass(String id);

    RatioCheck refuse(String id);
}
