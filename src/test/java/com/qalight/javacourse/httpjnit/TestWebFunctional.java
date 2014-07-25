package com.qalight.javacourse.httpjnit;

import com.meterware.httpunit.*;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by Vova on 25.07.2014.
 */
public class TestWebFunctional {

    @Test
    public void testFunctional() {
        WebConversation wc = new WebConversation();
        try {
            HttpUnitOptions.setScriptingEnabled(false);
            WebResponse resp = wc.getResponse( "http://localhost:8021/inputForm/UserHTMLFormLoader" );
            WebForm form = resp.getForms()[0];
            form.setParameter("userRequest1", "http://www.httpunit.org/");
//            System.out.println(form.getText());
            Button btn = form.getButtons()[0];
            btn.click();
            System.out.println(form.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
