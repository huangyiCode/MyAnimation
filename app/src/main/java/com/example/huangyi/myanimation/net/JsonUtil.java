package com.example.huangyi.myanimation.net;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * JSON解析
 * Created by wangyu on 2015/8/17.
 */
public class JsonUtil {

    public static ResultBean toJavaBeanResult(String resultStr) throws Exception {
        try {
            ResultBean result = JSON.parseObject(resultStr, ResultBean.class);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    public static ResultBean toJavaBean(String resultStr, Class clazz) throws Exception {
        try {
            ResultBean result = JSON.parseObject(resultStr, ResultBean.class);
            Object o = JSON.parseObject(JSON.toJSONString(result.object), clazz);
            result.object = o;
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public static ResultBean toJavaBeanList(String resultStr, Class clazz) throws Exception {
        try {
            ResultBean result = JSON.parseObject(resultStr, ResultBean.class);
            List tList = JSON.parseArray(JSON.toJSONString(result.object), clazz);
            result.object = tList;
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public static ResultBean toJavaBeanListResult(String resultStr) throws Exception {
        try {
            ResultBean result = JSON.parseObject(resultStr, ResultBean.class);
            String s = JSON.toJSONString(result.object);
            List tList;
            if (s.length() > 2) {
                tList = JSON.parseArray(s);
            } else {
                tList = null;
            }
            result.object = tList;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
