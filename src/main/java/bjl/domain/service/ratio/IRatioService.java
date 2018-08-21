package bjl.domain.service.ratio;

import bjl.application.ratio.command.EditRatioCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.ratio.Ratio;

/**
 * Created by zhangjin on 2018/5/2
 */
public interface IRatioService {

    Ratio create(Account account);

    Ratio getByAccount(String accountId);

    Ratio edit(EditRatioCommand command);

    void update(Ratio ratio);
}
