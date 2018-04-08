package com.data.encryption.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by admin on 2018/4/2.
 */
@ApiModel("历史Entity")
public class History {
    @ApiModelProperty(value = "标题")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
