package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.utils.PythonRunner;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

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

        log.info("run python: {}, args: {}, paperType: {}", pyScript, pyArgs, paperType);

        if (null == paperType) {
            paperType = "3";
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
}