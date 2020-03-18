package com.gdglc.hzqmes.common.vo;

import com.gdglc.hzqmes.po.PicData;
import com.gdglc.hzqmes.po.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductVo extends Product{
    List<PicData> picDataPrintContent;
    List<PicData> picDataPunchInfo;
}
