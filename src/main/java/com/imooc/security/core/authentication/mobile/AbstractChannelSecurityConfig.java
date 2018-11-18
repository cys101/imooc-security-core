package com.imooc.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.imooc.security.core.constants.SecurityConstants;



public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
	      .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//默认的处理URL
	      .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)//usernamepassword默认的处理URL
	      .successHandler(imoocAuthenticationSuccessHandler)
	      .failureHandler(imoocAuthenticationFailureHandler);
	}

}
