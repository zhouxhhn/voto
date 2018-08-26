package bjl.interfaces.triratna;

import bjl.application.triratna.ITriratnaAppService;
import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.constants.VotoContants;
import bjl.interfaces.shared.web.BaseController;
import bjl.utils.CommonUtils;
import bjl.utils.GenerateExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Controller
@RequestMapping("triratna")
public class TriratnaController extends BaseController{

    @Autowired
    private ITriratnaAppService triratnaAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListITriratnaCommand command){

        return new ModelAndView("/triratna/list","pagination",triratnaAppService.pagination(command,null))
                .addObject("command",command).addObject("total",triratnaAppService.total(command));

    }

    @RequestMapping(value = "/exportTriratna")
    public void exportTriratna(HttpServletRequest request, HttpServletResponse response){
      try{
        String xueInput = request.getParameter("xueInput");
        String juInput = request.getParameter("juInput");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String userName = request.getParameter("userName");

        ListITriratnaCommand command = new ListITriratnaCommand();
        if(xueInput !=null && !"".equals(xueInput)){
          if(CommonUtils.isInteger(xueInput)){
              command.setBoots(Integer.parseInt(xueInput));
          }

        }
        if(juInput !=null && !"".equals(juInput)){
            if(CommonUtils.isInteger(xueInput)){
                command.setGames(Integer.parseInt(juInput));
            }

        }
        if(startDate !=null && !"".equals(startDate)){
          command.setStartDate(startDate);
        }
        if(xueInput !=null && !"".equals(xueInput)){
          command.setEndDate(endDate);
        }
        if(userName !=null && !"".equals(userName)){
           command.setUserName(userName);
        }
        List<TriratnaRepresentation> list = triratnaAppService.pagination(command, VotoContants.EXPORT_EXCEL).getData();
        TotalTriratna totalTriratna = triratnaAppService.total(command);

        String[] header = {"用户昵称","靴局","闲对","庄对","和","三宝总投注","选手总盈亏","公司总利润"};
        String fileName = "三宝盈亏汇总";
        GenerateExcelUtils.exportExcel(response, fileName, header, list, totalTriratna,  VotoContants.TRIRATNA_EXCEL);
      }catch (Exception e){
        e.printStackTrace();
      }
    }

}
