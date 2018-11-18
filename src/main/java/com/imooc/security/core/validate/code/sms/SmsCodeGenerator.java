package com.imooc.security.core.validate.code.sms;



import javax.servlet.http.HttpServletRequest;





import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator<SmsCode, HttpServletRequest> {
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public SmsCode generator(HttpServletRequest request) {
		 return new SmsCode(RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength()),securityProperties.getCode().getSms().getExpireInt());
	}
	
    

}
