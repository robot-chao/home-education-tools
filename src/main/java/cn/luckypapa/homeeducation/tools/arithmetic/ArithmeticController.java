package cn.luckypapa.homeeducation.tools.arithmetic;

import cn.luckypapa.homeeducation.utils.PythonRunner;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/arithmetic")
@Slf4j
public class ArithmeticController {

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
    public Object generate2(HttpServletResponse response) throws Exception {
        return new PythonRunner().exec("/data/application/python/hello.py");
    }
}