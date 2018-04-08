package com.data.encryption.advice;

import com.alibaba.fastjson.JSON;
import com.data.encryption.util.DesUtil;
import com.data.encryption.util.Helper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;

/**
 * Created by admin on 2018/4/2.
 */
//@Order(2)
@ControllerAdvice(basePackages = "com.data.encryption.controller")
public class MyRequestBodyAdvice implements RequestBodyAdvice {
    private final Logger log = LoggerFactory.getLogger(MyRequestBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {
            return new MyHttpInputMessage(httpInputMessage);
//           return httpInputMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return httpInputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {

        return o;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }


    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) {
            try {
                this.headers = inputMessage.getHeaders();
                String s = IOUtils.toString(inputMessage.getBody(), "UTF-8");
                //解密
                if (s.equals("{}")) s = "";
                System.out.println("解密" + s);
                String decrypt = DesUtil.decrypt(s, Helper.key);
                log.info("Body Input Parameter ：" + decrypt);
//            this.body = IOUtils.toInputStream(AESOperator.getInstance().decrypt(s), "UTF-8");
                this.body = IOUtils.toInputStream(decrypt, "UTF-8");
            } catch (Exception e) {
            }
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
