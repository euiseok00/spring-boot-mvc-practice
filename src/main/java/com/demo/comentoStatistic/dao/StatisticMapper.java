package com.demo.comentoStatistic.dao;

import com.demo.comentoStatistic.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatisticMapper {
    // 일자별 접속자 수 조회
    DailyUserCountDto selectDailyUserCount(String date);
    
    // 월별 접속자 수 조회
    MonthlyUserCountDto selectMonthlyUserCount(String yearMonth);
    
    // 평균 하루 접속수 조회 (기간 내 총 접속수)
    Integer selectTotalAccessCount(String startDate, String endDate);
    
    // 부서별 월별 접속수 조회
    DeptMonthlyUserCountDto selectDeptMonthlyUserCount(String yearMonth, String department);
    
    // 공휴일 제외 기간별 접속수 조회
    Integer selectTotalAccessCountExcludingDates(@Param("startDate") String startDate, 
                                                @Param("endDate") String endDate, 
                                                @Param("excludedDates") java.util.List<String> excludedDates);
    
    // 기간 내 공휴일 목록 조회
    java.util.List<String> selectHolidaysInPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
