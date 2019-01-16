package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

/**
 * 面试官认证信息实体
 */
@Data
public class Certification {
    /**
     * ID
     */
    private Integer cid;
    /**
     * 认证的信息
     */
    private String information;
    /**
     * 面试官ID
     */
    private Integer iid;
    /**
     * 状态（1、认证成功，2、认证失败，3、待审核）
     */
    private String certificationStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审核时间
     */
    private Date reviewTime;
}
