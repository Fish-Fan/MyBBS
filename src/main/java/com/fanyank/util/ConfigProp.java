package com.fanyank.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yanfeng-mac on 2017/3/28.
 * 读取properties文件的值
 */
public class ConfigProp {
    private static Properties prop = new Properties();
    static {
        try {
            prop.load(ConfigProp.class.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key 键
     * @return properties值
     */

    public static String get(String key) {return prop.getProperty(key);}
}
