package com.debuggor.mockinterview.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成激活码
 */
public class ActivateCodeUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        return sdf.format(date);
    }

    public static String createActivateCode() {
        return System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(createActivateCode().length());
    }
}
