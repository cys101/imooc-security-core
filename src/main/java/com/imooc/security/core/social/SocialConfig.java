package com.imooc.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.imooc.security.core.properties.SecurityProperties;
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required=false)
	private ConnectionSignUp connectionSignUp;
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator){
		JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
		return jdbcUsersConnectionRepository;
	}
	
	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig(){
		ImoocSocialSecurityConfig imoocSocialSecurityConfig = new ImoocSocialSecurityConfig(securityProperties.getSocial().getFilterProcessesUrl());
		imoocSocialSecurityConfig.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		return imoocSocialSecurityConfig;
	}
	
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}

}
