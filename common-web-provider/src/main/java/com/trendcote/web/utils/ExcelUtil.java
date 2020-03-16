package com.trendcote.web.utils;

import com.trendcote.web.dto.poi.StaffDailyRec;
import com.trendcote.web.entity.business.StaffAccessInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystem;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description 处理Excel
 * @Date 2019/9/9 15:42
 * @Created by xym
 */

public class ExcelUtil {

    /**
     *@Description 出入记录导出报表
     *@param  sheetName sheet1名  listStaffInfos 访问记录对象   StaffDailyRec 每天出入记录统计对象
     *@return 
     */
    // 数据导出为Excel  ( Sheet 1 为访问记录  Sheet 2为记录统计  )
    public static void generateExcel(String sheetName , String sheet2Name , List<StaffAccessInfo> listStaffInfos , StaffDailyRec rec) {
        String path = "";
        try {
            // 创建workbook
            HSSFWorkbook workbook = new HSSFWorkbook();                         // 创建工作簿对象
            // 添加sheet 1
            HSSFSheet sheet = workbook.createSheet(sheetName);                  // 创建工作表
            sheet.setColumnWidth(0,5000);
            sheet.setColumnWidth(1,5000);
            sheet.setColumnWidth(2,5000);
            sheet.setColumnWidth(3,5000);
            sheet.setColumnWidth(4,5000);
            sheet.setColumnWidth(5,5000);
            // 添加sheet 2
            HSSFSheet sheet2 = workbook.createSheet(sheet2Name);  // 创建工作表
            sheet2.setColumnWidth(0,5000);
            sheet2.setColumnWidth(1,5000);
            sheet2.setColumnWidth(2,5000);

            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            HSSFRow rowSheet2 = sheet2.createRow(0);

            // 标题行格式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            HSSFFont font = workbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);                            // 字体
            style.setFillForegroundColor((short) 13);       // 背景色
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFCell cell = row.createCell(0);
            cell.setCellValue("员工姓名");
            cell.setCellStyle(style);
            cell = row.createCell( 1);
            cell.setCellValue("员工卡号");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("刷卡时间");
            cell.setCellStyle(style);
            cell = row.createCell( 3);
            cell.setCellValue("认证方式");
            cell.setCellStyle(style);
            cell = row.createCell( 4);
            cell.setCellValue("员工体温");
            cell.setCellStyle(style);
            cell = row.createCell( 5);
            cell.setCellValue("员工状态");
            cell.setCellStyle(style);

            HSSFCell cellSheet2 = rowSheet2.createCell(0);
            cellSheet2.setCellValue("认证方式");
            cellSheet2.setCellStyle(style);
            cellSheet2 = rowSheet2.createCell( 1);
            cellSheet2.setCellValue("人数");
            cellSheet2.setCellStyle(style);
            cellSheet2 = rowSheet2.createCell(2);
            cellSheet2.setCellValue("占比");
            cellSheet2.setCellStyle(style);


            // 数据进行填充
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            for( int i = 0 ; i < listStaffInfos.size() ; i++ ){
                // 一条数据一个Row
                HSSFRow rowIndex = sheet.createRow(i + 1);
                StaffAccessInfo staffAccessInfo = listStaffInfos.get(i);
                // 创建单元格
                HSSFCell cell1 = rowIndex.createCell(0);
                HSSFCell cell2 = rowIndex.createCell(1);
                HSSFCell cell3 = rowIndex.createCell(2);
                HSSFCell cell4 = rowIndex.createCell(3);
                HSSFCell cell5 = rowIndex.createCell(4);
                HSSFCell cell6 = rowIndex.createCell(5);
                cell1.setCellValue(staffAccessInfo.getStaffName());
                cell1.setCellStyle(cellStyle);
                cell2.setCellValue(staffAccessInfo.getStaffCode());
                cell2.setCellStyle(cellStyle);
                cell3.setCellValue( new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(staffAccessInfo.getInTime()) );
                cell3.setCellStyle(cellStyle);

                int inPersonType = staffAccessInfo.getInPersonType();   // 1. 身份证 2. 员工IC卡 3.人脸
                if( inPersonType == 1 ){
                    cell4.setCellValue("身份证");
                } else if( inPersonType == 2){
                    cell4.setCellValue("员工卡");
                } else {
                    cell4.setCellValue("人脸");
                }
                cell4.setCellStyle(cellStyle);

                DecimalFormat decimalFormat =new DecimalFormat(".00");
                String tiwen = decimalFormat.format(staffAccessInfo.getTemperature());
                cell5.setCellValue(tiwen);

                int accessStatus = staffAccessInfo.getAccessStatus();
                if( accessStatus == 0 ){
                    cell6.setCellValue("进入");
                } else if( accessStatus == 1){
                    cell6.setCellValue("离开");
                } else {
                    cell6.setCellValue("已通过");
                }
                cell6.setCellStyle(cellStyle);
            }

            for( int i = 1 ; i <= 5 ; i++ ){
                HSSFRow rowIndex = sheet2.createRow(i);
                // 创建单元格
                HSSFCell cell1 = rowIndex.createCell(0);
                HSSFCell cell2 = rowIndex.createCell(1);
                HSSFCell cell3 = rowIndex.createCell(2);
                if( i == 1){
                    // 人脸识别
                    cell1.setCellValue("人脸识别");
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellValue(rec.getFaceSum());
                    cell2.setCellStyle(cellStyle);
                    cell3.setCellValue(DataUtil.division(rec.getFaceSum(),rec.getSum()));
                    cell3.setCellStyle(cellStyle);
                }else if( i == 2){
                    // 员工IC卡
                    cell1.setCellValue("员工卡");
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellValue(rec.getIcSum());
                    cell2.setCellStyle(cellStyle);
                    cell3.setCellValue(DataUtil.division(rec.getIcSum(),rec.getSum()));
                    cell3.setCellStyle(cellStyle);
                }else if( i == 3){
                    // 身份证
                    cell1.setCellValue("身份证");
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellValue(rec.getIdcardSum());
                    cell2.setCellStyle(cellStyle);
                    cell3.setCellValue(DataUtil.division(rec.getIdcardSum(),rec.getSum()));
                    cell3.setCellStyle(cellStyle);
                }else if( i == 4){

                }else if( i == 5){
                    // 总计
                    cell1.setCellValue("当日总计");
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellValue(rec.getSum());
                    cell2.setCellStyle(cellStyle);
                }
            }


            if (workbook != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
                String suffix = sdf.format(new Date());
                FileSystemView fsv = FileSystemView.getFileSystemView();//获取本的桌面路径
               //path = System.getProperty("user.dir")  + File.separator + suffix+".xls" ;
               path=fsv.getHomeDirectory().toString()+File.separator+suffix+"员工出入记录"+".xls";
                // 生成文件到指定路径
                FileOutputStream fout = new FileOutputStream(path);
                workbook.write(fout);
                fout.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
