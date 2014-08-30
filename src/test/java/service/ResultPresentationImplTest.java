package service;

import com.qalight.javacourse.service.JsonResultPresentation;
import com.qalight.javacourse.service.ResultPresentation;
import com.qalight.javacourse.service.ResultPresentationImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResultPresentationImplTest {
    private ResultPresentationImpl resultPresentationImpl;

    @Before
    public void setup() {
        resultPresentationImpl = new ResultPresentationImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsNullResultPresentation() {
        //given
        final String dataTypeResponse = null;

        //when
        resultPresentationImpl.getResultPresentation(dataTypeResponse);
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