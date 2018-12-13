package com.funeral.upload.entity;

import java.math.BigDecimal;

/**
 * 上传进度实体类
 * @author FuneralObjects
 * CreateTime 2018/12/13 10:00 PM
 */
public class UploadState {
    private BigDecimal percent = new BigDecimal(0);
    private Boolean complete = false;

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
