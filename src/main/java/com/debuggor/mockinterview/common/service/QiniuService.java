package com.debuggor.mockinterview.common.service;

import com.debuggor.mockinterview.common.constant.QiniuConstant;
import com.debuggor.mockinterview.common.constant.UserConstant;
import com.debuggor.mockinterview.common.util.RtcRoomManager;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QiniuService {
    @Autowired
    private FinderDao finderDao;
    @Autowired
    private InterviewerDao interviewerDao;
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
     * 房间所属帐号的 app
     */
    private String APPID = QiniuConstant.QINIU_APPID;
    /**
     * token 时长
     */
    private Long EXPIREAT = QiniuConstant.EXPIREAT;
    /**
     * 该用户的房间管理权限，"admin" 或 "user"，默认为 "user" 。
     * 当权限角色为 "admin" 时，拥有将其他用户移除出房间等特权.
     */
    private String USER = QiniuConstant.PERMISSION_USER;

    /**
     * 第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
     */
    Zone z = Zone.autoZone();
    Configuration c = new Configuration(z);
    /**
     * 创建上传对象 这里需要改动 20181216
     */
    private UploadManager uploadManager = new UploadManager(c);

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

    /**
     * 获取 roomToken
     */
    public String getRoomToken(Integer iid, Integer fid, Integer type) throws Exception {
        Finder finder = finderDao.getFinderById(fid);
        Interviewer interviewer = interviewerDao.getInterviewerById(iid);
        String femail = finder.getEmail();
        String iemail = interviewer.getEmail();
        String roomName = femail.substring(0, femail.indexOf('@')) + iemail.substring(0, iemail.indexOf('@'));

        RtcRoomManager rtc = new RtcRoomManager(auth);
        String roomToken = null;
        if (type.equals(UserConstant.Interviewer_Type)) {
            roomToken = rtc.getRoomToken(APPID, roomName, interviewer.getUsername(), EXPIREAT, USER);
        } else if (type.equals(UserConstant.Finder_Type)) {
            roomToken = rtc.getRoomToken(APPID, roomName, finder.getUsername(), EXPIREAT, USER);
        }
        return roomToken;
    }

}
