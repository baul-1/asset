package com.copin.asset.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RelErrorController implements ErrorController {
	
	@RequestMapping("error")
	public ModelAndView handleError(ModelAndView mv, HttpServletRequest request) {
		Object obj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(obj != null) {

			Integer errorCode = Integer.valueOf(obj.toString());

			if( errorCode == HttpStatus.BAD_REQUEST.value() ) {
				mv.setViewName("errorpage/400error");
			}else if(errorCode == HttpStatus.NOT_FOUND.value()) {
				mv.setViewName("errorpage/404error");
				return mv;
			} else if( errorCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				mv.setViewName("errorpage/500error");
				return mv;
			}
		}
		
		return mv;
		
	}

}
