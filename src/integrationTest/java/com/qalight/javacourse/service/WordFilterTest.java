package com.qalight.javacourse.service;

import com.qalight.javacourse.core.WordCounter;
import com.qalight.javacourse.util.TextRefiner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Ignore
public class WordFilterTest {

    @Autowired
    private WordFilter wordFilter;

    @Test
    public void testGetWordsForFilter() {
        //given

        //when

        //then

    }

    @Configuration
    static class ContextConfiguration {
        @Bean
        public WordCounterService service() {
            return new WordCounterServiceImpl();
        }

        @Bean
        public TextTypeInquirer i() {
            return new TextTypeInquirer();
        }

        @Bean
        public DocumentConverter converter() {
            return new DocumentConverter();
        }

        @Bean
        public WordCounter counter() {
            return new WordCounter();
        }

        @Bean
        ResultPresentationService presentation() {
            return new ResultPresentationService();
        }

        @Bean
        public TextRefiner getRefiner() {
            return new TextRefiner();
        }

        @Bean
        public WordFilter filter() {
            return new WordFilter();
        }
    }
}
