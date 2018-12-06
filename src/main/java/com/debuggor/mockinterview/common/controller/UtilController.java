package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.constant.QiniuConstant;
import com.debuggor.mockinterview.common.service.QiniuService;
import com.debuggor.mockinterview.common.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * 工具类controller
 */
@Controller
@RequestMapping("/util")
public class UtilController {

    @Autowired
    private QiniuService qiniuService;

    /**
     * 上传文件
     *
     * @param myFileName
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String uploadImage(MultipartFile myFileName) throws IOException {
        // 文件类型限制
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png","image/jpg"};
        boolean allowed = Arrays.asList(allowedType).contains(myFileName.getContentType());
        if (!allowed) {
            return "error|不支持的类型";
        }
        // 图片大小限制
        if (myFileName.getSize() > 3 * 1024 * 1024) {
            return "error|图片大小不能超过3M";
        }
        // 包含原始文件名的字符串
        String fi = myFileName.getOriginalFilename();
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
        // 生成云端的真实文件名
        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;
        qiniuService.upload(myFileName.getBytes(), remoteFileName);
        // 返回图片的URL地址
        System.out.println(QiniuConstant.QINIU_IMAGE_URL + remoteFileName);
        return QiniuConstant.QINIU_IMAGE_URL + remoteFileName;
    }
}
