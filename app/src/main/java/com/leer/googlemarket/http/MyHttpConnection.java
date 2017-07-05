package com.leer.googlemarket.http;

import android.text.TextUtils;

import com.leer.googlemarket.utils.IOUtils;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.MD5Utils;
import com.leer.googlemarket.utils.UIUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Leer on 2017/5/10.
 */

public class MyHttpConnection {

    public static String getData(String url) {
        //先从缓存中获取,如果没有的话再从网络获取
        String result = getDataFromCache(url);
        boolean isEmpty = TextUtils.isEmpty(result);
        if (isEmpty) {
            //如果从缓存中获取的数据为空,则直接从服务器上获取,并将数据缓存到内存中
            result = getDataFromServer(url);
            putDataIntoCache(url, result);
        }
        return result;
    }


    /**
     * @return 返回内存当中的数据, JSON格式
     * 当文件已经过期的时候,就返回null
     */
    private static String getDataFromCache(String url) {
        File file = UIUtils.getContext().getCacheDir();
        String cacheFileName = MD5Utils.encoding(url);
        File cacheFile = new File(file, cacheFileName);
        StringBuffer retureJson = new StringBuffer();
        BufferedReader reader = null;
        //如果该文件存在,说明有缓存
        if (cacheFile.exists()) {
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                long deadLine = Long.parseLong(reader.readLine());
                long currentTime = System.currentTimeMillis();

                //判断缓存有没有过期
                if (deadLine < currentTime) {
                    retureJson = null;
                    LogUtils.d("MyHttpCOnnection----" + "缓存内容过期啦...");
                } else {
                    String line = null;
                    while ((line = (reader.readLine())) != null) {
                        retureJson.append(line);
                    }
                    LogUtils.d("MyHttpCOnnection----" + "缓存内容没有过期啦...");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }

        } else {
            //否则没有缓存,直接返回null;
            retureJson = null;
            LogUtils.d("MyHttpCOnnection----" + "没有缓存啦...");

        }

        if (retureJson == null) {
            return null;
        }

        return retureJson.toString();
    }


    private static void putDataIntoCache(String url, String json) {
        File file = UIUtils.getContext().getCacheDir();
        String cacheFileName = MD5Utils.encoding(url);
        File cacheFile = new File(file, cacheFileName);
        FileWriter writer = null;

        try {
            writer = new FileWriter(cacheFile);
            //先向文件的首行写入有效截止日期
            long deadLineTime = System.currentTimeMillis() + 30 * 60 * 1000;
            writer.write(deadLineTime + "\n");//写入文件有效期的截止日期
            writer.write(json);//写入json数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
        LogUtils.d("MyHttpCOnnection----" + "写入缓存...");
    }

    /**
     * @return 返回从服务器端获得到的数据, JSON格式
     */
    private static String getDataFromServer(String urlStr) {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            InputStream in = connection.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];
            int temp;
            while ((temp = (in.read(bytes))) > 0) {
                bos.write(bytes, 0, temp);
            }

            return bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        LogUtils.d("MyHttpCOnnection----" + "从服务器获取数据...");
        return null;
    }


}
