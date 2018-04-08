package com.data.encryption.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/4/3.
 */
public class HttpParameterChangeBody<T> {
    /**
     * @param source    body中含有的参数
     * @param converted 普通入参参数
     * @return
     */
    public T changeObject(T source, T converted, HttpServletRequest request) {
        if (source == null && converted != null) {
            Map<String, Object> parmMap = new HashMap<String, Object>();
            Enumeration<String> a = request.getParameterNames();
            String parm = null;
            String val = "";
            while (a.hasMoreElements()) {
                //参数名
                parm = a.nextElement();
                //值
                val = request.getParameter(parm);

                if (parm.indexOf(".") != -1) {
                   parmMap.put(parm.substring(parm.indexOf(".")+1,parm.length()), val);
                }
            }
            Field[] fields = converted.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("variableStr")) {
                    try {
                        field.setAccessible(true);
                        field.set(converted,parmMap);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return (T) converted;
        }
        return (T) source;
    }
}
