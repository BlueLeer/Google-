package com.leer.googlemarket.utils;

/**用于判断字符串是否为空,或者为null,毕TextUtils.isEmpty()判断的情况更多一些
 * Created by Leer on 2017/5/10.
 */

public class StringUtils {
    /** 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }
}
