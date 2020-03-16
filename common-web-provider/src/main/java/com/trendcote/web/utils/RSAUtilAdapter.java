package com.trendcote.web.utils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAUtilAdapter {
  
  /**
   * 利用公钥进行RSA加密
   * @param content
   * @param publicKey
   * @return
   * @throws Exception
   */
  public static String encrypt(String content, String publicKey) throws Exception {
    PublicKey puk = RSAUtil.string2PublicKey(publicKey);
    String result = RSAUtil.byte2Base64(RSAUtil.publicEncrypt(content.getBytes(), puk)).replaceAll("\r\n|\r|\n|\n\r", "").replaceAll("\\+", "%2B");
    return result;
  }
  
  /**
   * 利用私钥进行RSA解密
   * @param content
   * @param privateKey
   * @return
   * @throws Exception
   */
  public static String decrypt(String content, String privateKey) throws Exception {
    PrivateKey prk = RSAUtil.string2PrivateKey(privateKey);
    String result = content.replaceAll("%2B", "+");
    result = new String(RSAUtil.privateDecrypt(RSAUtil.base642Byte(result), prk));
    return result;
  }

  public static void main(String[] args) throws Exception {
    KeyPair keypair = RSAUtil.getKeyPair();
    String publicKey = RSAUtil.getPublicKey(keypair);
    String privateKey = RSAUtil.getPrivateKey(keypair);
    
    String encryptContent = encrypt("atest123*&^", publicKey);
    String decryptContent = decrypt(encryptContent, privateKey);
    System.out.println(encryptContent);
    System.out.println(decryptContent);
  }
}
