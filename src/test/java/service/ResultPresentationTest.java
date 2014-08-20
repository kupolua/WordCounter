package service;

import com.qalight.javacourse.service.JsonResultPresentation;
import com.qalight.javacourse.service.ResultPresentation;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

public class ResultPresentationTest {
    JsonResultPresentation jsonResultPresentation = new JsonResultPresentation();

    @Test
    public void testCreateResponse() throws Exception {
        // given
        final Set<String> expectedEnumValues = new TreeSet<String>() {{
            add("JSON");
        }};

        //when
        Set<String> actualEnumValues = new TreeSet<String>();
        for (ResultPresentation resultPresentation : ResultPresentation.values()) {
            actualEnumValues.add(resultPresentation.toString());
        }

        //then
        Assert.assertEquals(expectedEnumValues, actualEnumValues);
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