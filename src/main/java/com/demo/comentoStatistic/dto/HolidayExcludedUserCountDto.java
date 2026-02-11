package com.demo.comentoStatistic.dto;

// 공휴일 제외 접속자 수 결과를 담는 dto
public class HolidayExcludedUserCountDto {
    private String startDate;
    private String endDate;
    private Integer excludedHolidays;
    private Integer totalUserCount;

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

    public Integer getExcludedHolidays() {
        return excludedHolidays;
    }

    public void setExcludedHolidays(Integer excludedHolidays) {
        this.excludedHolidays = excludedHolidays;
    }

    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }
}
