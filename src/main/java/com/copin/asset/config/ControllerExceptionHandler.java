package com.copin.asset.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {
	
	
	@ExceptionHandler(SizeLimitExceededException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, Object> MultipartException(SizeLimitExceededException e){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(e.getMessage());
		map.put("result", 4);
		return map;
	}
	
	@ExceptionHandler(NullPointerException.class)
	public Map<String,Object> NullPointerException(NullPointerException e){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cause", e.getCause());
		e.printStackTrace();
		return map;
	}
	
}
