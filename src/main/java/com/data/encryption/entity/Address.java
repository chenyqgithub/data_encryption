package com.data.encryption.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by admin on 2018/4/2.
 */
@ApiModel("地址Entity")
public class Address {
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "历史Entity")
    private History history;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
