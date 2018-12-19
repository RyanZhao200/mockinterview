package com.debuggor.mockinterview.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成订单号
 */
public class OrdersNumberUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhMMss");
        return sdf.format(date);
    }

    public static String createOrdersNumber() {
        Date date = new Date();
        String s = formatDate(date) + UUID.randomUUID().toString().replace("-", "");
        return s.substring(0, 20);
    }

    public static void main(String[] args) {
        System.out.println(createOrdersNumber());
        System.out.println(createOrdersNumber().length());
        System.out.println("中国");
    }
}
