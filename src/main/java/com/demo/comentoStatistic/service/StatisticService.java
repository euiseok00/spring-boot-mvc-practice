package com.demo.comentoStatistic.service;

import com.demo.comentoStatistic.dao.StatisticMapper;
import com.demo.comentoStatistic.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class StatisticService {

    @Autowired
    StatisticMapper statisticMapper;
    
    // 일자별 접속자 수 조회
    public DailyUserCountDto getDailyUserCount(String year, String month, String day) {
        // 날짜 포맷을 YYYYMMDD로 변환
        String date = year + String.format("%02d", Integer.parseInt(month)) + String.format("%02d", Integer.parseInt(day));
        return statisticMapper.selectDailyUserCount(date);
    }
    
    // 월별 접속자 수 조회
    public MonthlyUserCountDto getMonthlyUserCount(String year, String month) {
        // 날짜 포맷을 YYYYMM으로 변환
        String yearMonth = year + String.format("%02d", Integer.parseInt(month));
        return statisticMapper.selectMonthlyUserCount(yearMonth);
    }
    
    // 평균 하루 접속수 조회
    public AvgDailyUserCountDto getAvgDailyUserCount(String startDate, String endDate) {
        // 날짜 형식 변환 (YYYYMMDD -> YYYY-MM-DD)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        LocalDate start = LocalDate.parse(startDate, inputFormatter);
        LocalDate end = LocalDate.parse(endDate, inputFormatter);
        
        // 총 일수 계산
        long totalDays = ChronoUnit.DAYS.between(start, end) + 1;
        
        // 기간 내 총 접속수 조회
        Integer totalAccessCount = statisticMapper.selectTotalAccessCount(startDate, endDate);
        
        // 평균 하루 접속수 계산
        double avgDailyAccess = totalDays > 0 ? (double) totalAccessCount / totalDays : 0.0;
        
        // 결과 DTO 생성
        AvgDailyUserCountDto result = new AvgDailyUserCountDto();
        result.setStartDate(start.format(outputFormatter));
        result.setEndDate(end.format(outputFormatter));
        result.setTotalDays((int) totalDays);
        result.setTotalAccessCount(totalAccessCount);
        result.setAvgDailyAccess(Math.round(avgDailyAccess * 100.0) / 100.0); // 소수점 2자리까지 반올림
        
        return result;
    }
    
    // 부서별 월별 접속수 조회
    public DeptMonthlyUserCountDto getDeptMonthlyUserCount(String year, String month, String department) {
        // 날짜 포맷을 YYYYMM으로 변환
        String yearMonth = year + String.format("%02d", Integer.parseInt(month));
        return statisticMapper.selectDeptMonthlyUserCount(yearMonth, department);
    }
}
