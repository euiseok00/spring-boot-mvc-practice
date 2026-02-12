package com.demo.comentoStatistic.service;

import com.demo.comentoStatistic.dao.StatisticMapper;
import com.demo.comentoStatistic.dto.HolidayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {

    @Autowired
    StatisticMapper statisticMapper;

    private final WebClient webClient;
    private final String serviceKey;
    private final String apiEnabled;

    public HolidayService(WebClient.Builder webClientBuilder,
                         @Value("${holiday.api.base-url:https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService}") String baseUrl,
                         @Value("${holiday.api.key:}") String serviceKey,
                         @Value("${holiday.source:database}") String apiEnabled) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.serviceKey = serviceKey;
        this.apiEnabled = apiEnabled;
    }

    /**
     * 지정된 기간 내의 공휴일 목록을 가져옵니다.
     */
    public List<LocalDate> getHolidaysInPeriod(String startDate, String endDate) {
        if ("api".equals(apiEnabled) && !serviceKey.isEmpty()) {
            return getHolidaysFromApi(startDate, endDate);
        } else {
            return getHolidaysFromDatabase(startDate, endDate);
        }
    }

    /**
     * 데이터베이스에서 기간 내 공휴일 목록 가져오기
     */
    private List<LocalDate> getHolidaysFromDatabase(String startDate, String endDate) {
        // 데이터베이스에서 기간 내 공휴일 목록 가져오기
        List<String> holidayDates = statisticMapper.selectHolidaysInPeriod(startDate, endDate);

        // String을 LocalDate로 변환
        List<LocalDate> holidays = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (String dateStr : holidayDates) {
            try {
                LocalDate holiday = LocalDate.parse(dateStr, formatter);
                holidays.add(holiday);
            } catch (Exception e) {
                // 날짜 파싱 실패 시 스킵
            }
        }

        return holidays;
    }

    /**
     * 외부 API에서 기간 내 공휴일 목록 가져오기
     */
    private List<LocalDate> getHolidaysFromApi(String startDate, String endDate) {
        // API에서 가져오는 로직 (기존 코드 재사용)
        HolidayResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getHoliDeInfo")
                        .queryParam("solYear", startDate.substring(0, 4))
                        .queryParam("solMonth", startDate.substring(4, 6))
                        .queryParam("serviceKey", serviceKey)
                        .build())
                .retrieve()
                .bodyToMono(HolidayResponse.class)
                .block();

        return parseHolidaysFromResponse(response);
    }

    private List<LocalDate> parseHolidaysFromResponse(HolidayResponse response) {
        List<LocalDate> holidays = new ArrayList<>();

        if (response != null && response.getResponse() != null &&
            response.getResponse().getBody() != null &&
            response.getResponse().getBody().getItems() != null) {

            Object itemObj = response.getResponse().getBody().getItems().getItem();
            
            List<HolidayResponse.Item> items;
            if (itemObj instanceof List) {
                items = (List<HolidayResponse.Item>) itemObj;
            } else {
                items = new ArrayList<>();
                items.add((HolidayResponse.Item) itemObj);
            }

            for (HolidayResponse.Item item : items) {
                if ("Y".equals(item.getIsHoliday())) {
                    try {
                        String dateStr = String.valueOf(item.getLocdate());
                        LocalDate holidayDate = LocalDate.parse(dateStr,
                                DateTimeFormatter.ofPattern("yyyyMMdd"));
                        holidays.add(holidayDate);
                    } catch (Exception e) {
                        // 날짜 파싱 실패 시 스킵
                    }
                }
            }
        }

        return holidays;
    }
}
