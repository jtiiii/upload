package com.funeral.upload.entity;

import java.util.Date;

/**
 * 时间段period
 *
 * @author FuneralObjects
 * CreateTime 2018/6/5 2:34 PM
 */
public class TimePeriod {
    private Date start;
    private Date end;

    public TimePeriod() {
    }

    public TimePeriod(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
