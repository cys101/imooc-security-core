package com.imooc.security.core.validate.code.sms;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.image.ImageCode;

public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean{
    
	private AuthenticationFailureHandler authenticationFailureHandler;
    
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    
    private SecurityProperties securityProperties;
    
    private Set<String> urls = new HashSet<String>();
    
    private AntPathMatcher pathMatcher=new AntPathMatcher();
    
    @Override
    public void afterPropertiesSet() throws ServletException {
    	super.afterPropertiesSet();
    	if(StringUtils.isNotEmpty(securityProperties.getCode().getSms().getUrl())){
    		String[] s = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(), ",");
        	for(String url : s){
        		urls.add(url);
        	}
        	urls.add("/authentication/mobile");
    	}
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean action=false;
	    for(String url:urls){
	    	if(pathMatcher.match(url, request.getRequestURI())){
	    		action=true;
	    		break;
	    	}
	    	
	    }
		if(action){
			try {
				Validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
			
		}
		filterChain.doFilter(request, response);

	}
	public void Validate(ServletWebRequest request) {
		SmsCode smsCode = (SmsCode)sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
		String code = request.getParameter("smsCode");
		if(StringUtils.isBlank(code)){
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(smsCode==null){
			throw new ValidateCodeException("验证码的值不存在");
		}
		if(smsCode.isExpire()){
			sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
			throw new ValidateCodeException("验证码过期");
			
		}
		if(!StringUtils.equals(code, smsCode.getCode())){
			throw new ValidateCodeException("验证码不正确");
		}
		sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
	}
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}
	public void setAuthenticationFailureHandler(
			AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}
	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}
	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	public Set<String> getUrls() {
		return urls;
	}
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}
	
	

}
