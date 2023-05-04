package com.tchstart.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.tchstart.blog.constant.CacheKeyPrefix;
import com.tchstart.blog.support.cache.MemoryCacheStore;
import com.tchstart.blog.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Statistic Interceptor
 *
 * @author tchstart
 */
@Component
@Slf4j
public class StatisticInterceptor implements HandlerInterceptor {

	@Autowired
	private MemoryCacheStore cache;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 统计网站访问人数

		String ipAddress = IPUtils.getIpAddress(request);

		// 缓存在 StatisticTask 中清除
		String webVisitKey = CacheKeyPrefix.WEB_VISIT_COUNT + ipAddress;
		cache.setIfAbsent(webVisitKey, null);

		return true;
	}
}
