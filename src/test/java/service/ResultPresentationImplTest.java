package service;

import com.qalight.javacourse.service.JsonResultPresentation;
import com.qalight.javacourse.service.ResultPresentation;
import com.qalight.javacourse.service.ResultPresentationImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResultPresentationImplTest {
    private JsonResultPresentation jsonResultPresentation;
    private ResultPresentationImpl resultPresentationImpl;

    @Before
    public void setup() {
        jsonResultPresentation = new JsonResultPresentation();
        resultPresentationImpl = new ResultPresentationImpl();
    }

    @Test
    public void testIsNullResultPresentation() {
        //given
        final String dataTypeResponse = null;
        final String expectedNullPointerException = "java.lang.NullPointerException: com.qalight.javacourse.service.ResultPresentationImpl Method: getResultPresentation, Argument: dataTypeResponse is Null";

        //when
        NullPointerException actualNullPointerException = null;
        try {
            resultPresentationImpl.getResultPresentation(dataTypeResponse);
        } catch (NullPointerException e) {
            actualNullPointerException = e;
        }

        //then
        Assert.assertEquals(expectedNullPointerException, actualNullPointerException.toString());
    }

    @Test
    public void testGetResultPresentation() throws Exception {
        //given
        final String dataTypeResponse = "json";
        final JsonResultPresentation jsonResultPresentation = new JsonResultPresentation();
        final String expectedJsonResultPresentation = jsonResultPresentation.getClass().getName();

        //when
        ResultPresentation resultPresentation = resultPresentationImpl.getResultPresentation(dataTypeResponse);
        String actualResultPresentation = resultPresentation.getClass().getName();

        //then
        Assert.assertEquals(expectedJsonResultPresentation, actualResultPresentation);

    }
}