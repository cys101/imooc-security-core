package com.imooc.security.core.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.ValidateCodeType;
@SuppressWarnings("all")
public abstract class AbstractValidateCodeProcessor <T extends ValidateCode> implements ValidateCodeProcessor{
	
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    
	@Override
	public void create(ServletWebRequest request) throws Exception {
		T code = generator(request);
		save(request, code);
		send(request,code);
	}
	public void save(ServletWebRequest request,T code){
		sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX+getGeneratorType(request).toUpperCase(), code);
	}
	protected abstract void send(ServletWebRequest request,T code) throws Exception;
	
	public T generator(ServletWebRequest request){
		String type = getGeneratorType(request);
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator");
		T generator = (T) validateCodeGenerator.generator(request.getRequest());
		return generator;
	}
	
	public String getGeneratorType(ServletWebRequest request){
		return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
	}
	
	public String getSessionKey(ServletWebRequest request){
		return SESSION_KEY_PREFIX+getValidateCodeType(request).toString().toUpperCase();
	}
	
	@Override
	public void validate(ServletWebRequest request) throws ValidateCodeException{
		ValidateCodeType validateCodeType = getValidateCodeType(request);//获取validate类型
		String sessionKey = getSessionKey(request);
		
		T code = (T)sessionStrategy.getAttribute(request, sessionKey);
		
		String codeParam;
		try {
			codeParam=ServletRequestUtils.getStringParameter(request.getRequest(), validateCodeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}
		if(StringUtils.isBlank(codeParam)){
			throw new ValidateCodeException("验证码不能为空");
		}
		if(code==null){
			throw new ValidateCodeException("验证码不存在");
		}
		if(code.isExpire()){
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException("验证码已失效");
		}
		if(!StringUtils.equals(codeParam, code.getCode())){
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, sessionKey);
		
	}
	
	private ValidateCodeType getValidateCodeType(ServletWebRequest servletWebRequest){
		String before = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(before.toUpperCase());
	}
	
	

}
