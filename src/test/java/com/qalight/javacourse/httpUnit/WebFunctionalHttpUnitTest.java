package com.qalight.javacourse.httpUnit;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by Vova on 25.07.2014.
 */
// todo: all tests should end with word *Test TestWebFunctionalHttpUnit
// todo: give a test meaningful name! TestWebFunctionalHttpUnit
// todo: move integration and functional tests to separate dir from unit tests TestWebFunctionalHttpUnit
public class WebFunctionalHttpUnitTest {

    @Test
    @Ignore
    public void testFunctional() {
        WebConversation wc = new WebConversation();
        try {
            HttpUnitOptions.setScriptingEnabled(false);
            WebResponse resp = wc.getResponse("http://localhost:8021/inputForm/UserHtmlFormLoaderServlet");
            WebForm form = resp.getForms()[0];
            form.setParameter("userRequest1", "http://www.httpunit.org/");
//            System.out.println(form.getText());
//            Button btn = form.getButtons()[0];
//            btn.click();
//            System.out.println(form.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
