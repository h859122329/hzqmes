package com.gdglc.hzqmes.common.form;

import com.gdglc.hzqmes.po.Users;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Leyenda on 2019/10/15.
 */
@Data
public class CustomerSearchForm {

    private Users currentUser;

    private String customerName;

    private Integer settleType;

    private String customerLevel;

    private Integer isCooperating;

    private BigDecimal taxRateBottom;

    private BigDecimal taxRateTop;

    private Integer isTaxFree;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginRegDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endRegDate;

    private Integer beginSettleDay;

    private Integer endSettleDay;

    private String contactName;

    private String contactType;

    private String contactNoType;

    private String contactNo;

}
