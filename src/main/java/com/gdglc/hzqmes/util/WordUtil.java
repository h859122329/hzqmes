package com.gdglc.hzqmes.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class WordUtil {

    /**
     * 替换Word内容
     * @param path 文件路径
     * @param replacement 替换的内容
     * @return
     */
    public static byte[] replacementContent(String path, Map<String,Object> replacement)  {

        try {
//            InputStream inputStream = new FileInputStream(org.springframework.util.ResourceUtils.getFile("classpath:"+path));
//            InputStream inputStream = new FileInputStream(is);
//            HWPFDocument hwpfDocument = new HWPFDocument(inputStream);
            HWPFDocument hwpfDocument = new HWPFDocument(Objects.requireNonNull(WordUtil.class.getClassLoader().getResourceAsStream(path)));
            Fields fields = hwpfDocument.getFields();
            Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN)
                    .iterator();

            //替换word文档
            Range range = hwpfDocument.getRange();
            if(replacement!=null){
                for(String key : replacement.keySet()){
                    range.replaceText("${"+key+"}", replacement.get(key)==null?"":replacement.get(key).toString());
                }
            }

            //输出数据到流
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            hwpfDocument.write(ostream);
            //返回流字节
            return ostream.toByteArray();


        }catch (Exception e){
            log.error("WordUtil replacementContent方法出现异常",e);
            return null;
        }


    }
}