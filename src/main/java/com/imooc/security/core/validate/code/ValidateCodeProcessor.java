package com.imooc.security.core.validate.code;


import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {
	
	String SESSION_KEY_PREFIX="SESSION_KEY_FOR_CODE_";
	
	
	
	public void create(ServletWebRequest request) throws Exception;
	
	
	public void validate(ServletWebRequest servletWebRequest) throws ValidateCodeException;

}
