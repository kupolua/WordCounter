package com.qalight.javacourse.service;

import com.qalight.javacourse.util.PropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PropertiesReaderImpl implements PropertiesReader {

    @Value("${botton.Filtered_uk}") private String bottonFiltered_uk;
    @Value("${botton.UnFiltered_uk}") private String bottonUnFiltered_uk;
    @Value("${botton.ShowFilter_uk}") private String bottonShowFilter_uk;

    @Value("${botton.Filtered_en}") private String bottonFiltered_en;
    @Value("${botton.UnFiltered_en}") private String bottonUnFiltered_en;
    @Value("${botton.ShowFilter_en}") private String bottonShowFilter_en;

    @Value("${botton.Filtered_ru}") private String bottonFiltered_ru;
    @Value("${botton.UnFiltered_ru}") private String bottonUnFiltered_ru;
    @Value("${botton.ShowFilter_ru}") private String bottonShowFilter_ru;

    @Autowired
    public Map<String, String> readProperties() {
        Map<String, String> webFormProperties = new HashMap<>();
        webFormProperties.put("botton.Filtered_uk", bottonFiltered_uk);
        webFormProperties.put("botton.UnFiltered_uk", bottonUnFiltered_uk);
        webFormProperties.put("botton.ShowFilter_uk", bottonShowFilter_uk);
        webFormProperties.put("botton.Filtered_en", bottonFiltered_en);
        webFormProperties.put("botton.UnFiltered_en", bottonUnFiltered_en);
        webFormProperties.put("botton.ShowFilter_en", bottonShowFilter_en);
        webFormProperties.put("botton.Filtered_ru", bottonFiltered_ru);
        webFormProperties.put("botton.UnFiltered_ru", bottonUnFiltered_ru);
        webFormProperties.put("botton.ShowFilter_ru", bottonShowFilter_ru);

        return webFormProperties;
    }
}
