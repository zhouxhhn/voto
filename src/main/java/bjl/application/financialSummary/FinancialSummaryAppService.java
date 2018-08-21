package bjl.application.financialSummary;

import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.financialSummary.command.TotalFinancialSummaryCommand;
import bjl.application.financialSummary.representation.FinancialSummaryRepresentation;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.core.mapping.MappingService;
import bjl.domain.model.financialSummary.FinancialSummary;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.service.financialSummary.IFinancialSummaryService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dyp on 2017-12-20.
 */
@Service("financialSummaryAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FinancialSummaryAppService  implements  IFinancialSummaryAppService{
    @Autowired
    private IFinancialSummaryService financialSummaryService;
    @Autowired
    private MappingService mappingService;


    @Override
    public void create(List<CreateGameDetailedCommand> createGameDetailedCommands) {
        financialSummaryService.create(createGameDetailedCommands);
    }

    @Override
    public Pagination<FinancialSummaryRepresentation> pagination(ListFinancialSummaryCommand command) {

        command.verifyPage();
        command.verifyPageSize(18);

        Pagination<FinancialSummary>  pagination = financialSummaryService.pagination(command);
        List<FinancialSummaryRepresentation> data = mappingService.mapAsList(pagination.getData(),FinancialSummaryRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public TotalGameDetailedCommand total(ListFinancialSummaryCommand command) {
        return financialSummaryService.total(command);
    }

    @Override
    public void update(List<CreateGameDetailedCommand> createGameDetailedCommands) {
        financialSummaryService.update(createGameDetailedCommands);
    }

    @Override
    public Object[] sum(ListFinancialSummaryCommand command) {
        return financialSummaryService.sum(command);
    }
}

