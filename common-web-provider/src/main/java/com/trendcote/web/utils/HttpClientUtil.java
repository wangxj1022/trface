package com.trendcote.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 莹
 * @date 2018/6/4
 */
@Service
public class HttpClientUtil {
    @Autowired
    private  RestTemplate  restTemplate ;
    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param jsonString    发送的参数
     * @return  AdToutiaoJsonTokenData
     */
    public static String sendPostRequest(String url, String jsonString,RestTemplate client) {
         //新建Http头，add方法可以添加参数
         HttpHeaders headers = new HttpHeaders();
         //设置请求发送方式
         HttpMethod method = HttpMethod.POST;
        // 以json的方式提交
         headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        String json = client.postForEntity(url, jsonString, String.class).getBody();
        return json;
    }

    /**
     * 向目的URL发送get请求
     * @param url       目的url
     * @param     param1 起始时间  param2 结束时间
     * @return  String
     */
    public static String sendGetRequest(String url, String param1,String param2,RestTemplate client){
         HttpMethod method = HttpMethod.GET;
         HttpHeaders headers = new HttpHeaders();
         // 以json方式提交
         headers.setContentType(MediaType.APPLICATION_JSON);
         // 执行HTTP请求，将返回的结构使用String 类格式化
         String json = client.getForEntity(url,String.class,param1,param2).getBody();
         return json;
    }

    /**
     *@Description 判断设备是否在线
     *@param 
     *@return 
     */
    public static boolean isAlive(String ip){
        boolean result = false;
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            result = inetAddress.isReachable(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }





}
