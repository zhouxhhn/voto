package bjl.domain.service.logger;

import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.logger.command.ListLoggerCommand;
import bjl.core.enums.LoggerType;
import bjl.domain.model.account.Account;
import bjl.domain.model.logger.Logger;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Author pengyi
 * Date 17-4-21.
 */
public interface ILoggerService {
    void create(CreateLoggerCommand command);

    void create(Account account, LoggerType loggerType, String loggerContent);

    Pagination<Logger> pagination(ListLoggerCommand command);
}
