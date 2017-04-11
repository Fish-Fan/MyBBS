package com.fanyank.util;

import com.qiniu.util.Auth;

/**
 * Created by yanfeng-mac on 2017/4/11.
 */
public class QiniuUtil {
    public static String getDefaultToken(){
        Auth auth = Auth.create(ConfigProp.get("qiniu.ak"),ConfigProp.get("qiniu.sk"));
        String token = auth.uploadToken(ConfigProp.get("qiniu.bucket"));
        return token;
    }
}
