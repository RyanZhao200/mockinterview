package com.debuggor.mockinterview.common.service;

import com.debuggor.mockinterview.common.constant.QiniuConstant;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QiniuService {
    /**
     * 设置好账号的ACCESS_KEY和SECRET_KEY
     */
    private String ACCESS_KEY = QiniuConstant.QINIU_ACCESS_KEY;
    private String SECRET_KEY = QiniuConstant.QINIU_SECRET_KEY;
    /**
     * 要上传的空间
     */
    private String BUCKET_NAME = QiniuConstant.QINIU_BUCKET_NAME;
    /**
     * 密钥配置
     */
    private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    /**
     * 创建上传对象
     */
    private UploadManager uploadManager = new UploadManager();

    /**
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     */
    public String getUpToken() {
        return auth.uploadToken(BUCKET_NAME);
    }

    public void upload(byte[] localData, String remoteFileName) throws IOException {
        Response res = uploadManager.put(localData, remoteFileName, getUpToken());
        // 打印返回的信息
        System.out.println(res.bodyString());
    }

}
