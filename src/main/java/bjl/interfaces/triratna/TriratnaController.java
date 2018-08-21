package bjl.interfaces.triratna;

import bjl.application.triratna.ITriratnaAppService;
import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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

        return new ModelAndView("/triratna/list","pagination",triratnaAppService.pagination(command))
                .addObject("command",command).addObject("total",triratnaAppService.total(command));

    }

    @RequestMapping(value = "/exportTriratna")
    public void exportTriratna(HttpServletRequest request, HttpServletResponse response){
      System.out.println("11111");
      try{
     // @RequestParam(value = "xueInput") String xueInput
        String xueInput = request.getParameter("xueInput");
        String juInput = request.getParameter("juInput");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        System.out.println(xueInput+" "+juInput+" "+startDate+" "+endDate);

        ListITriratnaCommand command = new ListITriratnaCommand();
        if(xueInput !=null && !"".equals(xueInput)){
          command.setBoots(Integer.parseInt(xueInput));
        }
        if(juInput !=null && !"".equals(juInput)){
          command.setGames(Integer.parseInt(juInput));
        }
        if(startDate !=null && !"".equals(startDate)){
          command.setStartDate(startDate);
        }
        if(xueInput !=null && !"".equals(xueInput)){
          command.setEndDate(endDate);
        }

        List<TriratnaRepresentation> list = triratnaAppService.pagination(command).getData();

          String[] header = {"靴局","闲对","庄对","和","三宝总投注","选手总盈亏","公司总利润"};
          HSSFWorkbook wb = new HSSFWorkbook();
          String fileName = "三宝盈亏汇总";
          // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
          HSSFSheet sheet = wb.createSheet(fileName);
          // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
          HSSFRow row = sheet.createRow((int) 0);
          // 4.创建单元格，设置值表头，设置表头居中
          HSSFCellStyle style = wb.createCellStyle();
          // 居中格式
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

          // 设置表头
          HSSFCell cell = row.createCell(0);
          cell.setCellValue(header[0]);
          cell.setCellStyle(style);

          cell = row.createCell(1);
          cell.setCellValue(header[1]);
          cell.setCellStyle(style);

          cell = row.createCell(2);
          cell.setCellValue(header[2]);
          cell.setCellStyle(style);

          cell = row.createCell(3);
          cell.setCellValue(header[3]);
          cell.setCellStyle(style);

          cell = row.createCell(4);
          cell.setCellValue(header[4]);
          cell.setCellStyle(style);

          cell = row.createCell(5);
          cell.setCellValue(header[5]);
          cell.setCellStyle(style);

          cell = row.createCell(6);
          cell.setCellValue(header[6]);
          cell.setCellStyle(style);

          for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            TriratnaRepresentation triratnaRepresentation= list.get(i);
            // 创建单元格，设置值
            row.createCell(0).setCellValue(triratnaRepresentation.getBoots());
            row.createCell(1).setCellValue(triratnaRepresentation.getPlayer_pair()+"");
            row.createCell(2).setCellValue(triratnaRepresentation.getBank_pair()+"");
            row.createCell(3).setCellValue(triratnaRepresentation.getDraw()+"");
            row.createCell(4).setCellValue(triratnaRepresentation.getPlayer_pair()+""+triratnaRepresentation.getBank_pair()+""+triratnaRepresentation.getDraw());
            row.createCell(5).setCellValue(triratnaRepresentation.getTriratna_profit()+"");
            if(triratnaRepresentation.getTriratna_profit().compareTo(new BigDecimal(0)) > 0){
              row.createCell(6).setCellValue(0);
            }else{
              row.createCell(6).setCellValue(triratnaRepresentation.getTriratna_profit().abs()+"");
            }

          }
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          wb.write(os);
          byte[] content = os.toByteArray();
          InputStream is = new ByteArrayInputStream(content);
          // 设置response参数，可以打开下载页面
          response.setContentType("application/vnd.ms-excel;charset=utf-8");
          response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
          ServletOutputStream out = response.getOutputStream();
          BufferedInputStream bis = null;
          BufferedOutputStream bos = null;
          System.out.println("33333");
          try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
              bos.write(buff, 0, bytesRead);
            }
          } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
          } finally {
            if (bis != null)
              bis.close();
            if (bos != null) {
              bos.flush();
              bos.close();
            }
            System.out.println("44444");
          }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
