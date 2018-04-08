package com.data.encryption.controller;

import com.alibaba.fastjson.JSON;
import com.data.encryption.annotations.SerializedField;
import com.data.encryption.annotations.SwaggerSupport;
import com.data.encryption.controller.base.BaseController;
import com.data.encryption.entity.User;
import com.data.encryption.parameter.PageData;
import com.data.encryption.parameter.RequestData;
import com.data.encryption.parameter.ResponseData;
import com.data.encryption.service.UserService;
import com.data.encryption.util.HttpParameterChangeBody;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by admin on 2018/4/2.
 */
@RestController
@RequestMapping("/user")
@Api(value = "TasksController", description = "获取APP任务相关信息")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("/findUserById")
    @SerializedField(encode = false, excludes = {"id"})
    @SwaggerSupport(support = true)
    @ApiOperation(value = "findUserById", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "variable.id", value = "用户id", paramType = "query"),
    })

    public ResponseData<User> findUserById( @RequestBody(required = false)  RequestData<User> req1,  RequestData<User> req2, HttpServletRequest request) throws Exception {
        ResponseData<User> responseData = null;
        try {
            RequestData<User> userRequestData = new HttpParameterChangeBody<RequestData<User>>().changeObject(req1, req2, request);
            responseData = userRequestData.structureVariable(new User());
            User userById = userService.getUserById(userRequestData.getVariable().getId());
            responseData.setDataCollection(userById);
        } catch (Exception e) {
            throw new NullPointerException("用户id为空");
        }
        return responseData;

    }

    @RequestMapping("/all")
    @SerializedField(encode = false, excludes = {"id", "email"})
    @ApiOperation(value = "findAllUser", httpMethod = "POST")
    public List<User> findAllUser(RequestData<User> requestData1) {
        return userService.getAllUser();
    }

    @RequestMapping("/map")
    @ApiOperation(value = "findMap", httpMethod = "POST")
    @SerializedField(encode = false)
    public Map findMap(@ModelAttribute RequestData<User> requestData1) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        return map;
    }

    @RequestMapping("/findTest")
    @SerializedField(encode = false, excludes = {"history"})
    @ApiOperation(value = "findTest", httpMethod = "POST")
    public ResponseData<PageData<User>> findTest(@RequestBody(required = false) RequestData<PageData<User>> requestData1, RequestData<PageData<User>> requestData2, HttpServletRequest request) {
        RequestData<PageData<User>> pageDataRequestData = new HttpParameterChangeBody<RequestData<PageData<User>>>().changeObject(requestData1, requestData2, request);
        ResponseData<PageData<User>> pageDataResponseData = pageDataRequestData.structureVariable(new PageData<User>());
        PageData<User> pageData=new PageData<>();
        pageData.setCountNum(10);
        pageData.setPageNo("1");
        pageData.setPageSize("10");
        pageData.setPageConent(userService.getAllUser());
        pageDataResponseData.setDataCollection(pageData);
        return pageDataResponseData;
    }
}
