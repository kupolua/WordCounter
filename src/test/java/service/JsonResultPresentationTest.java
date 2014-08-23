package service;

import com.qalight.javacourse.service.JsonResultPresentation;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class JsonResultPresentationTest {
    JsonResultPresentation jsonResultPresentation = new JsonResultPresentation();
    // todo: use interface for all presentation types and remove enum

    @Test
    public void testCreateResponse() throws Exception {
        //given
        final String expectedJsonResponse = "{\"success\":true,\"response\":[[{\"hash\":1355330097,\"key\":\"Project\",\"value\":24},{\"hash\":2702083,\"key\":\"Word\",\"value\":13,\"next\":{\"hash\":-1672515181,\"key\":\"Counter\",\"value\":5}},{\"hash\":-1672515181,\"key\":\"Counter\",\"value\":5},{\"hash\":69610644,\"key\":\"Hello\",\"value\":10},{\"hash\":83767180,\"key\":\"World\",\"value\":7}]],\"listUsersUrls\":[\"http://www.eslfast.com/supereasy/se/supereasy006.htm\"],\"dataTypeResponse\":\"JSON\"}";
        final String sourceLink = "http://www.eslfast.com/supereasy/se/supereasy006.htm";
        final Map<String, Integer> countedWords = new HashMap<String, Integer>();
                countedWords.put("Hello", 10);
                countedWords.put("World", 7);
                countedWords.put("Word", 13);
                countedWords.put("Counter", 5);
                countedWords.put("Project", 24);
        final Set<Map.Entry<String, Integer>> set = countedWords.entrySet();
        final List<Map.Entry<String, Integer>> sortedWords = new ArrayList<Map.Entry<String, Integer>>(set);
        final String dataTypeResponse = "JSON";

        //when
        String actualJsonResponse = jsonResultPresentation.createResponse(sourceLink, sortedWords, dataTypeResponse);

        //then
        Assert.assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    public void testCreateErrorResponse() throws Exception {
        //given
        String expectedJsonResponse = "{\"success\":false,\"errorMessageToUser\":";

        //when
        Boolean isExpectedJsonResponse = jsonResultPresentation.createErrorResponse("Your request is empty.").startsWith(expectedJsonResponse);

        //then
        Assert.assertTrue(isExpectedJsonResponse);
    }
}