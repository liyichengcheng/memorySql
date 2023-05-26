package com.blue.sqldb.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    static SimpleDateFormat df_ymdhmsS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static void parse() throws ParseException {
        String dStr = "2021-05-14 00:00:00";
        Date date = df_ymdhmsS.parse(dStr);
        System.out.println(date.getTime());// 1620921600000
    }

    public static void main(String[] args) {
        Date date = new Date(1684566086056L);
        System.out.println(df_ymdhmsS.format(date));
    }
}
