package com.imooc.security.core.properties;

public class SocialProperties {
	private String filterProcessesUrl="/auth";
	
	private QQProperties qq=new QQProperties();
	
	private WXProperties wx=new WXProperties();

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public WXProperties getWx() {
		return wx;
	}

	public void setWx(WXProperties wx) {
		this.wx = wx;
	}
	
	
	
	
	

}
