package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.utils.PythonRunner;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/arithmetic")
@Slf4j
public class ArithmeticController {

    private String excelBasePath = "/data/application/excel/";
    private String pythonBasePath = "/data/application/python/";

    @Autowired
    private IArithmeticService arithmeticService;

    @RequestMapping("/generate")
    public void generate(HttpServletResponse response,
                         @RequestParam(name = "opCount") int opCount,
                         @RequestParam(name = "itemCount") int itemCount) throws Exception {
        ArithmeticPaper arithmeticPaper = arithmeticService.generate(opCount, itemCount);
        render2Excel(response, arithmeticPaper);
    }

    private void render2Excel(HttpServletResponse response, ArithmeticPaper arithmeticPaper) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("四则运算");
        sheet.setColumnWidth(0, 50*256);
        sheet.setColumnWidth(1, 50*256);
        // 设置要导出的文件的名字
        String fileName = arithmeticPaper.getUuid() + ".xls";

        HSSFRow row = null;
        for (int i = 0; i < arithmeticPaper.getItemCount(); i ++) {
            if (i % 2 == 0) {
                row = sheet.createRow(i / 2);
                row.setHeightInPoints((short) 240);
            }

            HSSFCell cell = row.createCell(i % 2);

            //生成单元格样式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            //新建font实体
            HSSFFont hssfFont = workbook.createFont();
            //字体大小
            hssfFont.setFontHeightInPoints((short)16);
            hssfFont.setFontName("楷体");
            cellStyle.setFont(hssfFont);
            cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            cell.setCellStyle(cellStyle);

            cell.setCellValue(arithmeticPaper.getItems().get(i));
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @RequestMapping("/generate2")
    public void generate2(HttpServletResponse response,
                            @RequestParam(name = "pyScript", required = false) String pyScript,
                            @RequestParam(name = "pyArgs", required = false) String pyArgs,
                            @RequestParam(name = "paperType", required = false) String paperType) throws Exception {
        if (StringUtils.isBlank(pyScript)) {
            pyScript = "gen_paper.py";
        }

        if (StringUtils.isBlank(pyArgs)) {
            pyArgs = "1,20,5";
        }

        log.info("run python: {}, args: {}, paperType: {}", pyScript, pyArgs, paperType);

        if (null == paperType) {
            paperType = "1";
        }

        String[] args = {excelBasePath, paperType};

        args = ArrayUtils.addAll(args, StringUtils.split(pyArgs, ","));

        String excelFilePath = new PythonRunner().exec(pythonBasePath + pyScript, args);

        log.info("excel path: {}", excelFilePath);

        if (StringUtils.endsWith(excelFilePath, ".xlsx")) {
            String fileName = StringUtils.substring(excelFilePath,
                    StringUtils.lastIndexOf(excelFilePath, "/") + 1);
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(excelFilePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" +  new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } else {
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.setCharacterEncoding("utf-8");
            PrintWriter pw = response.getWriter();
            pw.print("生成出错了~");
        }
    }

    @RequestMapping("/generate3")
    public void generate3(HttpServletResponse response,
                          @RequestParam(name = "opCount") int opCount,
                          @RequestParam(name = "pageCount") int pageCount,
                          @RequestParam(name = "type", required = false, defaultValue = "1") int type) throws Exception {
        if (opCount < 1) opCount = 1;
        if (opCount > 5) opCount = 2;
        if (type < 1 || type > 2) type = 1;
        if (pageCount < 2 || pageCount > 10) pageCount = 2;

        List<Arithmetic> arithmetics = null;
        if (type == 1) {
            arithmetics = ArithmeticBuilder.newGradeOneBuilder(opCount).build(30 * pageCount);
        } else {
            arithmetics = ArithmeticBuilder.newGradeFourBuilder(opCount, 2).build(30 * pageCount);
        }

        render2Excel(response, arithmetics, pageCount);
    }

    private void render2Excel(HttpServletResponse response, List<Arithmetic> arithmetics, int pageCount) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("四则运算");
        sheet.setColumnWidth(0, 35*256);
        sheet.setColumnWidth(1, 35*256);
        sheet.setColumnWidth(2, 35*256);
        // 单位为英寸 1英寸=2.54厘米
        sheet.setMargin(HSSFSheet.BottomMargin, 0.8);
        sheet.setMargin(HSSFSheet.TopMargin, 0.8);
        sheet.setMargin(HSSFSheet.LeftMargin, 0.4);
        sheet.setMargin(HSSFSheet.RightMargin, 0.4);

        // 设置要导出的文件的名字
        String fileName = "四则运算.xls";

        int rowNum = 0;
        for (int page = 0; page < pageCount; page++) {
            //生成单元格样式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            //新建font实体
            HSSFFont hssfFont = workbook.createFont();
            //字体大小
            hssfFont.setFontHeightInPoints((short)12);
            hssfFont.setFontName("微软雅黑");
            cellStyle.setFont(hssfFont);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            HSSFRow row = null;
            for (int i = 0; i < 30; i ++) {
                if (i % 3 == 0) {
                    row = sheet.createRow(rowNum ++);
                    row.setHeightInPoints((short) 30);
                }

                HSSFCell cell = row.createCell(i % 3);

                cell.setCellStyle(cellStyle);

                cell.setCellValue(arithmetics.get(i + 30 * page).toString());
            }

            row = sheet.createRow(rowNum ++);
            row.setHeightInPoints((short) 40);

            HSSFCell cell = row.createCell(0);
            cellStyle = workbook.createCellStyle();
            cellStyle.setFont(hssfFont);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            cell.setCellStyle(cellStyle);

            row.createCell(1);
            row.createCell(2);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 2));
            cell.setCellValue("日期：_________，用时：________，得分：________");
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}