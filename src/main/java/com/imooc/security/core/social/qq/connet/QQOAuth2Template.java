package com.imooc.security.core.social.qq.connet;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class QQOAuth2Template extends OAuth2Template {
	
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
    public QQOAuth2Template(String clientId, String clientSecret,
			String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    return restTemplate;
	}
	
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl,
			MultiValueMap<String, String> parameters) {
	    String accessTokenStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
	    logger.info("获取到的accessTokenStr= "+accessTokenStr);
	    String[] accessTokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(accessTokenStr,"&");
	    String accessToken=StringUtils.substringAfterLast(accessTokens[0], "=");
	    Long expiresIn=Long.valueOf(StringUtils.substringAfterLast(accessTokens[1], "="));
	    String refreshToken=StringUtils.substringAfterLast(accessTokens[2], "=");
	    return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
	

}
