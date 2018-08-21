package bjl.application.logger;

import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.logger.command.ListLoggerCommand;
import bjl.application.logger.representation.LoggerRepresentation;
import bjl.core.enums.LoggerType;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.account.Account;
import bjl.domain.model.logger.Logger;
import bjl.domain.service.logger.ILoggerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("loggerAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LoggerAppService implements ILoggerAppService {

    private final ILoggerService loggerService;

    private final IMappingService mappingService;

    @Autowired
    public LoggerAppService(ILoggerService loggerService, IMappingService mappingService) {
        this.loggerService = loggerService;
        this.mappingService = mappingService;
    }

    @Override
    public void create(CreateLoggerCommand command) {
        loggerService.create(command);
    }

    @Override
    public void create(Account account, LoggerType loggerType, String loggerContent) {
        loggerService.create(account,loggerType,loggerContent);
    }

    @Override
    public Pagination<LoggerRepresentation> pagination(ListLoggerCommand command) {
        command.verifyPage();
        command.verifyPageSize(16);

        Pagination<Logger> pagination = loggerService.pagination(command);
        List<LoggerRepresentation> data = mappingService.mapAsList(pagination.getData(), LoggerRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }
}
