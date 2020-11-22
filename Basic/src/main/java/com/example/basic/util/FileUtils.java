package com.example.basic.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/11 3:09 下午
 */
public class FileUtils {

    public static final String RES_QUESTION = "Question";
    public static final String RES_Answer = "Answer";
    public static final String RES_ARTICLE = "Article";

    //根据当前系统时间戳+用户id生成唯一文件名
    public static String convertFileName(MultipartFile multipartFile, Long id){
        String [] fileNameArr = multipartFile.getOriginalFilename().split("\\.");
        String fileName = System.currentTimeMillis()+String.valueOf(id)+"."+fileNameArr[(fileNameArr.length)-1];
        return fileName;
    }
    //判断 D:/upload/ZhiHu 下的某个文件夹是否存在
    public static void doFile(String path){
        File file = new File("/Users/linruixin/Desktop/upload/ZhiHu/"+path);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public static String getTextFile(String directory,String fileName){
        StringBuilder stringBuilder = null;
        FileReader fileReader = null;
        try {
            String url = "/Users/linruixin/Desktop/upload/ZhiHu/" + directory + "/" + fileName + ".txt";
            File file = new File(url);
            if(file.exists()){
                stringBuilder = new StringBuilder();
                fileReader = new FileReader(file);
                char[] buffer = new char[1024];
                while (fileReader.read(buffer) != -1){
                    stringBuilder.append(buffer);
                }
            }
        }catch (Exception e){
            return "";
        }finally {
            if(fileReader != null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
