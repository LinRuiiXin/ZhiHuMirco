package com.example.upload.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.util.FileUtils;
import com.example.upload.service.rpc.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/11 3:01 下午
 */
@RestController
@RequestMapping("/Upload")
public class UploadController {

    private final UserService userService;

    @Autowired
    public UploadController(UserService userService) {
        this.userService = userService;
    }

    /*
     * 上传问题，回答，文章的图片文件，并以当前时间戳+用户id作为文件名存储在 /upload/ZhiHu/Image/文件名+后缀
     * */
    @PostMapping("/Image/{id}")
    public SimpleDto uploadImage(@PathVariable("id")Long id,MultipartHttpServletRequest request){
        try {
            MultipartFile image = request.getFile("image");
            if(image != null){
                FileUtils.doFile("/Image");
                String fileName = FileUtils.convertFileName(image,id);
                image.transferTo(new File("/Users/linruixin/Desktop/upload/ZhiHu/Image/"+fileName));
                return new SimpleDto(true,fileName,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new SimpleDto(false,"上传失败",null);
        }
    }

    /*
     * 上传问题，回答，文章的视频文件，并以当前时间戳+用户id作为文件名存储在 /upload/ZhiHu/Video/文件名+后缀
     * */
    @PostMapping("/Video/{id}")
    public SimpleDto uploadVideo(@PathVariable("id")Long id,MultipartHttpServletRequest request){
        try {
            MultipartFile video = request.getFile("video");
            if(video != null){
                FileUtils.doFile("/Video");
                String fileName = FileUtils.convertFileName(video,id);
                video.transferTo(new File("/Users/linruixin/Desktop/upload/ZhiHu/Video/"+fileName));
                return new SimpleDto(true,fileName,null);
            }else{
                return new SimpleDto(false,"文件为空",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new SimpleDto(false,"上传失败",null);
        }
    }
}
