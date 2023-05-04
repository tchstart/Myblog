package com.tchstart.blog.support.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.tchstart.blog.config.properties.MyBlogProperties;
import com.tchstart.blog.constant.CacheKeyPrefix;
import com.tchstart.blog.constant.RS;
import com.tchstart.blog.exception.ServiceException;
import com.tchstart.blog.model.UserDetail;
import com.tchstart.blog.service.impl.UserServiceImpl;
import com.tchstart.blog.support.cache.MemoryCacheStore;
import com.tchstart.blog.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * 自定义登录业务逻辑
 */
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MemoryCacheStore memoryCacheStore;

	@Autowired
	private MyBlogProperties properties;

	@Autowired
	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		HttpServletRequest request = Optional.ofNullable(IPUtils.getRequest())
				.orElseThrow(() -> new NullPointerException("获取IP地址出错"));

		String ipAddress = IPUtils.getIpAddress(request);

		String key = CacheKeyPrefix.LOGIN_FAILED_COUNT + ipAddress;
		// 获取登录失败的次数
		Integer count = (Integer) memoryCacheStore.get(key);
		// 如果同一IP在规定时间内连续认证错误xx次，则拒绝该ip登录
		if (Objects.nonNull(count)) {
			if (count < properties.getAllowLoginFailureCount()) {
				memoryCacheStore.set(key, count + 1, properties.getAllowLoginFailureSeconds());
			} else {
				log.info("Ip: [{}] 多次登录失败", ipAddress);
				throw new ServiceException(RS.MULTIPLE_AUTHENTICATION_FAILURE);
			}
		} else {
			memoryCacheStore.set(key, 1, properties.getAllowLoginFailureSeconds());
		}

		UserDetail userDetails = userService.getByUsername(username);
		if (Objects.isNull(userDetails)) {
			throw new UsernameNotFoundException("用户[" + username + "]不存在");
		}

		return userDetails;
	}
}
