package com.trendcote.web.controller.business;


import com.trendcote.web.dto.page.Json;
import com.trendcote.web.entity.business.ServiceStaffInfo;
import com.trendcote.web.service.business.IServiceStaffService;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



public class ExcelImport extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(ExcelImport.class);

    public static List<ServiceStaffInfo> parseExcel(String path) {
        List<ServiceStaffInfo> list = new ArrayList<ServiceStaffInfo>();
        File file = null;
        InputStream input = null;
        if (path !=null && path.length() >7){
            //判断文件是否是excel
            String suffix = path.substring(path.lastIndexOf("."),path.length());
            file = new File(path);
            try {
                input = new FileInputStream(file);
            } catch (FileNotFoundException e) {
               logger.error("未找到指定的Excel文件!");
            }
            if (".xls".equals(suffix)){
                POIFSFileSystem fileSystem = null;
                HSSFWorkbook workbook = null;
                try {
                    fileSystem = new POIFSFileSystem(input);
                    workbook = new HSSFWorkbook(fileSystem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取第一个工作薄
                HSSFSheet sheet = workbook.getSheetAt(0);
                list =getContent((Sheet)sheet);
            }else if (".xlsx".equals(suffix)){
                XSSFWorkbook workbook =null;
                try {
                    workbook = new XSSFWorkbook(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                XSSFSheet sheet = workbook.getSheetAt(0);
                list = getContent(sheet);
            }
        }else{
            logger.error("非法的文件路径！");
        }
        return list;

    }


    public static List<ServiceStaffInfo> getContent(Sheet sheet){
        List<ServiceStaffInfo> list = new ArrayList<ServiceStaffInfo>();
        //获取表格的总行数
        int excelCount = sheet.getPhysicalNumberOfRows();
        //遍历数据，略过标题，从第二行开始
        for (int i= 1;i< excelCount;i++){
            //new 一个对象
            ServiceStaffInfo staffInfo =new ServiceStaffInfo();
            Row row = sheet.getRow(i);
            //单元格
            int cellcount = row.getPhysicalNumberOfCells();
            boolean isnull = false;
            for (int j=0;j<cellcount;j++){
                Cell cell = row.getCell(j);
                switch (j){
                    case 0:
                        isnull=false;
                        try{
                            String teme = cell.getStringCellValue();
                        }catch (Exception e){
                            if (e.toString().indexOf("NullPointerException")>0){
                                isnull =true;
                            }
                        }
                        if (isnull){
                            break;
                        }
                        System.out.println("============"+cell.getCellType());
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            staffInfo.setServiceStaffIcNo(cell.getStringCellValue());
                        }else{
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                                DecimalFormat df = new DecimalFormat("0");
                                String value = df.format(cell.getNumericCellValue());
                                staffInfo.setServiceStaffIcNo(value);
                            }
                        }
                            break;
                    case 1:
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING){
                            staffInfo.setServiceStaffName(cell.getStringCellValue());
                        }
                        break;
                }
                if (isnull){
                    break;
                }

            }
            if (isnull){
                break;
            }
            list.add(staffInfo);
        }
        return list;
    }

}
