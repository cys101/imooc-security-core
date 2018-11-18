package com.imooc.security.core.social.qq.api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private String appid;
	
	private String openid;
	
	//获取openid
	private static final String URL_GET_OPENID="https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	//获取用户信息
	private static final String  URL_GET_USERINFO="https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	private ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public QQUserInfo getUserInfo() {
		String url=String.format(URL_GET_USERINFO, appid,openid);
		String result = getRestTemplate().getForObject(url, String.class);
		logger.info("获取的用户信息为："+result);
		QQUserInfo qqserInfo;
		try {
			qqserInfo = objectMapper.readValue(result, QQUserInfo.class);
			qqserInfo.setOpenId(openid);
		} catch (Exception e) {
			logger.info("获取qq用户信息失败：appid = "+appid+" openid = "+openid );
			throw new RuntimeException("获取用户信息失败 ："+e);
		}
		return qqserInfo;
	}
	
	public QQImpl(String appid,String accessToken){
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appid=appid;
		String url=String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		openid=StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
		logger.info("该用户的openid = " +openid);
	}
	
	

}
