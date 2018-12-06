package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.util.FileUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 工具类controller
 */
@Controller
@RequestMapping("/util")
public class UtilController {

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
        String filename = null;
        if (myFileName != null) {
            filename = FileUploadUtils.upload(myFileName);
        }
        // 七牛云认证过后，把图片上传到七牛云保存，再修改这里
//        return filename;
        return "http://edu-image.nosdn.127.net/8EA2B6D4CF0CCE3155519C2BFDD46AE7.jpg?imageView&thumbnail=510y288&quality=100";
    }
}
