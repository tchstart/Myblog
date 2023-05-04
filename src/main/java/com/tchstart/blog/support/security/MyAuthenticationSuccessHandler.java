package com.tchstart.blog.support.security;/**
 * todo
 *
 * @author tchstart
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.tchstart.blog.annotation.ActionRecord;
import com.tchstart.blog.config.properties.MyBlogProperties;
import com.tchstart.blog.constant.CacheKeyPrefix;
import com.tchstart.blog.model.UserDetail;
import com.tchstart.blog.model.enums.LogType;
import com.tchstart.blog.model.vo.UserVO;
import com.tchstart.blog.support.cache.MemoryCacheStore;
import com.tchstart.blog.utils.IPUtils;
import com.tchstart.blog.utils.RUtils;
import com.tchstart.blog.utils.RWriterUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private MemoryCacheStore memoryCacheStore;

	@Autowired
	private MyBlogProperties properties;

	@ActionRecord(content = "#authentication.getName()", type = LogType.LOGGED_IN)
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
			, Authentication authentication) throws IOException, ServletException {

		String ipAddress = IPUtils.getIpAddress(request);
		log.info("用户 [{}] 登录系统，IP：[{}]", authentication.getName(), ipAddress);
		// 登录成功，重置登录失败次数
		String key = CacheKeyPrefix.LOGIN_FAILED_COUNT + ipAddress;
		memoryCacheStore.set(key, 0, properties.getAllowLoginFailureSeconds());
		// 返回登录结果
		UserDetail userDetails = (UserDetail) authentication.getPrincipal();
		UserVO userVO = new UserVO().convertFrom(userDetails);
		RWriterUtils.writeJson(response, RUtils.success("登录成功", userVO));
	}
}
