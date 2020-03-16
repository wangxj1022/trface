package com.trendcote.web.utils;

import com.trendcote.web.entity.business.StaffInfo;
import com.trendcote.web.entity.business.VisitorInfo;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 数据工具类
 *
 * @author guangyan.li
 * @date 2018/4/16 9:22
 */
public class DataUtil {
    /**
     * 检查字符串是否为空(为null)
     *
     * @return str == null？
     */
    public static boolean isStringNull(String str) {
        return str == null;
    }

    /**
     * 检查字符串是否为空(null和空串都表示为空)
     *
     * @param str
     * @return
     */
    public static boolean isStirngBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 为null或者长度为0
     *
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 不为null 并且长度不为0
     *
     * @param str
     * @return
     */
    public static boolean isStringNotEmpty(String str) {
        return !isStringEmpty(str);
    }

    /**
     * 是否是 空串的md5
     *
     * @param str
     * @return
     */
    public static boolean isStringBlankMd5(String str) {
        String blankMd5 = "d41d8cd98f00b204e9800998ecf8427e";
        return blankMd5.equalsIgnoreCase(str);
    }

    /**
     * 检查俩字符串是否相等(任意一个为null表示不相等)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isNotNullStrEquals(String str1, String str2) {
        if (isStringNull(str1) || isStringNull(str2)) {
            return false;
        }
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 检查俩字符串是否相等(忽略大小写，任意一个为null表示不相等)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isNotNullStrEqualsIgnoreCase(String str1, String str2) {
        if (isStringNull(str1) || isStringNull(str2)) {
            return false;
        }
        if (str1.equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 检查俩字符串是否相等(任意一个为blank表示不相等)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isNotBalnkStrEquals(String str1, String str2) {
        if (isStirngBlank(str1) || isStirngBlank(str2)) {
            return false;
        }
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 检查俩字符串是否相等(忽略大小写，任意一个为blank表示不相等)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isNoBalnkStrEqualsIgnoreCase(String str1, String str2) {
        if (isStirngBlank(str1) || isStirngBlank(str2)) {
            return false;
        }
        if (str1.equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 检查List是否为空(list是否为null)
     *
     * @param list
     * @return
     */
    public static boolean isListNull(List list) {
        return list == null;
    }

    /**
     * 检查List是否为空(list为null或者size=0表示为空)
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * map是否为null
     *
     * @param map
     * @return
     */
    public static boolean isMapNull(Map map) {
        return map == null;
    }

    /**
     * map是否为空(map为null或者map的size为0)
     *
     * @param map
     * @return
     */
    public static boolean isMapEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * boolean转换成 int值，遵循c的非0即真的原则
     *
     * @param b
     * @return
     */
    public static int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    /**
     * boolean转换成 int值，遵循c的非0即真的原则
     *
     * @param b
     * @return
     */
    public static String boolToString(boolean b) {
        return String.valueOf(b ? 1 : 0);
    }

    /**
     * int 转换成 bool 值，遵循c的非0即真的原则
     *
     * @param num
     * @return
     */
    public static boolean intToBool(int num) {
        return num != 0;
    }

    /**
     * string转boolean，字符串为空或者为‘0’表示false
     *
     * @param str
     * @return
     */
    public static boolean stringToBool(String str) {
        if (isStirngBlank(str)) {
            return false;
        }
        return !"0".equals(str.replaceAll(" ", ""));
    }

    /**
     * 是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isPureDigital(String str) {
        if (isStirngBlank(str)) {
            return false;
        }
        Pattern pattern = compile("^[0-9]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否是指定长度的纯数字
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isPureDigital(String str, int length) {
        if (isStringNull(str) || str.length() != length) {
            return false;
        }
        Pattern pattern = compile("^[0-9]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断对像是否为空
     *
     * @param obj
     * @return
     */
    public static boolean objectIsNull(Object obj) {
        return obj == null;
    }


    public static String generateRandom(int length ){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }


    /**
     * 占比计算保留小数的位数方法
     * 转成百分数
     * 当前数除以总数
     * @param  num1 ,num2  num1/num2
     * @return  rate  保留2位小数的
     */
    public static String  division(int num1,int num2){
        String rate="0.00%";
        //定义格式化起始位数
        String format="0.00";
        if(num2 != 0 && num1 != 0){
            DecimalFormat dec = new DecimalFormat(format);
            rate =  dec.format((double) num1 / num2*100)+"%";
            while(true){
                if(rate.equals(format+"%")){
                    format=format+"0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate =  dec1.format((double) num1 / num2*100)+"%";
                }else {
                    break;
                }
            }
        }else if(num1 != 0 && num2 == 0){
            rate = "100%";
        }
        return rate;
    }

}
