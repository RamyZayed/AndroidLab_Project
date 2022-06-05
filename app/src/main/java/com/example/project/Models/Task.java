package com.example.project.Models;

import java.sql.Date;

public class Task {
    private int Id;
    private String Desc;
    private int Year;
    private int Month;
    private int Day;
    private String email;
    private int complete;
    public Task(){

    }
    public Task( String desc, int year, int month, int day,String email,int complete) {
        Desc = desc;
        Year = year;
        Month = month;
        Day = day;
        this.email = email;
        this.complete = complete;
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

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + Id +
                ", Desc='" + Desc + '\'' +
                ", Year=" + Year +
                ", Month=" + Month +
                ", Day=" + Day +
                ", email='" + email + '\'' +
                ", complete=" + complete +
                '}';
    }
}
