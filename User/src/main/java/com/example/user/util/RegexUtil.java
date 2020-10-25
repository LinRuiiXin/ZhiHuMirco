package com.example.user.util;

import org.springframework.util.StringUtils;

public class RegexUtil {
    //密码至少包含 数字和英文，长度6-20
    private static final String passwordRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    //邮箱
    private static final String mailRegex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //手机号码
    private static final String phoneRegex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    public static boolean validatePassword(String password){
        return (!StringUtils.isEmpty(password)&&password.matches(passwordRegex));
    }
    public static boolean validateMail(String mail){
        return (!StringUtils.isEmpty(mail)&&mail.matches(mailRegex));
    }
    public static boolean validatePhone(String phone){
        return (!StringUtils.isEmpty(phone)&&phone.matches(phoneRegex));
    }
}
