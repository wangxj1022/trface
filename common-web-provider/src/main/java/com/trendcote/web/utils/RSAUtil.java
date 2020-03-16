package com.trendcote.web.utils;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;  
  
import javax.crypto.Cipher;


import com.alibaba.fastjson.JSONObject;
import org.apache.axis.encoding.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;  
  
public class RSAUtil {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    // ------  EHR 系统加密方法  ( 员工信息 )

    //生成秘钥对  
    public static KeyPair getKeyPair() throws Exception {  
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");  
        keyPairGenerator.initialize(2048);  
        KeyPair keyPair = keyPairGenerator.generateKeyPair();  
        return keyPair;  
    }  
      
    //获取公钥(Base64编码)  
    public static String getPublicKey(KeyPair keyPair){  
        PublicKey publicKey = keyPair.getPublic();  
        byte[] bytes = publicKey.getEncoded();  
        return byte2Base64(bytes);  
    }  
      
    //获取私钥(Base64编码)  
    public static String getPrivateKey(KeyPair keyPair){  
        PrivateKey privateKey = keyPair.getPrivate();  
        byte[] bytes = privateKey.getEncoded();  
        return byte2Base64(bytes);  
    }  
      
    //将Base64编码后的公钥转换成PublicKey对象  
    public static PublicKey string2PublicKey(String pubStr) throws Exception{  
        byte[] keyBytes = base642Byte(pubStr);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    }  
      
    //将Base64编码后的私钥转换成PrivateKey对象  
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{  
        byte[] keyBytes = base642Byte(priStr);  
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
        return privateKey;  
    }  
      
    //公钥加密  
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        byte[] bytes = cipher.doFinal(content);  
        return bytes;  
    }  
      
    //私钥解密  
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        byte[] bytes = cipher.doFinal(content);  
        return bytes;  
    }  
      
    //字节数组转Base64编码  
    public static String byte2Base64(byte[] bytes){  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(bytes);  
    }  
      
    //Base64编码转字节数组  
    public static byte[] base642Byte(String base64Key) throws IOException{  
        BASE64Decoder decoder = new BASE64Decoder();  
        return decoder.decodeBuffer(base64Key);  
    }

    public static JSONObject rsaAnalysis(String parameters,String privateKeyStr) {
      try {
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);  
        //加密后的内容Base64解码  
        byte[] base642Byte = RSAUtil.base642Byte(parameters);  
        //用私钥解密  
        byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);  
        //解密后的明文  
        return JSONObject.parseObject(new String(privateDecrypt));
      } catch (Exception e) {
        JSONObject json = new JSONObject();
        json.put("resultFlag", false);
        json.put("msg", "无效的证书");
        return json;
      }
    }


    // ------  EOA 系统加密方法 ( 访客信息 )

    /**
     *
     * 作者：nietaibing
     * 创建时间：2016年11月3日 下午2:16:53
     * 功能描述:BASE64解密
     * @param key:秘钥
     * @return 解密后的内容
     * 版本：V1.0
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        Base64 base64 = new Base64();
        return base64.decode(key);
    }

    /**
     *
     * 作者：nietaibing
     * 创建时间：2016年11月3日 下午2:43:15
     * 功能描述:用公钥解密
     * @param encryptedData:待解密内容；publicKey:公钥
     * @return 解密后的内容
     * @throws Exception
     * 版本：V1.0
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(publicKey);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key key = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    // 解密EOA系统 使用方法
    public static String decryptEOADataByPublicKey(String msg, String publicKey){
        String data = "";
        byte[] bytes = new byte[0];
        try {
            //  解密数据
            byte[] resp = RSAUtil.decryptBASE64(msg.replaceAll("%2B", "\\+"));
            bytes = RSAUtil.decryptByPublicKey(resp, publicKey);
            data = new String(bytes,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }



}