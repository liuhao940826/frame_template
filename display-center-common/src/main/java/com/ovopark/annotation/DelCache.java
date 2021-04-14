package com.ovopark.annotation;


import java.lang.annotation.*;

/**
 * @Classname DelCache
 * @Description 删除缓存
 * @Date 2020/7/16 4:49 下午
 * @Created by liuhao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DelCache {

	/**
	 * 缓存的key，支持占位符，例如 sys:user:${userId}或者sys:user:${user.id}, 自动取方法入参替换进去
	 * 
	 * @return
	 */
	String value();
	
	/**
	 * 当{@link CacheKey}的类型为Hash时，该字段对应子key
	 * 
	 * @return
	 */
	String hashKey() default "";
	
	/**
	 * 缓存类型 {@link CacheType}
	 * @return
	 */
	CacheType type() default CacheType.STRING;
}
