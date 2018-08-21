package bjl.application.ratio;

import bjl.application.ratio.command.EditRatioCommand;
import bjl.application.ratio.representation.RatioRepresentation;
import bjl.domain.model.ratio.Ratio;

/**
 * Created by zhangjin on 2018/5/2
 */
public interface IRatioAppService {

    RatioRepresentation getByAccount(String accountId);

    Ratio edit(EditRatioCommand command);

    void update(Ratio ratio);
}
