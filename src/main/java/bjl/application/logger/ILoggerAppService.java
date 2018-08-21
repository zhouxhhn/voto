package bjl.application.logger;

import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.logger.command.ListLoggerCommand;
import bjl.application.logger.representation.LoggerRepresentation;
import bjl.core.enums.LoggerType;
import bjl.domain.model.account.Account;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Author pengyi
 * Date 17-4-21.
 */
public interface ILoggerAppService {

    void create(CreateLoggerCommand command);

    void create(Account account, LoggerType loggerType, String loggerContent);

    Pagination<LoggerRepresentation> pagination(ListLoggerCommand command);
}
