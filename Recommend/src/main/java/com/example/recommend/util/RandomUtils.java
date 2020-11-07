package com.example.recommend.util;

import java.util.Random;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/6 9:22 下午
 */
public class RandomUtils {
    private static Random random = new Random();
    public static Integer getRangeNumber(Integer range){
        return (random.nextInt(range))+1;
    }
}
