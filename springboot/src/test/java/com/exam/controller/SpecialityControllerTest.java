package com.exam.controller;

import com.exam.serviceimpl.SpecialityServiceImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SpecialityControllerTest {

         @InjectMocks
         SpecialityController specialityController;

         @Mock
         SpecialityServiceImpl specialityService;

         @Test
         public void testFindAll() {
           System.out.println(
                   specialityController.findAll()
           );
         }


}