package com.ypb.spring.annotation.advice;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
/**
 * @ClassName: JsonpAdvice
 * @Description: spring使用jsonp解决跨域， spring4中增加了对jsonp的原生支持，只需要ControllerAdvice就可以开启
 * @author yangpengbing
 * @date 2019-04-23-9:52
 * @version V1.0.0
 *
 */
@RestControllerAdvice("com.ypb.spring.annotation.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

	private static final String PARAMSTER_CALLBACK = "callback";

	private final String[] jsonpQueryParamNames;

	public JsonpAdvice() {
		super(PARAMSTER_CALLBACK, "jsonp");
		this.jsonpQueryParamNames = new String[]{"callback"};
	}

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest req = (HttpServletRequest) request;
		if (ObjectUtils.isEmpty(req.getParameter(PARAMSTER_CALLBACK))) {
			return;
		}

		for (String paramName : jsonpQueryParamNames) {
			String value = req.getParameter(paramName);
			if (StringUtils.isEmpty(value)) {
				continue;
			}

			MediaType type = getContentType(contentType, request, response);
			response.getHeaders().setContentType(type);
			bodyContainer.setJsonpFunction(value);
		}
	}
}
