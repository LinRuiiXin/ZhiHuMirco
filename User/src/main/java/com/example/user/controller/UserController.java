package com.example.user.controller;

import com.example.user.dto.SimpleDto;
import com.example.user.po.User;
import com.example.user.service.AsyncService;
import com.example.user.service.interfaces.UserService;
import com.example.user.util.FileUtil;
import com.example.user.util.SecurityCodeUtil;
import com.example.user.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * 用户登录与注册
 * @author LinRuiXin
 * @date 2020/10/25 11:34 上午
 */

@RestController
@RequestMapping("/User")
public class UserController {

    private final UserService userService;
    private final StringRedisTemplate redisTemplate;
    private final AsyncService asyncService;

    @Autowired
    public UserController(UserService userService, StringRedisTemplate redisTemplate, AsyncService asyncService){
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.asyncService = asyncService;
    }

    //发送邮件任务
    @GetMapping("/Login/SecurityCode/{mail}")
    public SimpleDto sendLoginMailSecurityCode(@PathVariable("mail")String mail){
        //先判断是否存在用户
        if(userService.mailHasRegistered(mail)){
            //生成6位随机验证码
            String securityCode = SecurityCodeUtil.getSecurityCode(6);
            //执行异步发送邮件任务
            asyncService.sendMail("LoginCode:",mail,securityCode,"欢迎通过验证码登录ZhiHu,您的验证码是");
            return new SimpleDto(true,null,null);
        }else{
            return new SimpleDto(false,"该账号还未被注册",null);
        }
    }

    //根据邮箱地址判断当前用户是否已被注册，如果还未被注册，发送验证码，否则返回错误信息
    @GetMapping("/Register/SecurityCode/{mail}")
    public SimpleDto sendRegisterMailSecurityCode(@PathVariable("mail") String mail){
        if(userService.mailHasRegistered(mail)){
            return new SimpleDto(false,"该账号已被注册",null);
        }else{
            asyncService.sendMail("RegisterCode:",mail, SecurityCodeUtil.getSecurityCode(6),"感谢注册ZhiHu，您的验证码是");
            return new SimpleDto(true,null,null);
        }
    }

    //检查验证码
    @GetMapping("/Register/SecurityCode/{mail}/{code}")
    public SimpleDto validateCode(@PathVariable("mail")String mail,@PathVariable("code") String code){
        Object o = redisTemplate.opsForValue().get("RegisterCode:"+mail);
        if(o == null){
            return new SimpleDto(false,"验证码过期或已失效",null);
        }else{
            String redisCode = (String) o;
            if(redisCode.equals(code.trim().toUpperCase())){
                return new SimpleDto(true,null,null);
            }else{
                return new SimpleDto(false,"验证码错误",null);
            }
        }
    }



    //免密码登录
    @GetMapping("/NoPassword/{mail}/{code}")
    public SimpleDto loginNoPassword(@PathVariable("mail") String mail,@PathVariable("code")String code){
        Object o = redisTemplate.opsForValue().get("LoginCode:" + mail);
        if(o != null){
            String codeInRedis = (String) o;
            if(codeInRedis.equals(code.trim().toUpperCase())){
                User user = userService.queryUserByMail(mail);
                if(user == null){
                    return new SimpleDto(false,"该账号不存在",null);
                }else{
                    return new SimpleDto(true,null,user);
                }
            }else{
                return new SimpleDto(false,"验证码错误",null);
            }
        }else{
            return new SimpleDto(false,"验证码过期或已失效",null);
        }
    }

    @GetMapping("/{mail}/{password}")
    public SimpleDto loginWithPassword(@PathVariable String mail,@PathVariable String password){
        User user = userService.queryUserByMailPassword(mail, password);
        if(user == null){
            return new SimpleDto(false,"账号或密码错误",null);
        }else{
            return new SimpleDto(true,null,user);
        }
    }

    //接收一个User，校验密码、邮箱地址、电话号码，插入数据
    @PostMapping
    public SimpleDto register(@Valid @RequestBody User user, Errors errors){
        List<FieldError> fieldErrors = errors.getFieldErrors();
        Map<String,String> errorsMap = null;
        if(fieldErrors!=null && fieldErrors.size()!=0){
            errorsMap = new HashMap<>();
            for(FieldError error:fieldErrors){
                errorsMap.put(error.getField(),error.getDefaultMessage());
            }
            return new SimpleDto(false,"数据格式错误",errorsMap);
        }else{
            synchronized (this){
                if(!userService.nameHasRegistered(user.getUserName())){
                    if(!userService.phoneHasRegistered(user.getPhone())){
                        if(!userService.mailHasRegistered(user.getMail())) {
                            User newUser = userService.insertUser(user);
                            return new SimpleDto(true, null, newUser);
                        }else{
                            return new SimpleDto(false,"该邮箱已被注册",null);
                        }
                    }else{
                        return new SimpleDto(false,"该手机号已被注册",null);
                    }
                }else{
                    return new SimpleDto(false,"该用户名已被注册",null);
                }
            }
        }
    }

    //上传头像，存储在 /upload/ZhiHu/用户id/portrait.文件后缀
    @PostMapping("/Portrait/{id}")
    public SimpleDto uploadPortrait(@PathVariable("id")Long id, MultipartHttpServletRequest request){
        try {
            MultipartFile portrait = request.getFile("portrait");
            if(portrait != null){
                FileUtil.doFile("/User/"+id);
                String fileName = FileUtil.convertFileName(portrait,id);
                File file = new File("User/"+id+"/"+fileName);
                portrait.transferTo(file);
                userService.setPortraitFileNameById(id,fileName);
                return new SimpleDto(true,fileName,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            return new SimpleDto(false,"上传失败",null);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }


}
