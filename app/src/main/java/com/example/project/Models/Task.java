package com.example.project.Models;

import java.sql.Date;

public class Task {
    private int Id;
    private String Desc;
    private Date date;

    public Task(int id, String desc, Date date) {
        Id = id;
        Desc = desc;
        this.date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
