package bjl.interfaces.upDownPoint.web;
import bjl.application.transfer.ITransferAppService;
import bjl.application.upDownPoint.IUpDownPointAppService;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.constants.VotoContants;
import bjl.domain.model.upDownPoint.IUpDownPointRepository;
import bjl.domain.model.upDownPoint.UpDownPointAgent;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;
import bjl.utils.CommonUtils;
import bjl.utils.GenerateExcelUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dyp on 2017-12-20.
 */
@Controller
@RequestMapping("/upDownPoint")
public class UpDownPointController extends BaseController{

@Autowired
private ITransferAppService transferAppService;

@Autowired
private IUpDownPointRepository upDownPointRepository;

@Autowired
private IUpDownPointAppService upDownPointAppService;


    @RequestMapping(value = "/list")
        public ModelAndView list(UpDownPointCommand command) {

        BigDecimal upPoint = new BigDecimal(0);
        BigDecimal downPoint = new BigDecimal(0);

        return new ModelAndView("/upDownPoint/list", "pagination", upDownPointAppService.pagination(command))
                .addObject("upPoint", upPoint).addObject("downPoint", downPoint)
                .addObject("command", command);
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        try {

            UpDownPointCommand command = new UpDownPointCommand();
            String firstAgent = request.getParameter("firstAgent");
            String userName = request.getParameter("userName");
            String upDownPointType = request.getParameter("upDownPointType");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            if (firstAgent != null && !"".equals(firstAgent)) {
                command.setFirstAgent(firstAgent);
            }
            if (userName != null && !"".equals(userName)) {
                command.setUserName(userName);
            }
            if (upDownPointType != null && !"".equals(upDownPointType)) {
                if (CommonUtils.isInteger(upDownPointType)) {
                    command.setUpDownPointType(Integer.parseInt(upDownPointType));
                }
            }
            if (startDate != null && !"".equals(startDate)) {
                command.setStartDate(startDate);
            }
            if (endDate != null && !"".equals(endDate)) {
                command.setEndDate(endDate);
            }

            List<UpDownPointAgent> list = upDownPointAppService.export(command).getData();
            String[] header = { "公司", "一级代理", "推荐人", "玩家ID","玩家昵称", "操作时间", "操作类型", "金额", "操作员"};
            String fileName = "上下分明细";
            GenerateExcelUtils.exportExcel(response, fileName, header, list, null, VotoContants.UP_DOWN_EXCEL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
