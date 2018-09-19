/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package bjl.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.constants.VotoContants;
import bjl.domain.model.agent.AgentProfit;

public class GenerateAgentExcelUtils {

  public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public static void exportExcel(HttpServletResponse response,String fileName,String[] header,List list,Object object,String flag,String agent,ListAgentProfitCommand command){
    try{


      HSSFWorkbook wb = new HSSFWorkbook();
      // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
      HSSFSheet sheet = wb.createSheet(fileName);
      // 4.创建单元格，设置值表头，设置表头居中
      HSSFCellStyle style = wb.createCellStyle();
      // 居中格式
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short

      HSSFRow row0 = sheet.createRow(0);
      HSSFCell cell = row0.createCell(0);
      cell.setCellValue("代理");
      cell.setCellStyle(style);
      cell = row0.createCell(1);
      cell.setCellValue(agent);
      cell.setCellStyle(style);
      cell = row0.createCell(2);
      cell.setCellValue("");
      cell.setCellStyle(style);
      cell = row0.createCell(3);
      cell.setCellValue("手续费");
      cell.setCellStyle(style);
      cell = row0.createCell(4);
      cell.setCellValue("");
      cell.setCellStyle(style);


      HSSFRow row1 = sheet.createRow(1);
      cell = row1.createCell(0);
      cell.setCellValue("日期");
      cell.setCellStyle(style);
      cell = row1.createCell(1);
      String date = "";
      if(command.getStartDate()!= null && !"".equals(command.getStartDate())){
        //2018-03-15 11:32:49
        String startDate = command.getStartDate();
        String start = startDate.substring(0,10);
        date +=start;
      }
      if(command.getEndDate()!= null && !"".equals(command.getEndDate())){
        //2018-03-15 11:32:49
        String endDate = command.getStartDate();
        String end = endDate.substring(0,10);
        date +="--"+end;
      }else{
        String end = simpleDateFormat.format(new Date());
        date +="--"+end;
      }
      cell.setCellValue(date);
      cell.setCellStyle(style);
      cell = row1.createCell(2);
      cell.setCellValue("总转码");
      cell.setCellStyle(style);
      cell = row1.createCell(3);
      cell.setCellValue("");
      cell.setCellStyle(style);

      HSSFRow row2 = sheet.createRow(2);
      cell = row2.createCell(0);
      cell.setCellValue("总支付");
      cell.setCellStyle(style);
      cell = row2.createCell(1);
      cell.setCellValue("");
      cell.setCellStyle(style);

      HSSFRow row = sheet.createRow(3);


      // 设置表头
      if(header != null && header.length > 0){
        for(int j = 0,length = header.length; j<length; j++){
          cell = row.createCell(j);
          cell.setCellValue(header[j]);
          cell.setCellStyle(style);
        }
      }
      createCell(sheet,row,list,object,flag); //创建内容
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
      try {
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
          bos.write(buff, 0, bytesRead);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (bis != null)
          bis.close();
        if (bos != null) {
          bos.flush();
          bos.close();
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void createCell(HSSFSheet sheet,HSSFRow row,List list,Object object,String flag){

    if(list != null && list.size() > 0) {
      if (VotoContants.AGENT_PROFIT_EXCEL.equals(flag)) {
        //代理收益
        for (int i = 0; i < list.size(); i++) {
          row = sheet.createRow(i + 4);
          AgentProfit agentProfit = (AgentProfit) list.get(i);
          if (agentProfit.getPlace() == 1) {
            row.createCell(0).setCellValue("微投");
          } else if (agentProfit.getPlace() == 2) {
            row.createCell(0).setCellValue("电投");
          }
          row.createCell(1).setCellValue(agentProfit.getPlay().getName());
          row.createCell(2).setCellValue(agentProfit.getFirstAgent() == null ? "" : agentProfit.getFirstAgent().getName());
          row.createCell(3).setCellValue(agentProfit.getSecondAgent() == null ? "" : agentProfit.getSecondAgent().getName());
          row.createCell(4).setCellValue(agentProfit.getLoseScore() == null ? "0" : agentProfit.getLoseScore().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(5).setCellValue(agentProfit.getIntervalScore() == null ? "0" : agentProfit.getIntervalScore().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(6).setCellValue(agentProfit.getSupHigh() == null ? "0" : agentProfit.getSupHigh().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(7).setCellValue(agentProfit.getSupRatio() == null ? "0" : agentProfit.getSupRatio().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(8).setCellValue(agentProfit.getSubHigh() == null ? "0" : agentProfit.getSubHigh().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(9).setCellValue(agentProfit.getSubRatio() == null ? "0" : agentProfit.getSubRatio().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(10).setCellValue(agentProfit.getComRatio() == null ? "0" : agentProfit.getComRatio().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(11).setCellValue("平台交收");
          row.createCell(12).setCellValue(agentProfit.getAddScore() == null ? "0" : agentProfit.getAddScore().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(13).setCellValue(agentProfit.getRechargeScore() == null ? "0" : agentProfit.getRechargeScore().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(14).setCellValue(agentProfit.getFirstFee() == null ? "0" : agentProfit.getFirstFee().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(15).setCellValue(agentProfit.getSecondFee() == null ? "0" : agentProfit.getSecondFee().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(16).setCellValue(agentProfit.getComFee() == null ? "0" : agentProfit.getComFee().setScale(2, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(17).setCellValue(agentProfit.getSupIncome() == null ? "0" : agentProfit.getSupIncome().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(18).setCellValue(agentProfit.getSubIncome() == null ? "0" : agentProfit.getSubIncome().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(19).setCellValue(agentProfit.getComIncome() == null ? "0" : agentProfit.getComIncome().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(20).setCellValue(agentProfit.getSupR() == null ? "0" : agentProfit.getSupR().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(21).setCellValue(agentProfit.getSubR() == null ? "0" : agentProfit.getSubR().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(22).setCellValue(agentProfit.getPlayR() == null ? "0" : agentProfit.getPlayR().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(23).setCellValue(agentProfit.getSupBalance() == null ? "0" : agentProfit.getSupBalance().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(24).setCellValue(agentProfit.getSubBalance() == null ? "0" : agentProfit.getSubBalance().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          ;
          row.createCell(25).setCellValue(agentProfit.getPlayBalance() == null ? "0" : agentProfit.getPlayBalance().setScale(2, BigDecimal.ROUND_HALF_UP).longValue() + "");
        }

      }
    }
  }
}
