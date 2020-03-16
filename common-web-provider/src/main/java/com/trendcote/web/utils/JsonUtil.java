package com.trendcote.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trendcote.web.entity.business.VisitorInfo;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Description 对调用接口返回的Json串进行解析
 * @Date 2019/7/29 15:32
 * @Created by xym
 */

public class JsonUtil {

    /**
     *@Description 解析jsonStrings为 visitors集合
     *@param 
     *@return 
     */
    public static List<VisitorInfo> parseJson(String jsonStrings,String eoaPublicKey){
        List<String> visitorJsonStrs = JSON.parseArray(jsonStrings, String.class);
        List<VisitorInfo> visitors = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for( String visitorJsonStr : visitorJsonStrs ){
            VisitorInfo visitor = new VisitorInfo();
            JSONObject jsonObject = JSONObject.parseObject(visitorJsonStr);
            if( StringUtils.isEmpty(jsonObject.getString("name")) ){
                continue;
            }
            // EOA数据解密
            String name = RSAUtil.decryptEOADataByPublicKey(jsonObject.getString("name"), eoaPublicKey);
            visitor.setVisitorName( name );

            if( StringUtils.isEmpty(jsonObject.getString("idCardNo")) ){
                continue;
            }
            // EOA数据解密
            String idCardNo = RSAUtil.decryptEOADataByPublicKey(jsonObject.getString("idCardNo"), eoaPublicKey);
            visitor.setVisitorCardId( idCardNo );

            if( StringUtils.isEmpty(jsonObject.getString("beginTime")) ){
                continue;
            }
            try {
                visitor.setInTime( sdf.parse(jsonObject.getString("beginTime")) );
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }

            if( StringUtils.isEmpty(jsonObject.getString("endTime")) ){
                continue;
            }
            try {
                visitor.setOutTime( sdf.parse(jsonObject.getString("endTime")) );
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }

            if( StringUtils.isNotEmpty(jsonObject.getString("visitUnit")) ){
                // EOA数据解密
                String visitUnit = RSAUtil.decryptEOADataByPublicKey(jsonObject.getString("visitUnit"), eoaPublicKey);
                visitor.setVisitorCompany(visitUnit);
            }
            visitors.add(visitor);
        }
        return visitors;
    }


}
