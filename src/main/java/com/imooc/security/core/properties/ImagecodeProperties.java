package com.imooc.security.core.properties;

public class ImagecodeProperties {
	private int width = 65; 
    private int height = 20;
    private int length = 4;
    private int expireInt = 60;
    private String url;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getExpireInt() {
		return expireInt;
	}
	public void setExpireInt(int expireInt) {
		this.expireInt = expireInt;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
    

}
