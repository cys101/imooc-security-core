package com.imooc.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.constants.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.image.ImageCode;
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
    
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
    
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    
    @Autowired 
    private SecurityProperties securityProperties;
    
    
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    
    private Map<String, ValidateCodeType> urlsMap=new HashMap<String, ValidateCodeType>();
    
    private AntPathMatcher pathMatcher=new AntPathMatcher();
    
    @Override
    public void afterPropertiesSet() throws ServletException {
    	super.afterPropertiesSet();
    	urlsMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
    	addUrlToMap(securityProperties.getCode().getImage().getUrl(),ValidateCodeType.IMAGE);
    	
    	urlsMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
    	addUrlToMap(securityProperties.getCode().getSms().getUrl(),ValidateCodeType.SMS);
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ValidateCodeType validateCodeType = this.getValidateCodeType(request);
		if(validateCodeType!=null){
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + validateCodeType);
			try{
				ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType);
				validateCodeProcessor.validate(new ServletWebRequest(request));
				logger.info("验证码校验通过");
			}catch(ValidateCodeException v){
				authenticationFailureHandler.onAuthenticationFailure(request, response, v);
				return;
			}
		}
		filterChain.doFilter(request, response);

	}
	/*public void Validate(ServletWebRequest request) {
		ImageCode imageCode = (ImageCode)sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX);
		String code = request.getParameter("imageCode");
		if(StringUtils.isBlank(code)){
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(imageCode==null){
			throw new ValidateCodeException("验证码的值不存在");
		}
		if(imageCode.isExpire()){
			sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX);
			throw new ValidateCodeException("验证码过期");
			
		}
		if(!StringUtils.equals(code, imageCode.getCode())){
			throw new ValidateCodeException("验证码不正确");
		}
		sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX);
	}*/
	
	public void addUrlToMap(String urls,ValidateCodeType validateCodeType){
		if(StringUtils.isNotEmpty(urls)){
    		String[] s = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        	for(String url : s){
        		urlsMap.put(url, validateCodeType);
        	}
    	}
	}
	
	public ValidateCodeType getValidateCodeType(HttpServletRequest request){
		ValidateCodeType result = null;
		if(!StringUtils.equalsIgnoreCase(request.getMethod(), "get")){
			Set<String> keySet = urlsMap.keySet();
			for(String url:keySet){
				if(pathMatcher.match(url, request.getRequestURI())){
					result=urlsMap.get(url);
					break;
				}
			}
		}
		return result;
		
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
	/*public Set<String> getUrls() {
		return urls;
	}
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}*/
	
	

}
