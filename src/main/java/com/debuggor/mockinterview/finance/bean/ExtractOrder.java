package com.debuggor.mockinterview.finance.bean;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import lombok.Data;

import java.util.Date;

/**
 * 提现订单表（面试官）
 */
@Data
public class ExtractOrder {
    /**
     * ID
     */
    private Integer eoid;
    /**
     * 提现金额
     */
    private Float amount;
    /**
     * 支付宝账户
     */
    private String aliAccount;
    /**
     * 面试官ID
     */
    private Integer interviewerId;
    /**
     * 面试官姓名
     */
    private String username;
    /**
     * 订单状态（1、待审核，2、审核通过，3、不通过）
     */
    private String orderStatus;
    /**
     * 审核信息（管理员）
     */
    private String reviewInfo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审核时间
     */
    private Date reviewTime;
}
