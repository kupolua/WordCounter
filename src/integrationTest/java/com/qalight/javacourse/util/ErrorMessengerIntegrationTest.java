package com.qalight.javacourse.util;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class ErrorMessengerIntegrationTest {

    @Test
    public void testGetErrorMsg_unsupportedLocale_null() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_CONNECT;

        //when
        ErrorMessenger.setLocale(null);
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Cannot connect to the source:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_unsupportedLocale_fr() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_CONNECT;

        //when
        ErrorMessenger.setLocale(new Locale("fr"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Cannot connect to the source:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_en_connect() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_CONNECT;

        //when
        ErrorMessenger.setLocale(new Locale("en"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Cannot connect to the source:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_ru_connect() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_CONNECT;

        //when
        ErrorMessenger.setLocale(new Locale("ru"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Не удается подключится к удаленному серверу по ссылке:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_uk_connect() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_CONNECT;

        //when
        ErrorMessenger.setLocale(new Locale("uk"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Неможливо з'єднатись з віддаленим сервером за посиланням:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_en_count() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_COUNT_TEXT;

        //when
        ErrorMessenger.setLocale(new Locale("en"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "System cannot count entered text. " +
                "Did you forget to add 'http://' to the link or entered not readable text?";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_ru_count() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_COUNT_TEXT;

        //when
        ErrorMessenger.setLocale(new Locale("ru"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Система не может обработать введенный текст. Пожалуйста, проверьте, не забыли ли " +
                "Вы добавить 'http://' префикс к ссылке или ввели нечитаемый текст.";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_uk_count() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.CANNOT_COUNT_TEXT;

        //when
        ErrorMessenger.setLocale(new Locale("uk"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Система не взмозі обробити введений текст. Будь ласка, перевірте, чи ви не забули додати " +
                "'http://' префікс до посилання або ввели нечитабельний текст.";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_en_empty() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.REQUEST_IS_EMPTY_OR_NULL;

        //when
        ErrorMessenger.setLocale(new Locale("en"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Request is empty.";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_ru_empty() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.REQUEST_IS_EMPTY_OR_NULL;

        //when
        ErrorMessenger.setLocale(new Locale("ru"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Пустой запрос.";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_uk_empty() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.REQUEST_IS_EMPTY_OR_NULL;

        //when
        ErrorMessenger.setLocale(new Locale("uk"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Порожній запит.";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_en_non_readable() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.SOURCE_IS_EMPTY_OR_NON_READABLE;

        //when
        ErrorMessenger.setLocale(new Locale("en"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "System cannot count text in the source as it is empty or " +
                "contains non-readable content or symbols:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_ru_non_readable() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.SOURCE_IS_EMPTY_OR_NON_READABLE;

        //when
        ErrorMessenger.setLocale(new Locale("ru"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Система не может посчитать слова в источнике по ссылке, " +
                "так как в нем содержится нечитаемый текст или специальные символы:";

        assertEquals(expectedMsg, actualMsg);
    }

    @Test
    public void testGetErrorMsg_supportedLocale_uk_non_readable() throws Exception {
        //given
        ErrorCode code = ErrorCodeImpl.SOURCE_IS_EMPTY_OR_NON_READABLE;

        //when
        ErrorMessenger.setLocale(new Locale("uk"));
        String actualMsg = ErrorMessenger.getErrorMsg(code);

        //then
        final String expectedMsg = "Система не взмозі порахувати слова в джерелі за посиланням, адже він містить " +
                "нечитабельний текст або спеціальні символи:";

        assertEquals(expectedMsg, actualMsg);
    }
}