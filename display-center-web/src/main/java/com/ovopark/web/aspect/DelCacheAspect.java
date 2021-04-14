package com.ovopark.web.aspect;

import com.ovopark.annotation.CacheType;
import com.ovopark.annotation.DelCache;
import com.ovopark.utils.RedisUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Classname DelCacheAspect
 * @Description 删除缓存切面
 * @Date 2020/7/16 4:49 下午
 * @Created by liuhao
 */
@Aspect
@Component
public class DelCacheAspect {

	private static final Logger log = LoggerFactory.getLogger(DelCacheAspect.class);

	@Autowired
	private RedisUtil redisUtil;

	private static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
			"${", "}", ".", false);

	@Pointcut("@annotation(com.ovopark.annotation.DelCache)")
	public void pointCut() {

	}

	@Around("pointCut()")
	public Object arround(ProceedingJoinPoint point) throws Throwable{
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();

		String[] methodArgNames = signature.getParameterNames();
		Object[] methodArgs = point.getArgs();
		Properties methodArgNameMap = new Properties();
		if(! ArrayUtils.isEmpty(methodArgNames)) {
			for(int index = 0; index < methodArgNames.length; index ++) {
				methodArgNameMap.put(methodArgNames[index], String.valueOf(methodArgs[index]));
			}
		}

		DelCache autoCache = method.getAnnotation(DelCache.class);
		String cacheKey = autoCache.value();
		String hashKey = autoCache.hashKey();

		CacheType cacheType = autoCache.type();

		cacheKey = helper.replacePlaceholders(cacheKey, methodArgNameMap);
		log.info("DelCache 获取到key {}", cacheKey);

		switch (cacheType){
		case STRING:
			return handleStringCache(point, method, cacheKey);
		case HASH:
			if (! StringUtils.isEmpty(hashKey)){
				hashKey = helper.replacePlaceholders(hashKey, methodArgNameMap);
				return handleHashCache(point, method, cacheKey, hashKey);
			}
		}

		return  point.proceed();
	}

	public Object handleStringCache(ProceedingJoinPoint point, Method method, String cacheKey) throws Throwable{
		try {
			return point.proceed();
		}finally {
			redisUtil.del(cacheKey);
		}
	}

	public Object handleHashCache(ProceedingJoinPoint point, Method method, String cacheKey, String hashKey) throws Throwable{
		try {
			return point.proceed();
		}finally {
			redisUtil.hdel(cacheKey, hashKey);
		}
	}
}
