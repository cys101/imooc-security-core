/**
 * 
 */
package com.imooc.security.core.properties;

/**
 * @author T-caoys
 *
 */
public class ValidateCodeProperties {
    private ImagecodeProperties image=new ImagecodeProperties();
    
    private SmscodeProperties sms=new SmscodeProperties();

	public ImagecodeProperties getImage() {
		return image;
	}

	public void setImage(ImagecodeProperties image) {
		this.image = image;
	}

	public SmscodeProperties getSms() {
		return sms;
	}

	public void setSms(SmscodeProperties sms) {
		this.sms = sms;
	}
	
}
