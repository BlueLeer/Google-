package com.leer.googlemarket.utils;

import java.io.Closeable;
import java.io.IOException;

/**y用于将二进制IO流关闭的工具
 * Created by Leer on 2017/5/10.
 */

public class IOUtils {
    public static boolean close(Closeable c){
        if(c != null){
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
