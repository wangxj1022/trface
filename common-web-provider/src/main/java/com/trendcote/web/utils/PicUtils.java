package com.trendcote.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class PicUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(PicUtils.class);

    //图片转字符串 (正在使用)
    public static String pic2String( File picture){
        FileInputStream fis = null;
        byte[] data = null;

        try {
            fis = new FileInputStream(picture);
            data = new byte[fis.available()];
            fis.read(data);

        } catch (FileNotFoundException e) {
            LOGGER.error("图片找不到,"+ e.toString());
        } catch (IOException e){
            LOGGER.error("IO异常,"+ e.toString());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(data).replace("\r\n","");
        return encode;
    }


    //字符串转图片
    public static boolean string2Pic( String imgStr, String filename){

        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream("D:/Systems/" + filename);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    // 校验图片大小是否符合要求:  base64 判断照片大小是否 0KB ~ 200KB 之间( 推荐范围在 60KB~200KB )
    public static boolean checkPicString( String picStrings ){
        int equalIndex = picStrings.indexOf('=');
        if(picStrings.indexOf('=')>0)
        {
            picStrings=picStrings.substring(0, equalIndex);
        }
        int strlength = picStrings.length();
        int fileLength= (strlength-(strlength/8)*2)/1024;
        if( fileLength > 0 && fileLength <= 200 ){
            return true;
        }else {
            return false;
        }
    }


    /**
     *@Description 校验是否为图片
     *@param
     *@return
     */
    public static boolean checkImg(File file) {

        boolean flag = false;
        if( file.getName().endsWith("jpg") ){
            flag = true;
        }
        return flag;
    }

}
