package bjl.domain.service.logger;

import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.logger.command.ListLoggerCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.logger.ILoggerRepository;
import bjl.domain.model.logger.Logger;
import bjl.domain.service.account.IAccountService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author pengyi
 * Date 17-4-21.
 */
@Service("loggerService")
public class LoggerService implements ILoggerService {

    private final ILoggerRepository<Logger, String> loggerRepository;
    private final IAccountService accountService;

    @Autowired
    public LoggerService(ILoggerRepository<Logger, String> loggerRepository, IAccountService accountService) {
        this.loggerRepository = loggerRepository;
        this.accountService = accountService;
    }

    @Override
    public void create(CreateLoggerCommand command) {
        Account account = accountService.searchByID(command.getUserId());

        Logger logger = new Logger(account, command.getLoggerType(), command.getLoggerContent(), command.getIp());
        loggerRepository.save(logger);
    }

    @Override
    public void create(Account account, LoggerType loggerType, String loggerContent) {
        Logger logger = new Logger();
        logger.setLoggerContent(loggerContent);
        logger.setLoggerType(loggerType);
        logger.setOperationUser(account);
        loggerRepository.save(logger);
    }

    @Override
    public Pagination<Logger> pagination(ListLoggerCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();

        if (!CoreStringUtils.isEmpty(command.getOperationUser())) {
            criterionList.add(Restrictions.like("operationUser.userName", command.getOperationUser(), MatchMode.ANYWHERE));
            aliasMap.put("operationUser", "operationUser");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (null != command.getLoggerType()) {
            criterionList.add(Restrictions.eq("loggerType", command.getLoggerType()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return loggerRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }
}
