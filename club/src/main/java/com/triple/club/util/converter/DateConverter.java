package com.triple.club.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static String convert(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
