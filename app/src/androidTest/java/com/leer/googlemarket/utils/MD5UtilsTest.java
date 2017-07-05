package com.leer.googlemarket.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Leer on 2017/5/10.
 */
public class MD5UtilsTest {
    @Test
    public void encoding() throws Exception {
        String s = MD5Utils.encoding("12345678");
        System.out.println(s+"xxxxxxxxxx");
    }


}