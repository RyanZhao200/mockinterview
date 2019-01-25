package com.debuggor.mockinterview.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlipayUtil {
    /**
     * 获取支付宝付款成功后的参数
     * 获取支付宝GET过来反馈信息
     */
    public static Map<String, String> getAlipayParams(Map<String, String[]> requestParams) throws Exception {
        Map<String, String> params = new HashMap<>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
}
