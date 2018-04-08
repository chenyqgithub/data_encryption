package com.data.encryption.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.data.encryption.annotations.SerializedField;
import com.data.encryption.config.CommonInterceptor;
import com.data.encryption.util.DesUtil;
import com.data.encryption.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by admin on 2018/4/2.
 */
@ControllerAdvice(basePackages = "com.data.encryption.controller")
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    private final Logger log = LoggerFactory.getLogger(MyResponseBodyAdvice.class);
    //包含项
    private String[] includes = {};
    //排除项
    private String[] excludes = {};
    //是否加密
    private boolean encode = true;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //这里可以根据自己的需求
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //重新初始化为默认值
        includes = new String[]{};
        excludes = new String[]{};
        encode = false;

        //判断返回的对象是单个对象，还是list，活着是map
        if (o == null) {
            return null;
        }
        if (methodParameter.getMethod().isAnnotationPresent(SerializedField.class)) {
            //获取注解配置的包含和去除字段
            SerializedField serializedField = methodParameter.getMethodAnnotation(SerializedField.class);
            includes = serializedField.includes();
            excludes = serializedField.excludes();
            //是否加密
            encode = serializedField.encode();
        }

        Object retObj = null;
        if (o instanceof List) {
            //List
            List list = (List) o;
            try {
                retObj = handleList(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Single Object
            try {
                retObj = handleSingleObject(o, new HashMap<String, Object>());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       log.info("Output Parameter ："+ JSON.toJSONString(retObj));
        return retObj;
    }

    /**
     * 处理返回值是多个enity对象或一个Map对象 Map对象注解过滤字段无效
     * @param o
     * @param tempMap
     * @return
     * @throws Exception
     */
    private Object handleSingleObject(Object o, Map<String, Object> tempMap) throws Exception {
        //Map类型特殊处理 当返回参数为Map类型须要用到递归迭代器
        if (o instanceof Map) {
            if (encode) {
                String s = JSONObject.toJSONString(o);
                String decrypt = DesUtil.encrypt(s, Helper.key);
                return decrypt;
            } else {
                return o;
            }
        } else {
            Map<String, Object> map = tempMap;
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                //如果未配置表示全部的都返回
                if (includes.length == 0 && excludes.length == 0) {
                    propertiesOfRecursion(o, field, map);
                } else if (includes.length > 0) {
                    //有限考虑包含字段
                    if (Helper.isStringInArray(field.getName(), includes)) {
                        propertiesOfRecursion(o, field, map);
                    }
                } else {
                    //去除字段
                    if (excludes.length > 0) {
                        if (!Helper.isStringInArray(field.getName(), excludes)) {
                            propertiesOfRecursion(o, field, map);
                        }
                    }
                }

            }
            String s = JSONObject.toJSONString(map);
            String decrypt = DesUtil.encrypt(s, Helper.key).replaceAll("\\r","").replaceAll("\\n","");
//            System.out.println(decrypt);
            if (encode) {
                return decrypt;
            } else {
                return map;
            }
        }


    }

    /**
     * 处理返回值是列表
     *
     * @param list
     * @return
     */
    private List handleList(List list) throws Exception {
        List retList = new ArrayList();
        for (Object o : list) {
            Map map = (Map) handleSingleObject(o, new HashMap<String, Object>());
            retList.add(map);
        }
        return retList;
    }

    /**
     * 获取加密后的新值
     *
     * @param o
     * @param field
     * @return
     */
    private String getNewVal(Object o, Field field) {
        String newVal = "";
        try {
            field.setAccessible(true);
            Object val = field.get(o);

            if (val != null) {
                if (encode) {
//                    newVal = Helper.encode(val.toString());
                    newVal = val.toString();
                } else {
                    newVal = val.toString();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return newVal;
    }

    private static boolean isBaseDataType(Class clazz) throws Exception {
        return
                (
                        clazz.equals(String.class) ||
                                clazz.equals(Integer.class) ||
                                clazz.equals(Byte.class) ||
                                clazz.equals(Long.class) ||
                                clazz.equals(Double.class) ||
                                clazz.equals(Float.class) ||
                                clazz.equals(Character.class) ||
                                clazz.equals(Short.class) ||
                                clazz.equals(BigDecimal.class) ||
                                clazz.equals(BigInteger.class) ||
                                clazz.equals(Boolean.class) ||
                                clazz.equals(Date.class) ||
//                                clazz.equals(DateTime.class) ||
                                clazz.isPrimitive()
                );
    }

    /**
     * 属性递归
     *
     * @param o
     * @param field
     * @param map
     */
    private void propertiesOfRecursion(Object o, Field field, Map<String, Object> map) {
        String newVal = getNewVal(o, field);
        Class<?> aClass = null;
        try {
            aClass = field.get(o).getClass();
            boolean baseDataType = false;
            baseDataType = this.isBaseDataType(aClass);
            if (baseDataType) {
                map.put(field.getName(), newVal);
            } else {
                Map<String, Object> childMap = new HashMap<>();
                Object o1 = field.get(o);
                if(o1 instanceof List){//list对象支持
                    List list=(List)o1;
                    List<Map<String, Object>> tempList=new ArrayList<>();
                    for(Object o2:list){
                        Map<String, Object> listChildMap = new HashMap<>();
                        handleSingleObject(o2, listChildMap);
                        tempList.add(listChildMap);
                    }
                    map.put(field.getName(), tempList);
                }else {//普通对象支持
                    handleSingleObject(field.get(o), childMap);
                    map.put(field.getName(), childMap);
                }



            }
        } catch (Exception e) {
//            e.printStackTrace();
        }


    }

}
