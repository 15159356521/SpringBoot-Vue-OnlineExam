package com.exam.controller;

import com.exam.serviceimpl.TableExcelServiceImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // 使用SpringRunner进行测试
public class TableExcelControllerTest {

     @InjectMocks
     TableExcelController tableExcelController;

     @Mock
     TableExcelServiceImpl tableExcelService;

     @Test
     public void testGetTableExcel() {
//          tableExcelController.getTableExcel(20190001);
     }

}