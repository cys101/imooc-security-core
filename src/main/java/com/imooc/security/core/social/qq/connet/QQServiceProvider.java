package com.imooc.security.core.social.qq.connet;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQImpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
	private String appid;
	
	private static final String URL_AUTHORIZE="https://graph.qq.com/oauth2.0/authorize";
	
    private static final String URL_ACCESS_TOKEN="https://graph.qq.com/oauth2.0/token";

	public QQServiceProvider(String appid,String appSecret) {
		super(new QQOAuth2Template(appid, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appid=appid;
	}

	@Override
	public QQ getApi(String accessToken) {
		QQImpl qqImpl = new QQImpl(appid, accessToken);
		return qqImpl;
	}

	

}
