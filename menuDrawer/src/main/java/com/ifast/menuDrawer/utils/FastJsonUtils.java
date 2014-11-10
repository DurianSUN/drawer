package com.ifast.menuDrawer.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhiwen on 2014/11/4.
 */
public class FastJsonUtils {

    public FastJsonUtils(){

    }

    public static String createJsonString(Object object){
        return JSON.toJSONString(object);
    }

    public static <T> T createJsonBean(String jsonString,Class<T> cls){
        return JSON.parseObject(jsonString,cls);
    }

}
