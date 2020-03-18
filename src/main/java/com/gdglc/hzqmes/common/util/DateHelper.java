package com.gdglc.hzqmes.common.util;

import java.time.*;
import java.util.Date;

/**
 * Created by Leyenda on 2019/3/13.
 */
public class DateHelper {

    /**
     *  Transform LocalDate to java.utility.Date
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);

        return  date;
    }

    public static LocalDate Date2LocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }
}
