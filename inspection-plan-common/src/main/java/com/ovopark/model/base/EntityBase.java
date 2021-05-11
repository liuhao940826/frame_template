package com.ovopark.model.base;

import com.ovopark.constants.CommonConstants;
import com.ovopark.context.HttpContext;
import com.ovopark.model.enums.DeleteEnum;
import com.ovopark.model.login.Users;
import com.ovopark.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 描述
 * @create 2019-11-30 14:24
 */
public class EntityBase {


    private static final Logger logger = LoggerFactory.getLogger(EntityBase.class);

    /**
     * 初始化创建通用属性
     * @param object
     * @param user
     */
    public static void setCreateParams(Object object, Users user){
    	setCreateParams(object, user.getId());
    }
    
    public static void setCreateParams(Object object, Integer userId){
        Date now = new Date();

        Class<?> clazz = object.getClass();

        Method[] declaredMethods= clazz.getDeclaredMethods();

        for(Method method : declaredMethods){
            try
            {
                if(method.getName().equals("setCreateId") || method.getName().equals("setUpdateId"))
                {
                    method.invoke(object, userId);
                }
                else if(method.getName().equals("setCreateTime") || method.getName().equals("setUpdateTime"))
                {
                    method.invoke(object, now);
                }
                else if(method.getName().equals("setIsDelete") || method.getName().equals("setDelFlag"))
                {
                    method.invoke(object, DeleteEnum.NOT_DELETED.getCode());
                }
                else if(method.getName().equals("setVersion"))
                {
                    method.invoke(object, CommonConstants.DEFAULT_VERSION);
                }
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 初始化更新通用属性
     * @param object
     * @param user
     */
    public static void setUpdateParams(Object object, Users user){
        Class clazz = object.getClass();
        Method[] declaredMethods= clazz.getDeclaredMethods();

        Date now = new Date();

        for(Method method : declaredMethods){
            try
            {
                if(method.getName().equals("setUpdateId"))
                {
                    method.invoke(object, user.getId());
                }
                else if(method.getName().equals("setUpdateTime"))
                {
                    method.invoke(object, now);
                }
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 过滤有权限的对象
     * @param objectCode
     * @param object
     * @param editObject
     * @param allowFields
     * @return
     */

    /**
     * 反射获取对象id，需要有getId() 方法
     * @param object
     * @return
     */
    private static String getObjectId(Object object){
        Class clazz = object.getClass();

        try {
            Method method = clazz.getDeclaredMethod("getId");
            Object id = method.invoke(object);
            if (null == id){
                return "";
            }
            return id.toString();
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }

        return "";
    }



}
