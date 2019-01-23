package com.debuggor.mockinterview.finance.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户金额
 */
@Data
public class Amount {
    /**
     * ID
     */
    private Integer aid;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户类别
     */
    private String userType;
}
