package com.recMall.common.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    // 生成数字验证码
    public static String randomNumbers(int length) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(0, 10));
        }
        return sb.toString();
    }
    
    // 生成混合验证码（字母+数字）
    public static String randomString(int length) {
        String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}