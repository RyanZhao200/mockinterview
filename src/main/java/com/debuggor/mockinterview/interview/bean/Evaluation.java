package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Evaluation {
    /**
     * 评价ID
     */
    private Integer eid;
    /**
     * 内容
     */
    private String comment;
    /**
     * 评星
     */
    private Float grade;
    /**
     * 面试官ID
     */
    private Integer iid;
    /**
     * 面试官 interviewer
     */
    private Interviewer interviewer;
    /**
     * 评价者ID
     */
    private Integer fid;
    /**
     * 评价者Finder
     */
    private Finder finder;
    /**
     * 订单ID
     */
    private Integer oid;
    /**
     * 创建时间
     */
    private Date createTime;


}
