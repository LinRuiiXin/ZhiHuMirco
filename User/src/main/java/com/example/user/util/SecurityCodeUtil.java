package com.example.user.util;

import java.util.Random;

public class SecurityCodeUtil {
    private static final char[] supportChar = {'1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final Random random = new Random();
    // 传入需要生成的随机数长度,生成[0-9],[A-Z]的随机数
    public static String getSecurityCode(int len){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ; i < len ; i++){
            stringBuilder.append(supportChar[random.nextInt(supportChar.length)]);
        }
        return stringBuilder.toString();
    }
}
