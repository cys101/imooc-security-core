package com.imooc.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.imooc.security.core.authentication.mobile.SmsCodeAuthencationFilter;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationProvider;

@Component
public class SmsCodeAuthenticationSecutiryConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		SmsCodeAuthencationFilter smsCodeAuthencationFilter = new SmsCodeAuthencationFilter(); 
		smsCodeAuthencationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsCodeAuthencationFilter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);
		smsCodeAuthencationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
		
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		http.authenticationProvider(smsCodeAuthenticationProvider)
		    .addFilterAfter(smsCodeAuthencationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

}
