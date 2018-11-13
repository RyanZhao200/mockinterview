package com.debuggor.mockinterview.common;

import com.debuggor.mockinterview.common.util.Md5Util;

public class LoginTests {
    public static void main(String[] args) {
        String hash = Md5Util.hash("123456");
        System.out.println(hash);
    }
}
