package com.leer.googlemarket.domain;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/26.
 */

public class AppInfoDetail {
    public String author;
    public String date;
    public String des;
    public String downloadNum;
    public String downloadUrl;
    public String iconUrl;
    public long id;
    public String name;
    public String packageName;

    public long size;
    public float stars;
    public String version;
    public ArrayList<Safe> safe;
    public ArrayList<String> screen;


    public class Safe{
        public String safeDes;
        public String safeDesUrl;
        public String safeUrl;
    }


}
