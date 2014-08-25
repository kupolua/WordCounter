import com.qalight.javacourse.service.HtmlFormReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;

public class HtmlFormReaderTest {

    @Test
    public void testReadHtmlSourceFile() throws Exception {

        // given
        final InputStream in = this.getClass().getResourceAsStream("/index.html");
        final String expectedHtmlPageString = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

        // when
        HtmlFormReader htmlFormReader = new HtmlFormReader();
        String actualIndexHtmlString = htmlFormReader.readHtmlSourceFile("index.html");

        // then
        Assert.assertEquals(expectedHtmlPageString, actualIndexHtmlString);

    }

    @Test
    public void testReadErrorWebPage() throws Exception {

        // given
        final InputStream in = this.getClass().getResourceAsStream("/error.html");
        final String expectedHtmlPageString = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

        // when
        HtmlFormReader htmlFormReader = new HtmlFormReader();
        String actualHtmlPageString = htmlFormReader.readHtmlSourceFile("error.html");

        // then
        Assert.assertEquals(expectedHtmlPageString, actualHtmlPageString);

    }

    @Test
    public void testReturnErrorPageWhenRequiredPageIsMissing() {

        // given
        final InputStream in = this.getClass().getResourceAsStream("/error.html");
        final String expectedHtmlPageString = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

        // when
        HtmlFormReader htmlFormReader = new HtmlFormReader();
        String actualErrorHtmlString = htmlFormReader.readHtmlSourceFile("someRequiredPage.html");

        // then
        Assert.assertEquals(expectedHtmlPageString, actualErrorHtmlString);

    }

}