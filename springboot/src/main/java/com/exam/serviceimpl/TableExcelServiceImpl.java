package com.exam.serviceimpl;

import com.exam.entity.TableExcel;
import com.exam.mapper.TableExcelMappper;
import com.exam.service.TableExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableExcelServiceImpl implements TableExcelService {
    @Autowired
    private TableExcelMappper tableExcelMappper;

    @Override
    public List<TableExcel> getTableExcel(Integer examCode) {
        System.out.println("getTableExcel");
        return tableExcelMappper.getTableExcel(examCode);
    }
}
