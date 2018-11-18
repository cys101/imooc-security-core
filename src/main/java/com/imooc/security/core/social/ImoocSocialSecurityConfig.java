package com.imooc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class ImoocSocialSecurityConfig extends SpringSocialConfigurer {
	
	private String filterProcessesUrl;
	
	public ImoocSocialSecurityConfig(String filterProcessesUrl){
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter socialFilter = (SocialAuthenticationFilter)super.postProcess(object);
		socialFilter.setFilterProcessesUrl(filterProcessesUrl);
		 return (T)socialFilter;
	}

}
