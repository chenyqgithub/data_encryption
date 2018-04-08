package com.data.encryption.parameter;

/**
 * Created by admin on 2018/4/3.
 */

import com.alibaba.fastjson.JSONObject;
import com.data.encryption.entity.BaseEntity;

import java.lang.reflect.Field;
import java.util.Map;

public class RequestData<T> extends BaseEntity<T> {
    /**
     * 类型
     */
    private String os;
    /**
     * 版本
     */
    private String version;
    /**
     * 变化参数
     */
    private T variable;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 语言 zh=中文 en=英文
     */
    private String language = "zh";

    /**
     * 临时
     */
//    @NotEmpty(message="密码不能为空")
    private JSONObject jsonObject = new JSONObject();
    private Map<String,Object> variableStr;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getVariable() {
        return variable;
    }

    public void setVariable(T variable) {
        this.variable = variable;
    }

//    public User getVariable() {
//        return variable;
//    }
//
//    public void setVariable(User variable) {
//        this.variable = variable;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "os='" + os + '\'' +
                ", version='" + version + '\'' +
                ", variable='" + variable + '\'' +
                '}';
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Map getVariableStr() {
        return variableStr;
    }

    public void setVariableStr(Map variableStr) {
        this.variableStr = variableStr;
    }

    /**
     * 初始化泛型变量参数对应值
     * @param b
     */
    public ResponseData<T> structureVariable(T b){
        if(this.variableStr.keySet().size()!=0){
            Field[] declaredFields = b.getClass().getDeclaredFields();
            for(Field field:declaredFields){
                field.setAccessible(true);
                String name = field.getName();
                Object o = variableStr.get(name);
                try {
                    if(o!=null){
                        System.out.println(o.toString());
                        field.set(b,o);
                        variableStr.remove(name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.setVariable(b);
        }
        return new ResponseData<T>();
    }


}
