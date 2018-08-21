package bjl.application.financialSummary;
import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.financialSummary.representation.FinancialSummaryRepresentation;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;


/**
 * Created by dyp on 2017-12-20.
 */
public interface IFinancialSummaryAppService {


    void create(List<CreateGameDetailedCommand> createGameDetailedCommands);

    Pagination<FinancialSummaryRepresentation> pagination(ListFinancialSummaryCommand command);

    TotalGameDetailedCommand total(ListFinancialSummaryCommand command);

    void update(List<CreateGameDetailedCommand> createGameDetailedCommands);

    Object[] sum(ListFinancialSummaryCommand command);

}
