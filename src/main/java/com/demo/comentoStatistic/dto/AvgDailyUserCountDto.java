package com.demo.comentoStatistic.dto;

public class AvgDailyUserCountDto {
    private String startDate;
    private String endDate;
    private Integer totalDays;
    private Integer totalAccessCount;
    private Double avgDailyAccess;
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public Integer getTotalDays() {
        return totalDays;
    }
    
    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }
    
    public Integer getTotalAccessCount() {
        return totalAccessCount;
    }
    
    public void setTotalAccessCount(Integer totalAccessCount) {
        this.totalAccessCount = totalAccessCount;
    }
    
    public Double getAvgDailyAccess() {
        return avgDailyAccess;
    }
    
    public void setAvgDailyAccess(Double avgDailyAccess) {
        this.avgDailyAccess = avgDailyAccess;
    }
}
