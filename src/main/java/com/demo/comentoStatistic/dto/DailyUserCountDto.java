package com.demo.comentoStatistic.dto;

public class DailyUserCountDto {
    private String date;
    private Integer userCount;
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public Integer getUserCount() {
        return userCount;
    }
    
    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
}
