package com.gdglc.hzqmes.service.codestatic;

import com.gdglc.hzqmes.po.CodeStaticTable;
import com.gdglc.hzqmes.service.CodeStaticTableService;
import org.aspectj.apache.bcel.classfile.Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Leyenda on 2019/11/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeStaticTest {

    @Autowired
    private CodeStaticTableService codeStaticTableService;

    @Test
    public void testGetByCodeType(){

        List<CodeStaticTable> codeList = codeStaticTableService.getCodeListByCodeType("CONTNOTP");

        for (CodeStaticTable code :
                codeList) {
            System.out.println("code:"+ code);
        }
    }


}
