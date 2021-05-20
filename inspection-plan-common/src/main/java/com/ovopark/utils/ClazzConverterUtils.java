package com.ovopark.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Classname ClazzConverterUtils
 * @Description 转换类
 * @Date 2020/7/16 4:49 下午
 * @Created by liuhao
 */
public class ClazzConverterUtils {

    public static <T1, T2> T1 converterClass(T2 srcClazz, Class<T1> dstClazz) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(srcClazz);
        return jsonObject == null ? null : JSONObject.toJavaObject(jsonObject, dstClazz);
    }

    public static <T1, T2> T1 converterClass(T2 srcClazz, TypeReference<T1> type) {
        String json = JsonUtils.toJson(srcClazz);
        return json == null ? null : JsonUtils.fromJson(json, type);
    }

    public static <T1, T2> List<T1> converterClass(Collection<T2> srcClazzCollection, Class<T1> dstClazz) {
        JSONArray jsonArray = (JSONArray) JSONObject.toJSON(srcClazzCollection);
        return jsonArray == null ? new ArrayList<>() : JSONArray.parseArray(jsonArray.toJSONString(), dstClazz);
    }

    public static <T1, T2> T1[] converterClass(T2[] srcClazzArray, Class<T1> dstClazz) {
        JSONArray jsonArray = (JSONArray) JSONObject.toJSON(srcClazzArray);
        if (jsonArray == null) {
            return null;
        } else {
            List<T1> result = JSONArray.parseArray(jsonArray.toJSONString(), dstClazz);
            return result == null ? null : (T1[]) result.toArray();
        }
    }
}
