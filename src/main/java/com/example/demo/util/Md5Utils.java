package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author xiaoyang
 */
public class Md5Utils {

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5Encrypt(String text) throws Exception {
        byte[] bytes = text.getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        bytes = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean md5Verify(String text, String md5) throws Exception {
        String md5Text = md5Encrypt(text);
        return md5Text.equalsIgnoreCase(md5);
    }

    /**
     * 短信md5编码
     *
     * @param bts
     * @return
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 短信md5加密
     *
     * @param strSrc
     * @return
     */
    public static String md5(String strSrc) {
        MessageDigest md = null;
        try {
            byte[] encryptSrc = strSrc.getBytes(StandardCharsets.UTF_8);
            md = MessageDigest.getInstance("MD5");
            md.update(encryptSrc);
            return bytes2Hex(md.digest()).toUpperCase();
        } catch (Exception e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
    }
}
