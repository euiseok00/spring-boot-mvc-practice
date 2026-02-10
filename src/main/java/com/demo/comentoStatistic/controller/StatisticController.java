package com.demo.comentoStatistic.controller;

import com.demo.comentoStatistic.dto.*;
import com.demo.comentoStatistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatisticController {
    @Autowired
    StatisticService statisticService;

    // 월별 접속자 수 조회 엔드포인트
    @RequestMapping(value="/stats/users/{year}/{month}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getMonthlyUserCount(@PathVariable("year") String year, @PathVariable("month") String month){
        
        // 년도 검증 (4자리 숫자)
        if (!year.matches("\\d{4}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"년도는 4자리 숫자여야 합니다 (예: 2024)\"}");
        }
        
        int yearNum = Integer.parseInt(year);
        if (yearNum < 1950 || yearNum > 2026) {
            return ResponseEntity.badRequest().body("{\"error\": \"유효하지 않은 년도입니다 (1950-2026)\"}");
        }
        
        // 월 검증
        if (!month.matches("\\d{1,2}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"월은 1-2자리 숫자여야 합니다 (예: 1, 01, 12)\"}");
        }
        
        int monthNum = Integer.parseInt(month);
        if (monthNum < 1 || monthNum > 12) {
            return ResponseEntity.badRequest().body("{\"error\": \"월은 1-12 사이여야 합니다\"}");
        }

        try {
            MonthlyUserCountDto result = statisticService.getMonthlyUserCount(year, month);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"서버 오류가 발생했습니다: " + e.getMessage() + "\"}");
        }
    }
    
    // 일자별 접속자 수 조회 엔드포인트
    @RequestMapping(value="/stats/users/{year}/{month}/{day}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getDailyUserCount(@PathVariable("year") String year, 
                                              @PathVariable("month") String month, 
                                              @PathVariable("day") String day){
        
        // 년도 검증 (4자리 숫자)
        if (!year.matches("\\d{4}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"년도는 4자리 숫자여야 합니다 (예: 2024)\"}");
        }
        
        int yearNum = Integer.parseInt(year);
        if (yearNum < 1950 || yearNum > 2026) {
            return ResponseEntity.badRequest().body("{\"error\": \"유효하지 않은 년도입니다 (1950-2026)\"}");
        }
        
        // 월 검증
        if (!month.matches("\\d{1,2}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"월은 1-2자리 숫자여야 합니다 (예: 1, 01, 12)\"}");
        }
        
        int monthNum = Integer.parseInt(month);
        if (monthNum < 1 || monthNum > 12) {
            return ResponseEntity.badRequest().body("{\"error\": \"월은 1-12 사이여야 합니다\"}");
        }
        
        // 일 검증
        if (!day.matches("\\d{1,2}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"일은 1-2자리 숫자여야 합니다\"}");
        }
        
        int dayNum = Integer.parseInt(day);
        if (dayNum < 1 || dayNum > 31) {
            return ResponseEntity.badRequest().body("{\"error\": \"일은 1-31 사이여야 합니다\"}");
        }

        try {
            DailyUserCountDto result = statisticService.getDailyUserCount(year, month, day);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"서버 오류가 발생했습니다: " + e.getMessage() + "\"}");
        }
    }
    
    // 평균 하루 접속수 조회 엔드포인트
    @RequestMapping(value="/stats/users/avg-daily/{start-date}/{end-date}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAvgDailyUserCount(@PathVariable("start-date") String startDate, 
                                                 @PathVariable("end-date") String endDate){
        
        // 시작일 검증 (8자리 숫자)
        if (!startDate.matches("\\d{8}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"시작일은 8자리 숫자여야 합니다 (예: 20240101)\"}");
        }
        
        // 종료일 검증 (8자리 숫자)
        if (!endDate.matches("\\d{8}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"종료일은 8자리 숫자여야 합니다 (예: 20240131)\"}");
        }
        
        // 날짜 유효성 검증
        try {
            int startYear = Integer.parseInt(startDate.substring(0, 4));
            int endYear = Integer.parseInt(endDate.substring(0, 4));
            
            if (startYear < 1950 || startYear > 2026 || endYear < 1950 || endYear > 2026) {
                return ResponseEntity.badRequest().body("{\"error\": \"유효하지 않은 년도입니다 (1950-2026)\"}");
            }
            
            if (startDate.compareTo(endDate) > 0) {
                return ResponseEntity.badRequest().body("{\"error\": \"시작일이 종료일보다 늦을 수 없습니다\"}");
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"날짜 형식이 올바르지 않습니다\"}");
        }

        try {
            AvgDailyUserCountDto result = statisticService.getAvgDailyUserCount(startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"서버 오류가 발생했습니다: " + e.getMessage() + "\"}");
        }
    }
    
    // 부서별 월별 접속수 조회 엔드포인트
    @RequestMapping(value="/stats/users/dept-monthly/{year}/{month}/{department}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getDeptMonthlyUserCount(@PathVariable("year") String year, 
                                               @PathVariable("month") String month, 
                                               @PathVariable("department") String department){
        
        // 년도 검증 (4자리 숫자)
        if (!year.matches("\\d{4}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"년도는 4자리 숫자여야 합니다 (예: 2024)\"}");
        }
        
        int yearNum = Integer.parseInt(year);
        if (yearNum < 1950 || yearNum > 2026) {
            return ResponseEntity.badRequest().body("{\"error\": \"유효하지 않은 년도입니다 (1950-2026)\"}");
        }
        
        // 월 검증
        if (!month.matches("\\d{1,2}")) {
            return ResponseEntity.badRequest().body("{\"error\": \"월은 1-2자리 숫자여야 합니다 (예: 1, 01, 12)\"}");
        }
        
        int monthNum = Integer.parseInt(month);
        if (monthNum < 1 || monthNum > 12) {
            return ResponseEntity.badRequest().body("{\"error\": \"월은 1-12 사이여야 합니다\"}");
        }
        
        // 부서 검증
        String[] validDepts = {"dev", "plan", "law", "marketing", "design"};
        boolean isValidDept = false;
        for (String validDept : validDepts) {
            if (validDept.equals(department)) {
                isValidDept = true;
                break;
            }
        }
        
        if (!isValidDept) {
            return ResponseEntity.badRequest().body("{\"error\": \"유효하지 않은 부서입니다. (dev, plan, law, marketing, design)\"}");
        }

        try {
            DeptMonthlyUserCountDto result = statisticService.getDeptMonthlyUserCount(year, month, department);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"서버 오류가 발생했습니다: " + e.getMessage() + "\"}");
        }
    }

}
