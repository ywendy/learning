package com.ypb.token.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return sra.getRequest();
	}
}
