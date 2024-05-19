package com.exam.service;

import com.exam.entity.TableExcel;

import java.util.List;

public interface TableExcelService {
    List<TableExcel> getTableExcel(Integer examCode);
}
