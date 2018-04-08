package com.data.encryption.controller.base;

import com.alibaba.fastjson.JSON;
import com.data.encryption.annotations.SerializedField;
import com.data.encryption.parameter.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    /**
     * 使用@ExceptionHandler注解，继承此类的Controller发生异常时会自动执行该方法
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    @SerializedField(encode = false)
    public ResponseData exception(HttpServletRequest request, Exception e) {
        //对异常进行判断做相应的处理
        ResponseData responseData = new ResponseData();
        responseData.setResultCode("202");
        responseData.setResultMessage(e.getMessage());
        return responseData;
    }
}
