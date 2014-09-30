package com.qalight.javacourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PropertiesReaderImpl implements PropertiesReader {

    @Value("${button.Filtered_uk}") private String buttonFiltered_uk;
    @Value("${button.UnFiltered_uk}") private String buttonUnFiltered_uk;
    @Value("${button.ShowFilter_uk}") private String buttonShowFilter_uk;

    @Value("${button.Filtered_en}") private String buttonFiltered_en;
    @Value("${button.UnFiltered_en}") private String buttonUnFiltered_en;
    @Value("${button.ShowFilter_en}") private String buttonShowFilter_en;

    @Value("${button.Filtered_ru}") private String buttonFiltered_ru;
    @Value("${button.UnFiltered_ru}") private String buttonUnFiltered_ru;
    @Value("${button.ShowFilter_ru}") private String buttonShowFilter_ru;

    @Autowired
    public Map<String, String> readProperties() {
        Map<String, String> webFormProperties = new HashMap<>();
        webFormProperties.put("button.Filtered_uk", buttonFiltered_uk);
        webFormProperties.put("button.UnFiltered_uk", buttonUnFiltered_uk);
        webFormProperties.put("button.ShowFilter_uk", buttonShowFilter_uk);
        webFormProperties.put("button.Filtered_en", buttonFiltered_en);
        webFormProperties.put("button.UnFiltered_en", buttonUnFiltered_en);
        webFormProperties.put("button.ShowFilter_en", buttonShowFilter_en);
        webFormProperties.put("button.Filtered_ru", buttonFiltered_ru);
        webFormProperties.put("button.UnFiltered_ru", buttonUnFiltered_ru);
        webFormProperties.put("button.ShowFilter_ru", buttonShowFilter_ru);

        return webFormProperties;
    }
}
