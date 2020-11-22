package com.example.basic.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/21 7:34 下午
 */
public class StringUtils {

    public static String jointString(char sign, List<?> list,String field) throws NoSuchFieldException, IllegalAccessException {
        if(list.size() > 0){
            StringBuilder stringBuilder = new StringBuilder();
            Class<?> aClass = list.get(0).getClass();
            Field f = aClass.getDeclaredField(field);
            f.setAccessible(true);
            int len = list.size();
            for (int i = 0; i < len; i++) {
                stringBuilder.append(f.get(list.get(i)));
                if(i != len-1){
                    stringBuilder.append(sign);
                }
            }
            return stringBuilder.toString();
        }else{
            return "";
        }
    }
}
