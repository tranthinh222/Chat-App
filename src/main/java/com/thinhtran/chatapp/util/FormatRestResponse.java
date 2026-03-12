package com.thinhtran.chatapp.util;

import com.thinhtran.chatapp.domain.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType,
                     Class converterType){
        return true;
    }

    public Object beforeBodyWrite(Object body,
                           MethodParameter returnType,
                           MediaType selectedContentType,
                           Class selectedConverterType,
                           ServerHttpRequest request,
                           ServerHttpResponse response){
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        int status = servletResponse.getStatus();
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatusCode(status);

        // RestResponse including: statusCode, data, message, error
        if (body instanceof String){
            return body;
        }
        //error case
        if (status >= 400){
            return body;
        }
        else{
            restResponse.setData(body);
            restResponse.setMessage("Call API success");
        }

        return restResponse;
    }

}
