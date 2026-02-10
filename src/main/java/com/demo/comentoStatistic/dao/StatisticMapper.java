package com.demo.comentoStatistic.dao;

import com.demo.comentoStatistic.dto.*;
import org.apache.ibatis.annotations.Mapper;

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
}
