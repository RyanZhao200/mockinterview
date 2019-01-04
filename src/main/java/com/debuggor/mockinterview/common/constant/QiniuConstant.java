package com.debuggor.mockinterview.common.constant;

/**
 * 七牛云相关的配置
 */
public class QiniuConstant {

    /**
     * 七牛云的配置
     */
    public static final String QINIU_IMAGE_URL = "http://qiniu.three-day.cn/";
    public static final String QINIU_ACCESS_KEY = "FcfL25k2zgEJJGBRMVjb3_8d2SQaQgJHvLXiJmnD";
    public static final String QINIU_SECRET_KEY = "pCG3wRkgrPDHb2C-xl3dxYCTJLZ_-5XeQPL-MveX";
    public static final String QINIU_BUCKET_NAME = "mockinterview";

    /**
     * 房间所属帐号的 app
     */
    public static final String QINIU_APPID = "dvknxbjx9";
    /**
     * 该用户的房间管理权限，"admin" 或 "user"，默认为 "user" 。
     * 当权限角色为 "admin" 时，拥有将其他用户移除出房间等特权.
     */
    public static final String PERMISSION_ADMIN = "admin";
    public static final String PERMISSION_USER = "user";
    /**
     * 2 小时
     * int64 类型，鉴权的有效时间，传入以秒为单位的64位Unix绝对时间，
     * token 将在该时间后失效
     */
    public static final long EXPIREAT =System.currentTimeMillis()+ (1000 * 60 * 60 * 2L);

}
