package com.example.user.validator;

import com.example.basic.po.User;
import com.example.user.util.RegexUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o == null){
            errors.rejectValue("",null,"用户不能为空");
        }
        User user = (User) o;
        if(!RegexUtil.validatePassword(user.getPassword())){
            errors.rejectValue("password",null,"密码格式不正确");
        }
        if(!RegexUtil.validateMail(user.getMail())){
            errors.rejectValue("mail",null,"邮箱格式不正确");
        }
        if(!RegexUtil.validatePhone(user.getPhone())){
            errors.rejectValue("phone",null,"手机号格式不正确");
        }
    }
}
