package com.debuggor.mockinterview.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 把图片保存在服务器
 */
public class FileUploadUtils {
    /**
     * 默认大小 2M
     */
    public static final long DEFAULT_MAX_SIZE = 2 * 1024 * 1024;

    /***
     * 图片存放地址
     */
    private static String DEFAULT_DIR = "D:/Pictures/web/mockinterview/";

    /**
     * 默认文件类型jpg
     */
    public static final String IMAGE_JPG_EXTENSION = ".jpg";

    public static String upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        long fileSize = file.getSize();
        if (fileSize > DEFAULT_MAX_SIZE) {
            return "文件大小大于2M";
        }
        String path = DEFAULT_DIR + System.currentTimeMillis() + FileUploadUtils.IMAGE_JPG_EXTENSION;
        File dest = new File(path);
        file.transferTo(dest);
        return path;
    }
}
