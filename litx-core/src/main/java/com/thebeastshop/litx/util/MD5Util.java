package com.thebeastshop.litx.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;

public class MD5Util {

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
    public final static String makeMD5(String password) {
  		MessageDigest md;   
  		   try {   
  		    // 生成一个MD5加密计算摘要   
  		    md = MessageDigest.getInstance("MD5");   
  		    // 计算md5函数   
  		    md.update(password.getBytes());   
  		    // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符   
  		    // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值   
  		    String pwd = new BigInteger(1, md.digest()).toString(16);   
  		    //System.err.println(pwd);   
  		    return pwd;   
  		   } catch (Exception e) {   
  		    e.printStackTrace();   
  		   }   
  		   return password;   
      }
}
