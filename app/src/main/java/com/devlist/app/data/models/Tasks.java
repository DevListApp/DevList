package com.devlist.app.data.models;

import java.util.Date;

public class Tasks {
    String title;
    String note;
    Date dt_start_scheduled;
    Date dt_end_scheduled;
    Integer status;

    public Tasks(String title, String note, Date dt_start_scheduled, Date dt_end_scheduled, Integer status) {
        this.title = title;
        this.note = note;
        this.dt_start_scheduled = dt_start_scheduled;
        this.dt_end_scheduled = dt_end_scheduled;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDt_start_scheduled() {
        return dt_start_scheduled;
    }

    public void setDt_start_scheduled(Date dt_start_scheduled) {
        this.dt_start_scheduled = dt_start_scheduled;
    }

    public Date getDt_end_scheduled() {
        return dt_end_scheduled;
    }

    public void setDt_end_scheduled(Date dt_end_scheduled) {
        this.dt_end_scheduled = dt_end_scheduled;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
