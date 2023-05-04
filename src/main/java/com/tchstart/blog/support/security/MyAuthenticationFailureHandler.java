package com.tchstart.blog.support.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.tchstart.blog.annotation.ActionRecord;
import com.tchstart.blog.constant.RS;
import com.tchstart.blog.model.R;
import com.tchstart.blog.model.enums.LogType;
import com.tchstart.blog.utils.RUtils;
import com.tchstart.blog.utils.RWriterUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@ActionRecord(content = "#exception.getMessage()", type = LogType.LOGIN_FAILED)
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
			, AuthenticationException exception) throws IOException, ServletException {
		R result = RUtils.fail(exception instanceof BadCredentialsException
				? RS.USERNAME_PASSWORD_ERROR
				: RS.SYSTEM_ERROR);
		RWriterUtils.writeJson(response, result);
	}
}
