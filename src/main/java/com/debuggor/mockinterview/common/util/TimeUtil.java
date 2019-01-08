package com.debuggor.mockinterview.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 */
public class TimeUtil {
    /**
     * 把时间格式化
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = format.format(date);
        return dataString;
    }
}
