package com.ovopark.utils;

import java.util.UUID;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 描述
 * @create 2019-11-25 14:34
 */
public class UuidUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getNewId(){

        String newId = System.currentTimeMillis() +
                (Long.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).toString().substring(0, 6));

        return newId;
    }

    public static Long getNewIdByLong(){

        String newId = System.currentTimeMillis() +
                (Long.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).toString().substring(0, 6));
        return Long.parseLong(getNewId());
    }
}
