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
     * 面试官信息
     */
    private Interviewer interviewer;
    /**
     * 状态（1、认证成功，2、认证失败，3、待审核）
     */
    private String certificationStatus;
    /**
     * 反馈，管理员对认证的信息进行评价。不通过的原因等
     */
    private String feedback;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审核时间
     */
    private Date reviewTime;
}
