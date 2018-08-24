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
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import bjl.application.triratna.representation.TriratnaRepresentation;

public class GenerateExcelUtils {

  public static void exportTriratna(HttpServletResponse response,String fileName,String[] header,List list){
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
      createCell(sheet,row,list); //创建内容
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

  private static void createCell(HSSFSheet sheet,HSSFRow row,List list){

    //      List<TriratnaRepresentation> list = null;
    if(list != null && list.size() > 0){
      for (int i = 0; i < list.size(); i++) {
        row = sheet.createRow(i + 1);
        TriratnaRepresentation triratnaRepresentation = (TriratnaRepresentation)list.get(i);
        // 创建单元格，设置值
        row.createCell(0).setCellValue(triratnaRepresentation.getBoots());
        row.createCell(1).setCellValue(triratnaRepresentation.getPlayer_pair() + "");
        row.createCell(2).setCellValue(triratnaRepresentation.getBank_pair() + "");
        row.createCell(3).setCellValue(triratnaRepresentation.getDraw() + "");
        row.createCell(4).setCellValue(triratnaRepresentation.getPlayer_pair() + "" + triratnaRepresentation.getBank_pair() + "" + triratnaRepresentation.getDraw());
        row.createCell(5).setCellValue(triratnaRepresentation.getTriratna_profit() + "");
        if (triratnaRepresentation.getTriratna_profit().compareTo(new BigDecimal(0)) > 0) {
          row.createCell(6).setCellValue(0);
        } else {
          row.createCell(6).setCellValue(triratnaRepresentation.getTriratna_profit().abs() + "");
        }
      }
    }
  }
}
