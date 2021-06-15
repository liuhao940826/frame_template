package self.alan.web.aspect;

import com.alibaba.fastjson.JSON;
import self.alan.annotation.AutoCache;
import self.alan.annotation.CacheType;
import self.alan.constants.CommonConstants;
import self.alan.utils.RedisUtil;
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
 * @Classname AutoCacheAspect
 * @Description 自动缓存切面
 * @Date 2020/7/16 4:49 下午
 * @Created by liuhao
 */
@Aspect
@Component
public class AutoCacheAspect {

	private static final Logger log = LoggerFactory.getLogger(AutoCacheAspect.class);

	@Autowired
	private RedisUtil redisUtil;

	private static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
			"${", "}", ".", false);

	@Pointcut("@annotation(self.alan.annotation.AutoCache)")
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

		AutoCache autoCache = method.getAnnotation(AutoCache.class);
		String cacheKey = autoCache.value();
		String hashKey = autoCache.hashKey();
		long expire = autoCache.expire();
		long expireShakeRange = autoCache.expire();
		if (expire > 0 && expireShakeRange > 0 && expireShakeRange < expire) {
			expire = expire - (long)(Math.random() * expireShakeRange);
		}

		CacheType cacheType = autoCache.type();

		cacheKey = helper.replacePlaceholders(cacheKey, methodArgNameMap);
		log.info("AutoCache 获取到key {}", cacheKey);

		switch (cacheType){
		case STRING:
			return handleStringCache(point, method, cacheKey, expire);
		case HASH:
			if (! StringUtils.isEmpty(hashKey)){
				hashKey = helper.replacePlaceholders(hashKey, methodArgNameMap);
				return handleHashCache(point, method, cacheKey, hashKey, expire);
			}
		}

		return  point.proceed();
	}

	public Object handleStringCache(ProceedingJoinPoint point, Method method, String cacheKey, long expire) throws Throwable{
		String cacheValue = (String) redisUtil.getString(cacheKey);
		Object result = null;
		if(StringUtils.isBlank(cacheValue)) {
			result = point.proceed();
			if(result != null) {
				cacheValue = JSON.toJSONString(result);
				boolean suc = redisUtil.setString(cacheKey, cacheValue);
				if (expire > 0){
					redisUtil.expire(cacheKey, expire);
				}
				log.info("写缓存 key {}, value {} 的状态为{}", cacheKey, cacheValue, suc);
			}else {
				log.info("为空不写缓存 key {}, value {}", cacheKey, cacheValue);
			}
		}else {
			if(! cacheValue.equalsIgnoreCase(CommonConstants.NULL)){
				result = JSON.parseObject(cacheValue, method.getGenericReturnType());
			}
		}
		return result;
	}

	public Object handleHashCache(ProceedingJoinPoint point, Method method, String cacheKey, String hashKey, long expire) throws Throwable{
		String cacheValue = (String) redisUtil.hget(cacheKey, hashKey);
		Object result = null;
		if(StringUtils.isBlank(cacheValue)) {
			result = point.proceed();
			if(result != null) {
				cacheValue = JSON.toJSONString(result);
				boolean suc = redisUtil.hset(cacheKey, hashKey, cacheValue);
				log.info("写缓存 key {}, value {} 的状态为{}", cacheKey, cacheValue, suc);
			}else {
				log.info("为空不写缓存 key {}, value {}", cacheKey, cacheValue);
			}
		}else {
			if(! cacheValue.equalsIgnoreCase(CommonConstants.NULL)){
				result = JSON.parseObject(cacheValue, method.getGenericReturnType());
			}
		}
		return result;
	}
}
