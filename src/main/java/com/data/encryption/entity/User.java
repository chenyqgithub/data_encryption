package com.data.encryption.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * Created by admin on 2018/4/2.
 */
@ApiModel("用户Entity")
public class User extends BaseEntity<User> {
    @NotEmpty(message = "id不能为空")
//    @Length(min=6,message="密码长度不能小于6位")
    @ApiModelProperty(value = "用户id")

    private String id;
    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "email地址")
    private String email;
    private String password;
    //    @JSONField(serialize = false)
    @ApiModelProperty(value = "地址Entity")
    private Address address;
//    private Map<String,Object> map;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //    @JSONField(serialize = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    //    public Map<String, Object> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, Object> map) {
//        this.map = map;
//    }

}
