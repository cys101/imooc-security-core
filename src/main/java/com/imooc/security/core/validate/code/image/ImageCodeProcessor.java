package com.imooc.security.core.validate.code.image;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.impl.AbstractValidateCodeProcessor;
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	@Override
	protected void send(ServletWebRequest request, ImageCode code) throws IOException {
		ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

	

}
