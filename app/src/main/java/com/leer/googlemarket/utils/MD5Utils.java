package com.leer.googlemarket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Leer on 2017/5/10.
 */

public class MD5Utils {
    public static String encoding(String value){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(value.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b:bytes){
                String s;
                int a = b & 0xff;
                if(a < 0x10){
                    s = Integer.toHexString(a)+"0";
                }else{
                    s = Integer.toHexString(a);
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
