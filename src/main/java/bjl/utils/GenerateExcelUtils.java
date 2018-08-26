/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package bjl.utils;

import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.application.triratna.command.TotalTriratna;
import bjl.constants.VotoContants;
import bjl.domain.model.agent.AgentProfit;
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
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import bjl.application.triratna.representation.TriratnaRepresentation;

public class GenerateExcelUtils {

  public static void exportExcel(HttpServletResponse response,String fileName,String[] header,List list,Object object,String flag){
    try{

      HSSFWorkbook wb = new HSSFWorkbook();
      // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
      HSSFSheet sheet = wb.createSheet(fileName);
      // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
      HSSFRow row = sheet.createRow(0);
      // 4.创建单元格，设置值表头，设置表头居中
      HSSFCellStyle style = wb.createCellStyle();
      // 居中格式
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

      // 设置表头
      HSSFCell cell = null;
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

    //      List<TriratnaRepresentation> list = null;
    if(list != null && list.size() > 0) {
      if (VotoContants.TRIRATNA_EXCEL.equals(flag)) {
        for (int i = 0; i < list.size(); i++) {
          row = sheet.createRow(i + 1);
          TriratnaRepresentation triratnaRepresentation = (TriratnaRepresentation) list.get(i);
          // 创建单元格，设置值
          // long l  = bd.setScale( 0, BigDecimal.ROUND_UP ).longValue(); // 向上取整
          row.createCell(0).setCellValue(triratnaRepresentation.getUserName());
          row.createCell(1).setCellValue(triratnaRepresentation.getBoots() + "靴" + triratnaRepresentation.getGames() + "局");
          row.createCell(2).setCellValue(triratnaRepresentation.getPlayer_pair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(3).setCellValue(triratnaRepresentation.getBank_pair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(4).setCellValue(triratnaRepresentation.getDraw().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(5).setCellValue(triratnaRepresentation.getPlayer_pair().add(triratnaRepresentation.getBank_pair()).add(triratnaRepresentation.getDraw()).setScale(0).longValue() + "");
          row.createCell(6).setCellValue(triratnaRepresentation.getTriratna_profit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          if (triratnaRepresentation.getTriratna_profit().compareTo(new BigDecimal(0)) > 0) {
            row.createCell(7).setCellValue(0);
          } else {
            row.createCell(7).setCellValue(triratnaRepresentation.getTriratna_profit().abs().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          }
        }
        TotalTriratna totalTriratna = (TotalTriratna)object;
        if(totalTriratna != null) {
          row = sheet.createRow(list.size() + 1);
          row.createCell(0).setCellValue("");
          row.createCell(1).setCellValue("总计");
          row.createCell(2).setCellValue(totalTriratna.getPlayerPair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(3).setCellValue(totalTriratna.getBankPair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(4).setCellValue(totalTriratna.getDraw().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(5).setCellValue(totalTriratna.getPlayerPair().add(totalTriratna.getBankPair()).add(totalTriratna.getDraw()).setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(6).setCellValue(totalTriratna.getTriratnaProfit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          if (totalTriratna.getTriratnaProfit().compareTo(new BigDecimal(0)) > 0) {
            row.createCell(7).setCellValue(0);
          } else {
            row.createCell(7).setCellValue("-" + totalTriratna.getTriratnaProfit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          }
        }
      } else if (VotoContants.AGENT_PROFIT_EXCEL.equals(flag)) {
        //代理收益
        for (int i = 0; i < list.size(); i++) {
          row = sheet.createRow(i + 1);
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

      } else if (VotoContants.GAME_DETAIL_EXCEL.equals(flag)) {
        //代理收益
        for (int i = 0; i < list.size(); i++) {
          row = sheet.createRow(i + 1);
          GameDetailedRepresentation gameDetailedRepresentation = (GameDetailedRepresentation) list.get(i);
          row.createCell(0).setCellValue(gameDetailedRepresentation.getXjNum());
          row.createCell(1).setCellValue(gameDetailedRepresentation.getName());
          if (gameDetailedRepresentation.getHallType() == 1) {
            row.createCell(2).setCellValue("菲律宾厅");
          } else if (gameDetailedRepresentation.getHallType() == 2) {
            row.createCell(2).setCellValue("越南厅");
          } else {
            row.createCell(2).setCellValue("澳门厅");
          }
          row.createCell(3).setCellValue(gameDetailedRepresentation.getPlayer() == null ? "0" : gameDetailedRepresentation.getPlayer().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(4).setCellValue(gameDetailedRepresentation.getBanker() == null ? "0" : gameDetailedRepresentation.getBanker().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(5).setCellValue(gameDetailedRepresentation.getPlayerPair() == null ? "0" : gameDetailedRepresentation.getPlayerPair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(6).setCellValue(gameDetailedRepresentation.getBankPair() == null ? "0" : gameDetailedRepresentation.getBankPair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(7).setCellValue(gameDetailedRepresentation.getDraw() == null ? "0" : gameDetailedRepresentation.getDraw().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(8).setCellValue(gameDetailedRepresentation.getBankPlayProfit() == null ? "0" : gameDetailedRepresentation.getBankPlayProfit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(9).setCellValue(gameDetailedRepresentation.getTriratnaProfit() == null ? "0" : gameDetailedRepresentation.getTriratnaProfit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(10).setCellValue(gameDetailedRepresentation.getEffective() == null ? "0" : gameDetailedRepresentation.getEffective().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(11).setCellValue(gameDetailedRepresentation.getBankPlayLose() == null ? "0" : gameDetailedRepresentation.getBankPlayLose().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
          row.createCell(12).setCellValue(gameDetailedRepresentation.getTriratnaLose() == null ? "0" : gameDetailedRepresentation.getTriratnaLose().setScale(0, BigDecimal.ROUND_HALF_UP).longValue() + "");
        }

        TotalGameDetailedCommand totalGameDetailedCommand = (TotalGameDetailedCommand) object;
        if(totalGameDetailedCommand != null) {
          row = sheet.createRow(list.size() + 1);
          row.createCell(0).setCellValue("合计");
          row.createCell(1).setCellValue(list.size());
          row.createCell(2).setCellValue("---");
          row.createCell(3).setCellValue(totalGameDetailedCommand.getPlayer()==null ? 0:totalGameDetailedCommand.getPlayer().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(4).setCellValue(totalGameDetailedCommand.getBanker()==null ? 0:totalGameDetailedCommand.getBanker().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(5).setCellValue(totalGameDetailedCommand.getPlayerPair()==null ? 0:totalGameDetailedCommand.getPlayerPair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(6).setCellValue(totalGameDetailedCommand.getBankPair()==null ? 0:totalGameDetailedCommand.getBankPair().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(7).setCellValue(totalGameDetailedCommand.getDraw()==null ? 0:totalGameDetailedCommand.getDraw().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(8).setCellValue(totalGameDetailedCommand.getBankPlayProfit()==null ? 0:totalGameDetailedCommand.getBankPlayProfit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(9).setCellValue(totalGameDetailedCommand.getTriratnaProfit()==null ? 0:totalGameDetailedCommand.getTriratnaProfit().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(10).setCellValue(totalGameDetailedCommand.getEffective()==null ? 0:totalGameDetailedCommand.getEffective().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(11).setCellValue(totalGameDetailedCommand.getBankPlayLose()==null ? 0:totalGameDetailedCommand.getBankPlayLose().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
          row.createCell(12).setCellValue(totalGameDetailedCommand.getTriratnaLose()==null ? 0:totalGameDetailedCommand.getTriratnaLose().setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
        }
      }
    }
  }
}
