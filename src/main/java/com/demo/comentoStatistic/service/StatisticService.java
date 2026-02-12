package com.demo.comentoStatistic.service;

import com.demo.comentoStatistic.dao.StatisticMapper;
import com.demo.comentoStatistic.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {

    @Autowired
    StatisticMapper statisticMapper;

    @Autowired
    HolidayService holidayService;
    
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
    
    // 공휴일 제외 기간별 접속자 수 조회
    public HolidayExcludedUserCountDto getHolidayExcludedUserCount(String startDate, String endDate) {
        // 기간 내 공휴일 목록 가져오기
        List<LocalDate> holidays = holidayService.getHolidaysInPeriod(startDate, endDate);
        
        // 공휴일 날짜를 YYYYMMDD 문자열 리스트로 변환
        List<String> excludedDates = holidays.stream()
                .map(date -> date.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .collect(Collectors.toList());
        
        // 공휴일 제외 접속자 수 조회
        Integer totalUserCount = statisticMapper.selectTotalAccessCountExcludingDates(startDate, endDate, excludedDates);
        
        // 결과 DTO 생성
        HolidayExcludedUserCountDto result = new HolidayExcludedUserCountDto();
        result.setStartDate(startDate);
        result.setEndDate(endDate);
        result.setExcludedHolidays(holidays.size());
        result.setTotalUserCount(totalUserCount != null ? totalUserCount : 0);
        
        return result;
    }
}
