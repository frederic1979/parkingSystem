package com.parkit.parkingsystem.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {

    static public LocalDateTime convertDateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
    }

}
