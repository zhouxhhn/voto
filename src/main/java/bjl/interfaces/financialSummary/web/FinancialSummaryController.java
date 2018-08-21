package bjl.interfaces.financialSummary.web;

import bjl.application.financialSummary.IFinancialSummaryAppService;
import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.financialSummary.representation.FinancialSummaryRepresentation;
import bjl.application.gamebet.IGameBetAppService;
import bjl.application.gamedetailed.IGameDetailedAppService;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.core.mapping.MappingService;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.service.financialSummary.IFinancialSummaryService;
import bjl.domain.service.gamedetailed.IGameDetailedService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by dyp on 2017-12-20.
 */
@Controller
@RequestMapping("/total")
public class FinancialSummaryController extends BaseController {

    @Autowired
    private IFinancialSummaryAppService financialSummaryAppService;
    @Autowired
    private IGameDetailedService gameDetailedService;

    @Autowired
    private IFinancialSummaryService financialSummaryService;

    @Autowired
    private MappingService mappingService;

    @RequestMapping(value = "/test")
    public void test() {
        ListGameDetailedCommand command = new ListGameDetailedCommand();
        command.setStartDate("2018-01-01");
        command.setPage(1);
        command.setPageSize(10);
        Pagination<GameDetailed> list = gameDetailedService.pagination(command);
        GameDetailed gameDetaileds = new GameDetailed();
//        List<CreateGameDetailedCommand> list;
//        for(GameDetailed gameDetailed : list.getData()){
//            gameDetaileds.setBanker(gameDetaileds.getBanker().add(gameDetailed.getBanker()));
//
//        }
//        financialSummaryAppService.create(gameDetaileds);
   }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListFinancialSummaryCommand command) {



        Pagination<FinancialSummaryRepresentation> pagination = financialSummaryAppService.pagination(command);
        Object[] objects = financialSummaryAppService.sum(command);

        return new ModelAndView("/financialSummary/FinancialSummary", "pagination", financialSummaryAppService.pagination(command))
                .addObject("sum", objects)
                //页面查询参数
                .addObject("command", command);

    }


}
