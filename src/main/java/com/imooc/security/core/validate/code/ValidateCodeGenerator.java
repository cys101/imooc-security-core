package com.imooc.security.core.validate.code;

public interface ValidateCodeGenerator<T,P>{
	
	public T generator (P p);

}
