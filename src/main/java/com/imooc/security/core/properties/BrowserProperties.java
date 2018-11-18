package com.imooc.security.core.properties;

import com.imooc.security.core.constants.SecurityConstants;



public class BrowserProperties {
	private String loginPage=SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	private LoginType loginType=LoginType.JSON;
	
	private int rememberMeSeconds=3600;
	
	private String  signUpUrl="/imooc-signUp.html";

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}
	
	
	

}
