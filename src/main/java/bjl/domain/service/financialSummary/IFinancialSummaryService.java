package bjl.domain.service.financialSummary;
import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.domain.model.financialSummary.FinancialSummary;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import java.util.List;

public interface IFinancialSummaryService {

    List<GameDetailed> apiList(String[] strings);

    List<FinancialSummary> list(String[] strings);

    Pagination<FinancialSummary>  pagination(ListFinancialSummaryCommand command);

    TotalGameDetailedCommand total(ListFinancialSummaryCommand command);

    List<GameDetailed>  findByDate(String userId, String startDate, String endDate);

    List<GameDetailed> findByBoot(GameDetailed winDetail);

    void create(List<CreateGameDetailedCommand> createGameDetailedCommands);

    void update(List<CreateGameDetailedCommand> createGameDetailedCommands);

    Object[] sum(ListFinancialSummaryCommand command);

}
