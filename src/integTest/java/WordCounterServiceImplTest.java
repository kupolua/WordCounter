import com.qalight.javacourse.service.WordCounterServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class WordCounterServiceImplTest {

    @Test
    public void testGetWordCounterResult_okUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPage.html";
        final String sortingParam = "KEY_ASCENDING";
        final String dataTypeResponse = "JSON";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":45,\"key\":\"-\",\"value\":2},{\"hash\":97,\"key\":\"a\",\"value\":2},{\"hash\":96726,\"key\":\"and\",\"value\":2},{\"hash\":93030097,\"key\":\"apple\",\"value\":1},{\"hash\":3122,\"key\":\"as\",\"value\":1,\"next\":{\"hash\":108521650,\"key\":\"risen\",\"value\":1}},{\"hash\":3019778,\"key\":\"been\",\"value\":1},{\"hash\":-1072081724,\"key\":\"beginning\",\"value\":1},{\"hash\":3029703,\"key\":\"book\",\"value\":1},{\"hash\":1564206610,\"key\":\"character\",\"value\":1},{\"hash\":1245438033,\"key\":\"characters\",\"value\":1,\"next\":{\"hash\":93030097,\"key\":\"apple\",\"value\":1}},{\"hash\":707888742,\"key\":\"computing-industry\",\"value\":1,\"next\":{\"hash\":3357542,\"key\":\"more\",\"value\":1}},{\"hash\":1032937741,\"key\":\"conjunctionwith\",\"value\":1},{\"hash\":-332950906,\"key\":\"consortium\",\"value\":1},{\"hash\":-351764541,\"key\":\"covering\",\"value\":1},{\"hash\":-80733044,\"key\":\"developed\",\"value\":1},{\"hash\":-1603782120,\"key\":\"english\",\"value\":1},{\"hash\":3149044,\"key\":\"form\",\"value\":1},{\"hash\":103067,\"key\":\"has\",\"value\":2},{\"hash\":-1443541794,\"key\":\"high-light\",\"value\":1},{\"hash\":3365,\"key\":\"in\",\"value\":2},{\"hash\":104415,\"key\":\"inc\",\"value\":1},{\"hash\":3370,\"key\":\"is\",\"value\":1},{\"hash\":-1109904801,\"key\":\"latest\",\"value\":1,\"next\":{\"hash\":104415,\"key\":\"inc\",\"value\":1}},{\"hash\":3357542,\"key\":\"more\",\"value\":1},{\"hash\":3373752,\"key\":\"name\",\"value\":2},{\"hash\":3543,\"key\":\"of\",\"value\":2},{\"hash\":3433147,\"key\":\"page\",\"value\":2},{\"hash\":1447425963,\"key\":\"published\",\"value\":1},{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}},{\"hash\":108521650,\"key\":\"risen\",\"value\":1},{\"hash\":1926511580,\"key\":\"scripts\",\"value\":1},{\"hash\":-906261105,\"key\":\"second\",\"value\":1},{\"hash\":113763,\"key\":\"set\",\"value\":1},{\"hash\":109442495,\"key\":\"since\",\"value\":1},{\"hash\":1312641152,\"key\":\"standard\",\"value\":2},{\"hash\":114253,\"key\":\"sun\",\"value\":1},{\"hash\":-613938469,\"key\":\"supporting\",\"value\":1},{\"hash\":-1743411762,\"key\":\"symbols\",\"value\":1},{\"hash\":-1737405355,\"key\":\"systems\",\"value\":1},{\"hash\":-1422419673,\"key\":\"testing\",\"value\":2},{\"hash\":3556635,\"key\":\"text\",\"value\":1,\"next\":{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}}},{\"hash\":3558807,\"key\":\"than\",\"value\":1},{\"hash\":114800,\"key\":\"the\",\"value\":3},{\"hash\":-1629331701,\"key\":\"thenbspunicodenbspstandard\",\"value\":1},{\"hash\":110326007,\"key\":\"there\",\"value\":1},{\"hash\":1106799874,\"key\":\"theuniversal\",\"value\":1,\"next\":{\"hash\":3019778,\"key\":\"been\",\"value\":1}},{\"hash\":-287009735,\"key\":\"unicode\",\"value\":7},{\"hash\":1158383351,\"key\":\"unicodecontains\",\"value\":1,\"next\":{\"hash\":110326007,\"key\":\"there\",\"value\":1}},{\"hash\":236801014,\"key\":\"various\",\"value\":1},{\"hash\":351602733,\"key\":\"version\",\"value\":1,\"next\":{\"hash\":45,\"key\":\"-\",\"value\":2}},{\"hash\":117480,\"key\":\"was\",\"value\":1},{\"hash\":-782072253,\"key\":\"worlds\",\"value\":1,\"next\":{\"hash\":-351764541,\"key\":\"covering\",\"value\":1}},{\"hash\":1602987863,\"key\":\"writing\",\"value\":1,\"next\":{\"hash\":3543,\"key\":\"of\",\"value\":2}}]],\"listUsersUrls\":[\"http://95.158.60.148:8008/kpl/testingPage.html\"],\"dataTypeResponse\":\"JSON\"}";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        String actualResult = wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_emptyUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestUrl = "";
        final String sortingParam = "KEY_ASCENDING";
        final String dataTypeResponse = "JSON";
        final Exception expectedException = new IllegalArgumentException("str is null or empty");

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);
        } catch (IllegalArgumentException e) {
            actualException = e;
        }

        // then
        if (actualException != null) {
            Assert.assertEquals(expectedException.toString(), actualException.toString());
        } else {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testGetWordCounterResult_InvalidUrl_KeyAscSort_JsonResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPageERROR.html";
        final String sortingParam = "KEY_ASCENDING";
        final String dataTypeResponse = "JSON";
        final String expctedExceptionString = "java.lang.RuntimeException: Can't connect to: http://95.158.60.148:8008/kpl/testingPageERROR.html";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);
        } catch (RuntimeException e) {
            actualException = e;
        }

        // then
        if (actualException != null) {
            Assert.assertEquals(expctedExceptionString, actualException.toString());
        } else {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testGetWordCounterResult_okUrl_KeyDescSort_JsonResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPage.html";
        final String sortingParam = "KEY_DESCENDING";
        final String dataTypeResponse = "JSON";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":1602987863,\"key\":\"writing\",\"value\":1,\"next\":{\"hash\":3543,\"key\":\"of\",\"value\":2}},{\"hash\":-782072253,\"key\":\"worlds\",\"value\":1,\"next\":{\"hash\":-351764541,\"key\":\"covering\",\"value\":1}},{\"hash\":117480,\"key\":\"was\",\"value\":1},{\"hash\":351602733,\"key\":\"version\",\"value\":1,\"next\":{\"hash\":45,\"key\":\"-\",\"value\":2}},{\"hash\":236801014,\"key\":\"various\",\"value\":1},{\"hash\":1158383351,\"key\":\"unicodecontains\",\"value\":1,\"next\":{\"hash\":110326007,\"key\":\"there\",\"value\":1}},{\"hash\":-287009735,\"key\":\"unicode\",\"value\":7},{\"hash\":1106799874,\"key\":\"theuniversal\",\"value\":1,\"next\":{\"hash\":3019778,\"key\":\"been\",\"value\":1}},{\"hash\":110326007,\"key\":\"there\",\"value\":1},{\"hash\":-1629331701,\"key\":\"thenbspunicodenbspstandard\",\"value\":1},{\"hash\":114800,\"key\":\"the\",\"value\":3},{\"hash\":3558807,\"key\":\"than\",\"value\":1},{\"hash\":3556635,\"key\":\"text\",\"value\":1,\"next\":{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}}},{\"hash\":-1422419673,\"key\":\"testing\",\"value\":2},{\"hash\":-1737405355,\"key\":\"systems\",\"value\":1},{\"hash\":-1743411762,\"key\":\"symbols\",\"value\":1},{\"hash\":-613938469,\"key\":\"supporting\",\"value\":1},{\"hash\":114253,\"key\":\"sun\",\"value\":1},{\"hash\":1312641152,\"key\":\"standard\",\"value\":2},{\"hash\":109442495,\"key\":\"since\",\"value\":1},{\"hash\":113763,\"key\":\"set\",\"value\":1},{\"hash\":-906261105,\"key\":\"second\",\"value\":1},{\"hash\":1926511580,\"key\":\"scripts\",\"value\":1},{\"hash\":108521650,\"key\":\"risen\",\"value\":1},{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}},{\"hash\":1447425963,\"key\":\"published\",\"value\":1},{\"hash\":3433147,\"key\":\"page\",\"value\":2},{\"hash\":3543,\"key\":\"of\",\"value\":2},{\"hash\":3373752,\"key\":\"name\",\"value\":2},{\"hash\":3357542,\"key\":\"more\",\"value\":1},{\"hash\":-1109904801,\"key\":\"latest\",\"value\":1,\"next\":{\"hash\":104415,\"key\":\"inc\",\"value\":1}},{\"hash\":3370,\"key\":\"is\",\"value\":1},{\"hash\":104415,\"key\":\"inc\",\"value\":1},{\"hash\":3365,\"key\":\"in\",\"value\":2},{\"hash\":-1443541794,\"key\":\"high-light\",\"value\":1},{\"hash\":103067,\"key\":\"has\",\"value\":2},{\"hash\":3149044,\"key\":\"form\",\"value\":1},{\"hash\":-1603782120,\"key\":\"english\",\"value\":1},{\"hash\":-80733044,\"key\":\"developed\",\"value\":1},{\"hash\":-351764541,\"key\":\"covering\",\"value\":1},{\"hash\":-332950906,\"key\":\"consortium\",\"value\":1},{\"hash\":1032937741,\"key\":\"conjunctionwith\",\"value\":1},{\"hash\":707888742,\"key\":\"computing-industry\",\"value\":1,\"next\":{\"hash\":3357542,\"key\":\"more\",\"value\":1}},{\"hash\":1245438033,\"key\":\"characters\",\"value\":1,\"next\":{\"hash\":93030097,\"key\":\"apple\",\"value\":1}},{\"hash\":1564206610,\"key\":\"character\",\"value\":1},{\"hash\":3029703,\"key\":\"book\",\"value\":1},{\"hash\":-1072081724,\"key\":\"beginning\",\"value\":1},{\"hash\":3019778,\"key\":\"been\",\"value\":1},{\"hash\":3122,\"key\":\"as\",\"value\":1,\"next\":{\"hash\":108521650,\"key\":\"risen\",\"value\":1}},{\"hash\":93030097,\"key\":\"apple\",\"value\":1},{\"hash\":96726,\"key\":\"and\",\"value\":2},{\"hash\":97,\"key\":\"a\",\"value\":2},{\"hash\":45,\"key\":\"-\",\"value\":2}]],\"listUsersUrls\":[\"http://95.158.60.148:8008/kpl/testingPage.html\"],\"dataTypeResponse\":\"JSON\"}";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        String actualResult = wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValAscSort_JsonResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPage.html";
        final String sortingParam = "VALUE_ASCENDING";
        final String dataTypeResponse = "JSON";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":1106799874,\"key\":\"theuniversal\",\"value\":1,\"next\":{\"hash\":3019778,\"key\":\"been\",\"value\":1}},{\"hash\":3019778,\"key\":\"been\",\"value\":1},{\"hash\":-332950906,\"key\":\"consortium\",\"value\":1},{\"hash\":-1629331701,\"key\":\"thenbspunicodenbspstandard\",\"value\":1},{\"hash\":-80733044,\"key\":\"developed\",\"value\":1},{\"hash\":1032937741,\"key\":\"conjunctionwith\",\"value\":1},{\"hash\":-906261105,\"key\":\"second\",\"value\":1},{\"hash\":1564206610,\"key\":\"character\",\"value\":1},{\"hash\":3558807,\"key\":\"than\",\"value\":1},{\"hash\":-1603782120,\"key\":\"english\",\"value\":1},{\"hash\":3556635,\"key\":\"text\",\"value\":1,\"next\":{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}}},{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}},{\"hash\":3370,\"key\":\"is\",\"value\":1},{\"hash\":1447425963,\"key\":\"published\",\"value\":1},{\"hash\":351602733,\"key\":\"version\",\"value\":1,\"next\":{\"hash\":45,\"key\":\"-\",\"value\":2}},{\"hash\":3122,\"key\":\"as\",\"value\":1,\"next\":{\"hash\":108521650,\"key\":\"risen\",\"value\":1}},{\"hash\":108521650,\"key\":\"risen\",\"value\":1},{\"hash\":109442495,\"key\":\"since\",\"value\":1},{\"hash\":-782072253,\"key\":\"worlds\",\"value\":1,\"next\":{\"hash\":-351764541,\"key\":\"covering\",\"value\":1}},{\"hash\":-351764541,\"key\":\"covering\",\"value\":1},{\"hash\":-1072081724,\"key\":\"beginning\",\"value\":1},{\"hash\":3029703,\"key\":\"book\",\"value\":1},{\"hash\":114253,\"key\":\"sun\",\"value\":1},{\"hash\":-1743411762,\"key\":\"symbols\",\"value\":1},{\"hash\":1245438033,\"key\":\"characters\",\"value\":1,\"next\":{\"hash\":93030097,\"key\":\"apple\",\"value\":1}},{\"hash\":93030097,\"key\":\"apple\",\"value\":1},{\"hash\":-1737405355,\"key\":\"systems\",\"value\":1},{\"hash\":1602987863,\"key\":\"writing\",\"value\":1,\"next\":{\"hash\":3543,\"key\":\"of\",\"value\":2}},{\"hash\":-613938469,\"key\":\"supporting\",\"value\":1},{\"hash\":1926511580,\"key\":\"scripts\",\"value\":1},{\"hash\":-1443541794,\"key\":\"high-light\",\"value\":1},{\"hash\":-1109904801,\"key\":\"latest\",\"value\":1,\"next\":{\"hash\":104415,\"key\":\"inc\",\"value\":1}},{\"hash\":104415,\"key\":\"inc\",\"value\":1},{\"hash\":113763,\"key\":\"set\",\"value\":1},{\"hash\":707888742,\"key\":\"computing-industry\",\"value\":1,\"next\":{\"hash\":3357542,\"key\":\"more\",\"value\":1}},{\"hash\":3357542,\"key\":\"more\",\"value\":1},{\"hash\":117480,\"key\":\"was\",\"value\":1},{\"hash\":3149044,\"key\":\"form\",\"value\":1},{\"hash\":236801014,\"key\":\"various\",\"value\":1},{\"hash\":1158383351,\"key\":\"unicodecontains\",\"value\":1,\"next\":{\"hash\":110326007,\"key\":\"there\",\"value\":1}},{\"hash\":110326007,\"key\":\"there\",\"value\":1},{\"hash\":1312641152,\"key\":\"standard\",\"value\":2},{\"hash\":103067,\"key\":\"has\",\"value\":2},{\"hash\":3365,\"key\":\"in\",\"value\":2},{\"hash\":-1422419673,\"key\":\"testing\",\"value\":2},{\"hash\":45,\"key\":\"-\",\"value\":2},{\"hash\":3373752,\"key\":\"name\",\"value\":2},{\"hash\":3433147,\"key\":\"page\",\"value\":2},{\"hash\":96726,\"key\":\"and\",\"value\":2},{\"hash\":3543,\"key\":\"of\",\"value\":2},{\"hash\":97,\"key\":\"a\",\"value\":2},{\"hash\":114800,\"key\":\"the\",\"value\":3},{\"hash\":-287009735,\"key\":\"unicode\",\"value\":7}]],\"listUsersUrls\":[\"http://95.158.60.148:8008/kpl/testingPage.html\"],\"dataTypeResponse\":\"JSON\"}";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        String actualResult = wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_ValDescSort_JsonResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPage.html";
        final String sortingParam = "VALUE_DESCENDING";
        final String dataTypeResponse = "JSON";
        final String expectedResult = "{\"success\":true,\"response\":[[{\"hash\":-287009735,\"key\":\"unicode\",\"value\":7},{\"hash\":114800,\"key\":\"the\",\"value\":3},{\"hash\":1312641152,\"key\":\"standard\",\"value\":2},{\"hash\":103067,\"key\":\"has\",\"value\":2},{\"hash\":3365,\"key\":\"in\",\"value\":2},{\"hash\":-1422419673,\"key\":\"testing\",\"value\":2},{\"hash\":45,\"key\":\"-\",\"value\":2},{\"hash\":3373752,\"key\":\"name\",\"value\":2},{\"hash\":3433147,\"key\":\"page\",\"value\":2},{\"hash\":96726,\"key\":\"and\",\"value\":2},{\"hash\":3543,\"key\":\"of\",\"value\":2},{\"hash\":97,\"key\":\"a\",\"value\":2},{\"hash\":1106799874,\"key\":\"theuniversal\",\"value\":1,\"next\":{\"hash\":3019778,\"key\":\"been\",\"value\":1}},{\"hash\":3019778,\"key\":\"been\",\"value\":1},{\"hash\":-332950906,\"key\":\"consortium\",\"value\":1},{\"hash\":-1629331701,\"key\":\"thenbspunicodenbspstandard\",\"value\":1},{\"hash\":-80733044,\"key\":\"developed\",\"value\":1},{\"hash\":1032937741,\"key\":\"conjunctionwith\",\"value\":1},{\"hash\":-906261105,\"key\":\"second\",\"value\":1},{\"hash\":1564206610,\"key\":\"character\",\"value\":1},{\"hash\":3558807,\"key\":\"than\",\"value\":1},{\"hash\":-1603782120,\"key\":\"english\",\"value\":1},{\"hash\":3556635,\"key\":\"text\",\"value\":1,\"next\":{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}}},{\"hash\":1647065627,\"key\":\"repertoire\",\"value\":1,\"next\":{\"hash\":103067,\"key\":\"has\",\"value\":2}},{\"hash\":3370,\"key\":\"is\",\"value\":1},{\"hash\":1447425963,\"key\":\"published\",\"value\":1},{\"hash\":351602733,\"key\":\"version\",\"value\":1,\"next\":{\"hash\":45,\"key\":\"-\",\"value\":2}},{\"hash\":3122,\"key\":\"as\",\"value\":1,\"next\":{\"hash\":108521650,\"key\":\"risen\",\"value\":1}},{\"hash\":108521650,\"key\":\"risen\",\"value\":1},{\"hash\":109442495,\"key\":\"since\",\"value\":1},{\"hash\":-782072253,\"key\":\"worlds\",\"value\":1,\"next\":{\"hash\":-351764541,\"key\":\"covering\",\"value\":1}},{\"hash\":-351764541,\"key\":\"covering\",\"value\":1},{\"hash\":-1072081724,\"key\":\"beginning\",\"value\":1},{\"hash\":3029703,\"key\":\"book\",\"value\":1},{\"hash\":114253,\"key\":\"sun\",\"value\":1},{\"hash\":-1743411762,\"key\":\"symbols\",\"value\":1},{\"hash\":1245438033,\"key\":\"characters\",\"value\":1,\"next\":{\"hash\":93030097,\"key\":\"apple\",\"value\":1}},{\"hash\":93030097,\"key\":\"apple\",\"value\":1},{\"hash\":-1737405355,\"key\":\"systems\",\"value\":1},{\"hash\":1602987863,\"key\":\"writing\",\"value\":1,\"next\":{\"hash\":3543,\"key\":\"of\",\"value\":2}},{\"hash\":-613938469,\"key\":\"supporting\",\"value\":1},{\"hash\":1926511580,\"key\":\"scripts\",\"value\":1},{\"hash\":-1443541794,\"key\":\"high-light\",\"value\":1},{\"hash\":-1109904801,\"key\":\"latest\",\"value\":1,\"next\":{\"hash\":104415,\"key\":\"inc\",\"value\":1}},{\"hash\":104415,\"key\":\"inc\",\"value\":1},{\"hash\":113763,\"key\":\"set\",\"value\":1},{\"hash\":707888742,\"key\":\"computing-industry\",\"value\":1,\"next\":{\"hash\":3357542,\"key\":\"more\",\"value\":1}},{\"hash\":3357542,\"key\":\"more\",\"value\":1},{\"hash\":117480,\"key\":\"was\",\"value\":1},{\"hash\":3149044,\"key\":\"form\",\"value\":1},{\"hash\":236801014,\"key\":\"various\",\"value\":1},{\"hash\":1158383351,\"key\":\"unicodecontains\",\"value\":1,\"next\":{\"hash\":110326007,\"key\":\"there\",\"value\":1}},{\"hash\":110326007,\"key\":\"there\",\"value\":1}]],\"listUsersUrls\":[\"http://95.158.60.148:8008/kpl/testingPage.html\"],\"dataTypeResponse\":\"JSON\"}";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        String actualResult = wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetWordCounterResult_okUrl_InvalidSort_JsonResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPage.html";
        final String sortingParam = "invalidSortingParam";
        final String dataTypeResponse = "JSON";
        final String expctedExceptionString = "java.lang.IllegalArgumentException: No enum constant com.qalight.javacourse.core.WordResultSorter.invalidSortingParam";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);
        } catch (RuntimeException e) {
            actualException = e;
        }

        // then
        if (actualException != null) {
            Assert.assertEquals(expctedExceptionString, actualException.toString());
        } else {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testGetWordCounterResult_okUrl_KeyAscSort_InvalidResp() {
        // given
        final String clientRequestUrl = "http://95.158.60.148:8008/kpl/testingPage.html";
        final String sortingParam = "KEY_ASCENDING";
        final String dataTypeResponse = "invalidResponseParam";
        final String expctedExceptionString = "java.lang.IllegalArgumentException: No enum constant com.qalight.javacourse.service.ResultPresentation.invalidResponseParam";

        // when
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        Exception actualException = null;
        try {
            wordCounterService.getWordCounterResult(clientRequestUrl, sortingParam, dataTypeResponse);
        } catch (RuntimeException e) {
            actualException = e;
        }

        // then
        if (actualException != null) {
            Assert.assertEquals(expctedExceptionString, actualException.toString());
        } else {
            Assert.assertFalse(true);
        }
    }

}

// given

// when

// then