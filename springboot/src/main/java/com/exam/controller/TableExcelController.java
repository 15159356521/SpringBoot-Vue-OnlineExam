package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.exam.entity.ApiResult;
import com.exam.entity.TableExcel;
import com.exam.serviceimpl.TableExcelServiceImpl;
import com.exam.util.ApiResultHandler;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class TableExcelController {

    // 通过注解注入TableExcelServiceImpl
    @Autowired
    private TableExcelServiceImpl tableExcelService;

    @GetMapping(value = "/tableExcel/{examCode}")
    public ApiResult getTableExcel(HttpServletResponse response, @PathVariable("examCode") Integer examCode) {
        System.out.println(response);
        List<TableExcel> tableExcel = tableExcelService.getTableExcel(examCode);
        String fileName = "学生成绩表";
        try {
            this.setExcelResponseProp(response, fileName);
            EasyExcel.write(response.getOutputStream()
            ).head(TableExcel.class).excelType(ExcelTypeEnum.XLSX).sheet("sheet1"
            ).doWrite(tableExcel);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return ApiResultHandler.buildApiResult(200, "查询成功", tableExcel);
    }

    private void setExcelResponseProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }


}
