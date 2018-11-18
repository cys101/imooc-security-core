package com.imooc.security.core.social.qq.connet;


import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.imooc.security.core.social.qq.api.QQ;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId,String appid, String appSecret) {
		super(providerId, new QQServiceProvider(appid, appSecret), new QQAdapter());
	}

}
