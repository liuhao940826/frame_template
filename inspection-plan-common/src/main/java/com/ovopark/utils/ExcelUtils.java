package com.ovopark.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description: 渠道类型
 * @Author: jeecg-boot
 * @Date: 2019-11-26
 * @Version: V1.0
 */
public class ExcelUtils {


    /**
     * 新建Excel文件，New Workbook
     * @param excelType 可为null，Excel版本，可为2003（.xls）或者2007（.xlsx）,默认为2003版本
     * @param sheetName 新建表单名称
     * @param headList 表头List集合
     * @param dataList 数据List<List<集合>>(行<列>)
     * @param path 新建excel路径
     * @return
     */
    public static boolean createWorkBook(String excelType, String sheetName, List<String> headList, List<List<String>> dataList, String path){
        Workbook wb = null;
        /*创建文件*/
        if (excelType == null  || excelType.endsWith("2007")){
            /*XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx */
            wb = new XSSFWorkbook();
        }else if (excelType.endsWith("2003")) {
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        } else {   //默认为2003版本
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        }
        /*Excel文件创建完毕*/
        CreationHelper createHelper = wb.getCreationHelper();  //创建帮助工具

        /*创建表单*/
        Sheet sheet = wb.createSheet(sheetName!=null?sheetName:"new sheet");

        // Note that sheet name is Excel must not exceed 31 characters（注意sheet的名字的长度不能超过31个字符，若是超过的话，会自动截取前31个字符）
        // and must not contain any of the any of the following characters:（不能包含下列字符）
        // 0x0000  0x0003  colon (:)  backslash (\)  asterisk (*)  question mark (?)  forward slash (/)  opening square bracket ([)  closing square bracket (])
        /*若是包含的话，会报错。但有一个解决此问题的方法，
        就是调用WorkbookUtil的createSafeSheetName(String nameProposal)方法来创建sheet name,
        若是有如上特殊字符，它会自动用空字符来替换掉，自动过滤。*/
        /*String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "过滤掉上面出现的不合法字符
        Sheet sheet3 = workbook.createSheet(safeName); //然后就创建成功了*/
        /*表单创建完毕*/

        //设置字体
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short)14);
        headFont.setFontName("Courier New");
        headFont.setItalic(false);
        headFont.setStrikeout(false);
        //设置头部单元格样式
        CellStyle headStyle = wb.createCellStyle();
//        headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
//        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
//        headStyle.setBorderLeft(BorderStyle.THICK);
//        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        headStyle.setBorderRight(BorderStyle.THICK);
//        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        headStyle.setBorderTop(BorderStyle.THICK);
//        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        headStyle.setAlignment(HorizontalAlignment.CENTER);    //设置水平对齐方式
//        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
        //headStyle.setShrinkToFit(true);  //自动伸缩
//        headStyle.setFont(headFont);  //设置字体
        /*设置数据单元格格式*/
        CellStyle dataStyle = wb.createCellStyle();
//        dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
//        dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
//        dataStyle.setBorderLeft(BorderStyle.THIN);
//        dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        dataStyle.setBorderRight(BorderStyle.THIN);
//        dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        dataStyle.setBorderTop(BorderStyle.THIN);
//        dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
//        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
        DataFormat dataFormat = wb.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));
        //dataStyle.setShrinkToFit(true);  //自动伸缩
        /*创建行Rows及单元格Cells*/
        Row headRow = sheet.createRow(0); //第一行为头
        for (int i=0;i<headList.size();i++){  //遍历表头数据
            Cell cell = headRow.createCell(i);  //创建单元格
            cell.setCellValue(createHelper.createRichTextString(headList.get(i)));  //设置值
            cell.setCellStyle(headStyle);  //设置样式
        }

        int rowIndex = 1;  //当前行索引
        //创建Rows
        for (List<String> rowdata : dataList){ //遍历所有数据
            Row row = sheet.createRow(rowIndex++); //第一行为头
            for (int j = 0;j< rowdata.size();j++){  //编译每一行
                Cell cell = row.createCell(j);
                cell.setCellStyle(dataStyle);
                //设置文本格式
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(createHelper.createRichTextString(rowdata.get(j)));
            }
        }
        /*创建rows和cells完毕*/
        CellStyle textStyle =  wb.createCellStyle();
        DataFormat textFormat = wb.createDataFormat();
        textStyle.setDataFormat(textFormat.getFormat("@"));
        /*设置列自动对齐*/
        for (int i =0;i<headList.size();i++){
            sheet.autoSizeColumn(i);
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            //设置猎德单元格式文本
            sheet.setDefaultColumnStyle(i,textStyle);
        }

        File file = new File(path);

        if(!file.exists()){
            File parentFile = file.getParentFile();

            boolean ismk = parentFile.mkdirs();
            System.out.println("创建文件的结果:"+ismk);

        }

        try  (OutputStream fileOut = new FileOutputStream(path)) {    //获取文件流
            wb.write(fileOut);   //将workbook写入文件流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 新建Excel文件，New Workbook
     * @param excelType 可为null，Excel版本，可为2003（.xls）或者2007（.xlsx）,默认为2003版本
     * @param sheetName 新建表单名称
     * @param headList 表头List集合
     * @param dataList 数据List<List<集合>>(行<列>)
     * @return
     */
    public static boolean createWorkBook(HttpServletResponse response, String excelType, String sheetName, List<String> headList, List<List<String>> dataList) throws IOException {
        response.setContentType("application/binary;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(StringUtils.isBlank(sheetName)?"":sheetName.replace(" ", "") +  ".xlsx", "utf-8"));
        OutputStream outputStream = response.getOutputStream();

        Workbook wb = null;
        /*创建文件*/
        if (excelType == null  || excelType.endsWith("2007")){
            /*XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx */
            wb = new XSSFWorkbook();
        }else if (excelType.endsWith("2003")) {
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        } else {   //默认为2003版本
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        }
        /*Excel文件创建完毕*/
        CreationHelper createHelper = wb.getCreationHelper();  //创建帮助工具

        /*创建表单*/
        Sheet sheet = wb.createSheet(sheetName!=null?sheetName:"new sheet");

        //设置字体
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short)14);
        headFont.setFontName("Courier New");
        headFont.setItalic(false);
        headFont.setStrikeout(false);
        //设置头部单元格样式
        CellStyle headStyle = wb.createCellStyle();
        /*设置数据单元格格式*/
        CellStyle dataStyle = wb.createCellStyle();
        DataFormat dataFormat = wb.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));
        //dataStyle.setShrinkToFit(true);  //自动伸缩
        /*创建行Rows及单元格Cells*/
        Row headRow = sheet.createRow(0); //第一行为头
        for (int i=0;i<headList.size();i++){  //遍历表头数据
            Cell cell = headRow.createCell(i);  //创建单元格
            cell.setCellValue(createHelper.createRichTextString(headList.get(i)));  //设置值
            cell.setCellStyle(headStyle);  //设置样式
        }

        int rowIndex = 1;  //当前行索引
        //创建Rows
        for (List<String> rowdata : dataList){ //遍历所有数据
            Row row = sheet.createRow(rowIndex++); //第一行为头
            for (int j = 0;j< rowdata.size();j++){  //编译每一行
                Cell cell = row.createCell(j);
                cell.setCellStyle(dataStyle);
                //设置文本格式
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(createHelper.createRichTextString(rowdata.get(j)));
            }
        }
        /*创建rows和cells完毕*/
        CellStyle textStyle =  wb.createCellStyle();
        DataFormat textFormat = wb.createDataFormat();
        textStyle.setDataFormat(textFormat.getFormat("@"));
        /*设置列自动对齐*/
        for (int i =0;i<headList.size();i++){
            sheet.autoSizeColumn(i);
            // 解决自动设置列宽中文失效的问题
//            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            //设置猎德单元格式文本
            sheet.setDefaultColumnStyle(i,textStyle);
        }

        try {
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            outputStream.close();
        }

        return true;
    }


    /**
     * 新建Excel文件，New Workbook
     * @param excelType 可为null，Excel版本，可为2003（.xls）或者2007（.xlsx）,默认为2003版本
     * @param sheetName 新建表单名称
     * @param headList 表头List集合
     * @param dataList 数据List<List<集合>>(行<列>)
     * @return
     */
    public static boolean createLinkWorkBook(HttpServletResponse response, String excelType, String sheetName, List<String> headList, List<List<String>> dataList, List<Integer> linkCellList) throws IOException {
        response.setContentType("application/binary;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(StringUtils.isBlank(sheetName)?"":sheetName.replace(" ", "") +  ".xlsx", "utf-8"));
        OutputStream outputStream = response.getOutputStream();

        Workbook wb = null;
        /*创建文件*/
        if (excelType == null  || excelType.endsWith("2007")){
            /*XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx */
            wb = new XSSFWorkbook();
        }else if (excelType.endsWith("2003")) {
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        } else {   //默认为2003版本
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        }
        /*Excel文件创建完毕*/
        CreationHelper createHelper = wb.getCreationHelper();  //创建帮助工具

        /*创建表单*/
        Sheet sheet = wb.createSheet(sheetName!=null?sheetName:"new sheet");

        //设置字体
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short)14);
        headFont.setFontName("Courier New");
        headFont.setItalic(false);
        headFont.setStrikeout(false);
        //设置头部单元格样式
        CellStyle headStyle = wb.createCellStyle();
        /*设置数据单元格格式*/
        CellStyle dataStyle = wb.createCellStyle();
        DataFormat dataFormat = wb.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));
        //dataStyle.setShrinkToFit(true);  //自动伸缩
        /*创建行Rows及单元格Cells*/
        Row headRow = sheet.createRow(0); //第一行为头
        for (int i=0;i<headList.size();i++){  //遍历表头数据
            Cell cell = headRow.createCell(i);  //创建单元格
            cell.setCellValue(createHelper.createRichTextString(headList.get(i)));  //设置值
            cell.setCellStyle(headStyle);  //设置样式
        }

        //设置超链接格式
        CellStyle hlinkStyle = wb.createCellStyle();
        Font hlinkFont = wb.createFont();
        hlinkFont.setUnderline(Font.U_SINGLE);
        hlinkFont.setFontHeightInPoints((short)12);
        hlinkFont.setColor(IndexedColors.BLUE.getIndex());
        hlinkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        hlinkStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        hlinkStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        hlinkStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        hlinkStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        hlinkStyle.setFont(hlinkFont);


        Boolean linkEmptyFlag = false;

        if(!CollectionUtils.isEmpty(linkCellList)){
            linkEmptyFlag =true;
        }

        int rowIndex = 1;  //当前行索引
        //创建Rows
        for (List<String> rowdata : dataList){ //遍历所有数据
            Row row = sheet.createRow(rowIndex++); //第一行为头
            for (int j = 0;j< rowdata.size();j++){  //编译每一行
                Cell cell = row.createCell(j);

                if(linkEmptyFlag && linkCellList.contains(j)){
                    cell.setCellStyle(hlinkStyle);
                }else {
                    cell.setCellStyle(dataStyle);
                }
                //设置文本格式
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(createHelper.createRichTextString(rowdata.get(j)));
            }
        }
        /*创建rows和cells完毕*/
        CellStyle textStyle =  wb.createCellStyle();
        DataFormat textFormat = wb.createDataFormat();
        textStyle.setDataFormat(textFormat.getFormat("@"));
        /*设置列自动对齐*/
        for (int i =0;i<headList.size();i++){
            sheet.autoSizeColumn(i);
            // 解决自动设置列宽中文失效的问题
//            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            //设置猎德单元格式文本
            sheet.setDefaultColumnStyle(i,textStyle);
        }

        try {
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            outputStream.close();
        }

        return true;
    }


    /**
     * 这个方法不是通用的 不要用
     * @param response
     * @param excelType
     * @param sheetName
     * @param headList
     * @param dataList
     * @param linkCellList
     * @return
     * @throws IOException
     */
    public static boolean createLinkWorkBookForExpandExport(HttpServletResponse response, String excelType, String sheetName, List<String> headList, List<List<String>> dataList, List<Integer> linkCellList, Integer newline) throws IOException {
        response.setContentType("application/binary;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(StringUtils.isBlank(sheetName)?"":sheetName.replace(" ", "") +  ".xlsx", "utf-8"));
        OutputStream outputStream = response.getOutputStream();

        Workbook wb = null;
        /*创建文件*/
        if (excelType == null  || excelType.endsWith("2007")){
            /*XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx */
            wb = new XSSFWorkbook();
        }else if (excelType.endsWith("2003")) {
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        } else {   //默认为2003版本
            /*操作Excel2003以前（包括2003）的版本，扩展名是.xls */
            wb = new HSSFWorkbook();
        }
        /*Excel文件创建完毕*/
        CreationHelper createHelper = wb.getCreationHelper();  //创建帮助工具

        /*创建表单*/
        Sheet sheet = wb.createSheet(sheetName!=null?sheetName:"new sheet");

        //设置字体
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short)14);
        headFont.setFontName("Courier New");
        headFont.setItalic(false);
        headFont.setStrikeout(false);
        //设置头部单元格样式
        CellStyle headStyle = wb.createCellStyle();
        /*设置数据单元格格式*/
        CellStyle dataStyle = wb.createCellStyle();
        DataFormat dataFormat = wb.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));
        //dataStyle.setShrinkToFit(true);  //自动伸缩
        /*创建行Rows及单元格Cells*/
        Row headRow = sheet.createRow(0); //第一行为头
        for (int i=0;i<headList.size();i++){  //遍历表头数据
            Cell cell = headRow.createCell(i);  //创建单元格
            cell.setCellValue(createHelper.createRichTextString(headList.get(i)));  //设置值
            cell.setCellStyle(headStyle);  //设置样式
        }

        //设置超链接格式
        CellStyle hlinkStyle = wb.createCellStyle();
        Font hlinkFont = wb.createFont();
        hlinkFont.setUnderline(Font.U_SINGLE);
        hlinkFont.setFontHeightInPoints((short)12);
        hlinkFont.setColor(IndexedColors.BLUE.getIndex());
        hlinkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        hlinkStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        hlinkStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        hlinkStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        hlinkStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        hlinkStyle.setFont(hlinkFont);
        hlinkStyle.setWrapText(true);


        Boolean linkEmptyFlag = false;

        if(!CollectionUtils.isEmpty(linkCellList)){
            linkEmptyFlag =true;
        }

        int rowIndex = 1;  //当前行索引
        //创建Rows
        for (List<String> rowdata : dataList){ //遍历所有数据
            Row row = sheet.createRow(rowIndex++); //第一行为头
            for (int j = 0;j< rowdata.size();j++){  //编译每一行
                Cell cell = row.createCell(j);
                //设置文本格式
                cell.setCellType(Cell.CELL_TYPE_STRING);

                if(linkEmptyFlag && linkCellList.contains(j)){
                    String s = rowdata.get(j);
                    String[] linkArr = s.split(",");
                    String url = linkArr[0];
                    Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
                    link.setAddress(url);
                    cell.setHyperlink(link);
                    cell.setCellStyle(hlinkStyle);

                    String ns = s.replaceAll(",", "\n");
                    cell.setCellStyle(hlinkStyle);
                    cell.setCellValue(createHelper.createRichTextString(ns));
                }else {
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue(createHelper.createRichTextString(rowdata.get(j)));
                }
            }
        }
        /*创建rows和cells完毕*/
        CellStyle textStyle =  wb.createCellStyle();
        DataFormat textFormat = wb.createDataFormat();
        textStyle.setDataFormat(textFormat.getFormat("@"));
        /*设置列自动对齐*/
        for (int i =0;i<headList.size();i++){
            sheet.autoSizeColumn(i);
            // 解决自动设置列宽中文失效的问题
//            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            //设置猎德单元格式文本
            sheet.setDefaultColumnStyle(i,textStyle);
        }

        try {
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            outputStream.close();
        }

        return true;
    }


}
