package com.tchstart.blog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.tchstart.blog.annotation.AccessLimit;
import com.tchstart.blog.constant.CacheKeyPrefix;
import com.tchstart.blog.constant.RS;
import com.tchstart.blog.exception.ServiceException;
import com.tchstart.blog.support.cache.MemoryCacheStore;
import com.tchstart.blog.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 接口限流防刷拦截器
 *
 * @author tchstart
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

	private final MemoryCacheStore memoryCacheStore;

	public AccessLimitInterceptor(MemoryCacheStore memoryCacheStore) {
		this.memoryCacheStore = memoryCacheStore;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		AccessLimit annotation = method.getAnnotation(AccessLimit.class);
		if (Objects.nonNull(annotation)) {
			check(annotation, request);
		}

		return true;
	}

	private void check(AccessLimit annotation, HttpServletRequest request) {

		int maxCount = annotation.maxCount();
		int seconds = annotation.seconds();

		String key = CacheKeyPrefix.ACCESS_LIMIT_PREFIX
				+ IPUtils.getIpAddress(request)
				+ request.getRequestURI();

		Integer count = (Integer) memoryCacheStore.get(key);
		if (Objects.nonNull(count)) {
			if (count < maxCount) {
				memoryCacheStore.set(key, count + 1, seconds);
			} else {
				throw new ServiceException(RS.FREQUENT_OPERATION);
			}
		} else {
			memoryCacheStore.set(key, 1, seconds);
		}
	}
}
