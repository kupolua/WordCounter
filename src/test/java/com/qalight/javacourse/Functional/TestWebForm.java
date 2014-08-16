package com.qalight.javacourse.Functional;

import com.qalight.javacourse.EntryPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Vova on 08.08.2014.
 */

public class TestWebForm {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private final String expectedResultEmptyUrlRequest = "Your request is empty.";
    private final String expectedResultUrlContainHttps = "Your request is empty.";
    private final String expectedResultIncorrectUrl = "";
    private int timeWait = 2000;

    @Before
    public void setUp() throws Exception {
        EntryPoint.jettyStart();
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8021/inputForm/UserHtmlFormLoaderServlet";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testEmptyUrlRequest() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultEmptyUrlRequest, actualResult);
    }

    @Test
    public void testUrlContainHttps() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("https://easypay.ua/");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultUrlContainHttps, actualResult);
    }
    @Ignore
    @Test
    public void testIncorrectUrl() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://htmlbook.u/html/input");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        assertEquals(expectedResultIncorrectUrl, actualResult);
    }

    @Test
    public void testSortingKeyAscending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        System.out.println(actualResult);
        String expectedResult = "http://www.httpunit.org/\n" +
                "Word:\n" +
                "Count :\n" +
                "a 13\n" +
                "able 1\n" +
                "access 1\n" +
                "allows 1\n" +
                "also 1\n" +
                "an 1\n" +
                "and 7\n" +
                "application 2\n" +
                "apr 2\n" +
                "are 1\n" +
                "as 3\n" +
                "asjunit 1\n" +
                "atid 2\n" +
                "aug 1\n" +
                "authentication 1\n" +
                "automated 1\n" +
                "automatic 1\n" +
                "available 2\n" +
                "basic 1\n" +
                "be 2\n" +
                "begun 1\n" +
                "behavior 1\n" +
                "being 2\n" +
                "browser 2\n" +
                "bug 2\n" +
                "but 1\n" +
                "by 2\n" +
                "bypass 1\n" +
                "calls 1\n" +
                "can 1\n" +
                "case 1\n" +
                "citations 1\n" +
                "code 3\n" +
                "com 2\n" +
                "combined 1\n" +
                "come 1\n" +
                "container 1\n" +
                "containers 1\n" +
                "cookbook 1\n" +
                "cookies 1\n" +
                "copyright 1\n" +
                "created 1\n" +
                "database 1\n" +
                "develop 2\n" +
                "developers 1\n" +
                "direct 1\n" +
                "distributed 1\n" +
                "distribution 1\n" +
                "documentation 1\n" +
                "dom 1\n" +
                "donations 1\n" +
                "download 4\n" +
                "easy 2\n" +
                "edu 1\n" +
                "either 2\n" +
                "emulates 1\n" +
                "ensure 1\n" +
                "examine 1\n" +
                "example 1\n" +
                "external 1\n" +
                "fairly 1\n" +
                "faq 2\n" +
                "feature 1\n" +
                "files 1\n" +
                "form 1\n" +
                "format 1\n" +
                "forms 1\n" +
                "framework 1\n" +
                "frameworks 1\n" +
                "from 1\n" +
                "ftp 2\n" +
                "functioning 1\n" +
                "gold 1\n" +
                "great 1\n" +
                "group 4\n" +
                "has 1\n" +
                "have 1\n" +
                "heavily 1\n" +
                "home 3\n" +
                "hosted 1\n" +
                "htm 1\n" +
                "http 10\n" +
                "httpunit 13\n" +
                "id 4\n" +
                "if 2\n" +
                "in 4\n" +
                "included 1\n" +
                "including 1\n" +
                "incorporated 1\n" +
                "intro 1\n" +
                "is 3\n" +
                "isi 1\n" +
                "it 2\n" +
                "items 1\n" +
                "java 2\n" +
                "javadoc 2\n" +
                "javascript 2\n" +
                "junit 1\n" +
                "license 1\n" +
                "links 1\n" +
                "list 1\n" +
                "listinfo 1\n" +
                "lists 2\n" +
                "mailing 1\n" +
                "maintained 1\n" +
                "makes 1\n" +
                "making 1\n" +
                "manual 1\n" +
                "mar 2\n" +
                "may 2\n" +
                "methodology 1\n" +
                "most 1\n" +
                "need 1\n" +
                "net 6\n" +
                "new 1\n" +
                "news 1\n" +
                "notes 1\n" +
                "nov 1\n" +
                "oct 1\n" +
                "of 5\n" +
                "oftesting 1\n" +
                "on 2\n" +
                "open 1\n" +
                "or 2\n" +
                "org 1\n" +
                "page 1\n" +
                "pages 1\n" +
                "part 1\n" +
                "patches 1\n" +
                "php 1\n" +
                "portions 1\n" +
                "practitioners 1\n" +
                "prdownloads 1\n" +
                "program 1\n" +
                "programming 1\n" +
                "project 2\n" +
                "quickly 1\n" +
                "range 1\n" +
                "redirection 1\n" +
                "released 6\n" +
                "relevant 1\n" +
                "relies 1\n" +
                "repository 2\n" +
                "requests 1\n" +
                "returned 1\n" +
                "rfc 1\n" +
                "rudimentarycookbook 1\n" +
                "russell 1\n" +
                "s 1\n" +
                "same 1\n" +
                "servlet 1\n" +
                "servlets 1\n" +
                "servletunit 1\n" +
                "several 1\n" +
                "simply 1\n" +
                "site 4\n" +
                "sites 1\n" +
                "software 1\n" +
                "sourceforge 6\n" +
                "submission 1\n" +
                "subversion 2\n" +
                "such 1\n" +
                "support 2\n" +
                "svn 1\n" +
                "switched 1\n" +
                "tables 1\n" +
                "techniques 1\n" +
                "test 4\n" +
                "tested 1\n" +
                "testing 1\n" +
                "tests 1\n" +
                "text 1\n" +
                "that 2\n" +
                "the 8\n" +
                "theextreme 1\n" +
                "them 1\n" +
                "this 1\n" +
                "to 12\n" +
                "tracker 3\n" +
                "tutorial 2\n" +
                "txt 1\n" +
                "use 1\n" +
                "used 2\n" +
                "user 1\n" +
                "usingservletunit 1\n" +
                "verify 1\n" +
                "very 1\n" +
                "want 2\n" +
                "way 1\n" +
                "web 5\n" +
                "what 2\n" +
                "when 1\n" +
                "which 1\n" +
                "with 2\n" +
                "without 1\n" +
                "work 2\n" +
                "working 1\n" +
                "works 1\n" +
                "write 1\n" +
                "written 1\n" +
                "www 3\n" +
                "xml 1\n" +
                "xp 1\n" +
                "xprogramming 2\n" +
                "you 3\n" +
                "your 1\n" +
                "zip 1";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSortingValueAscending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.xpath("(//input[@name='userChoice'])[2]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = "http://www.httpunit.org/\n" +
                "Word:\n" +
                "Count :\n" +
                "theextreme 1\n" +
                "servlet 1\n" +
                "your 1\n" +
                "without 1\n" +
                "xml 1\n" +
                "portions 1\n" +
                "fairly 1\n" +
                "documentation 1\n" +
                "patches 1\n" +
                "prdownloads 1\n" +
                "them 1\n" +
                "an 1\n" +
                "switched 1\n" +
                "items 1\n" +
                "usingservletunit 1\n" +
                "access 1\n" +
                "emulates 1\n" +
                "examine 1\n" +
                "manual 1\n" +
                "cookies 1\n" +
                "example 1\n" +
                "gold 1\n" +
                "citations 1\n" +
                "same 1\n" +
                "are 1\n" +
                "behavior 1\n" +
                "makes 1\n" +
                "svn 1\n" +
                "txt 1\n" +
                "such 1\n" +
                "listinfo 1\n" +
                "automated 1\n" +
                "s 1\n" +
                "calls 1\n" +
                "asjunit 1\n" +
                "combined 1\n" +
                "returned 1\n" +
                "isi 1\n" +
                "open 1\n" +
                "but 1\n" +
                "notes 1\n" +
                "sites 1\n" +
                "incorporated 1\n" +
                "written 1\n" +
                "has 1\n" +
                "programming 1\n" +
                "which 1\n" +
                "making 1\n" +
                "ensure 1\n" +
                "need 1\n" +
                "this 1\n" +
                "list 1\n" +
                "rfc 1\n" +
                "external 1\n" +
                "aug 1\n" +
                "tests 1\n" +
                "containers 1\n" +
                "page 1\n" +
                "oftesting 1\n" +
                "nov 1\n" +
                "htm 1\n" +
                "news 1\n" +
                "junit 1\n" +
                "org 1\n" +
                "way 1\n" +
                "bypass 1\n" +
                "functioning 1\n" +
                "form 1\n" +
                "xp 1\n" +
                "servlets 1\n" +
                "container 1\n" +
                "very 1\n" +
                "copyright 1\n" +
                "software 1\n" +
                "developers 1\n" +
                "frameworks 1\n" +
                "tested 1\n" +
                "quickly 1\n" +
                "program 1\n" +
                "when 1\n" +
                "tables 1\n" +
                "text 1\n" +
                "write 1\n" +
                "case 1\n" +
                "zip 1\n" +
                "oct 1\n" +
                "testing 1\n" +
                "distributed 1\n" +
                "come 1\n" +
                "practitioners 1\n" +
                "works 1\n" +
                "dom 1\n" +
                "donations 1\n" +
                "servletunit 1\n" +
                "direct 1\n" +
                "range 1\n" +
                "requests 1\n" +
                "mailing 1\n" +
                "distribution 1\n" +
                "feature 1\n" +
                "cookbook 1\n" +
                "russell 1\n" +
                "have 1\n" +
                "verify 1\n" +
                "great 1\n" +
                "framework 1\n" +
                "able 1\n" +
                "php 1\n" +
                "submission 1\n" +
                "use 1\n" +
                "several 1\n" +
                "begun 1\n" +
                "hosted 1\n" +
                "pages 1\n" +
                "intro 1\n" +
                "from 1\n" +
                "links 1\n" +
                "maintained 1\n" +
                "new 1\n" +
                "including 1\n" +
                "created 1\n" +
                "format 1\n" +
                "most 1\n" +
                "relevant 1\n" +
                "license 1\n" +
                "techniques 1\n" +
                "edu 1\n" +
                "files 1\n" +
                "basic 1\n" +
                "part 1\n" +
                "can 1\n" +
                "database 1\n" +
                "relies 1\n" +
                "working 1\n" +
                "redirection 1\n" +
                "included 1\n" +
                "authentication 1\n" +
                "allows 1\n" +
                "automatic 1\n" +
                "also 1\n" +
                "heavily 1\n" +
                "rudimentarycookbook 1\n" +
                "user 1\n" +
                "methodology 1\n" +
                "simply 1\n" +
                "forms 1\n" +
                "java 2\n" +
                "apr 2\n" +
                "want 2\n" +
                "easy 2\n" +
                "javascript 2\n" +
                "be 2\n" +
                "javadoc 2\n" +
                "by 2\n" +
                "bug 2\n" +
                "available 2\n" +
                "project 2\n" +
                "either 2\n" +
                "atid 2\n" +
                "develop 2\n" +
                "support 2\n" +
                "tutorial 2\n" +
                "xprogramming 2\n" +
                "with 2\n" +
                "what 2\n" +
                "repository 2\n" +
                "faq 2\n" +
                "if 2\n" +
                "work 2\n" +
                "being 2\n" +
                "it 2\n" +
                "mar 2\n" +
                "ftp 2\n" +
                "may 2\n" +
                "com 2\n" +
                "used 2\n" +
                "that 2\n" +
                "lists 2\n" +
                "browser 2\n" +
                "on 2\n" +
                "or 2\n" +
                "subversion 2\n" +
                "application 2\n" +
                "you 3\n" +
                "as 3\n" +
                "home 3\n" +
                "is 3\n" +
                "tracker 3\n" +
                "www 3\n" +
                "code 3\n" +
                "test 4\n" +
                "id 4\n" +
                "in 4\n" +
                "site 4\n" +
                "download 4\n" +
                "group 4\n" +
                "web 5\n" +
                "of 5\n" +
                "released 6\n" +
                "sourceforge 6\n" +
                "net 6\n" +
                "and 7\n" +
                "the 8\n" +
                "http 10\n" +
                "to 12\n" +
                "httpunit 13\n" +
                "a 13";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSortingKeyDescending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.xpath("(//input[@name='userChoice'])[3]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = "http://www.httpunit.org/\n" +
                "Word:\n" +
                "Count :\n" +
                "zip 1\n" +
                "your 1\n" +
                "you 3\n" +
                "xprogramming 2\n" +
                "xp 1\n" +
                "xml 1\n" +
                "www 3\n" +
                "written 1\n" +
                "write 1\n" +
                "works 1\n" +
                "working 1\n" +
                "work 2\n" +
                "without 1\n" +
                "with 2\n" +
                "which 1\n" +
                "when 1\n" +
                "what 2\n" +
                "web 5\n" +
                "way 1\n" +
                "want 2\n" +
                "very 1\n" +
                "verify 1\n" +
                "usingservletunit 1\n" +
                "user 1\n" +
                "used 2\n" +
                "use 1\n" +
                "txt 1\n" +
                "tutorial 2\n" +
                "tracker 3\n" +
                "to 12\n" +
                "this 1\n" +
                "them 1\n" +
                "theextreme 1\n" +
                "the 8\n" +
                "that 2\n" +
                "text 1\n" +
                "tests 1\n" +
                "testing 1\n" +
                "tested 1\n" +
                "test 4\n" +
                "techniques 1\n" +
                "tables 1\n" +
                "switched 1\n" +
                "svn 1\n" +
                "support 2\n" +
                "such 1\n" +
                "subversion 2\n" +
                "submission 1\n" +
                "sourceforge 6\n" +
                "software 1\n" +
                "sites 1\n" +
                "site 4\n" +
                "simply 1\n" +
                "several 1\n" +
                "servletunit 1\n" +
                "servlets 1\n" +
                "servlet 1\n" +
                "same 1\n" +
                "s 1\n" +
                "russell 1\n" +
                "rudimentarycookbook 1\n" +
                "rfc 1\n" +
                "returned 1\n" +
                "requests 1\n" +
                "repository 2\n" +
                "relies 1\n" +
                "relevant 1\n" +
                "released 6\n" +
                "redirection 1\n" +
                "range 1\n" +
                "quickly 1\n" +
                "project 2\n" +
                "programming 1\n" +
                "program 1\n" +
                "prdownloads 1\n" +
                "practitioners 1\n" +
                "portions 1\n" +
                "php 1\n" +
                "patches 1\n" +
                "part 1\n" +
                "pages 1\n" +
                "page 1\n" +
                "org 1\n" +
                "or 2\n" +
                "open 1\n" +
                "on 2\n" +
                "oftesting 1\n" +
                "of 5\n" +
                "oct 1\n" +
                "nov 1\n" +
                "notes 1\n" +
                "news 1\n" +
                "new 1\n" +
                "net 6\n" +
                "need 1\n" +
                "most 1\n" +
                "methodology 1\n" +
                "may 2\n" +
                "mar 2\n" +
                "manual 1\n" +
                "making 1\n" +
                "makes 1\n" +
                "maintained 1\n" +
                "mailing 1\n" +
                "lists 2\n" +
                "listinfo 1\n" +
                "list 1\n" +
                "links 1\n" +
                "license 1\n" +
                "junit 1\n" +
                "javascript 2\n" +
                "javadoc 2\n" +
                "java 2\n" +
                "items 1\n" +
                "it 2\n" +
                "isi 1\n" +
                "is 3\n" +
                "intro 1\n" +
                "incorporated 1\n" +
                "including 1\n" +
                "included 1\n" +
                "in 4\n" +
                "if 2\n" +
                "id 4\n" +
                "httpunit 13\n" +
                "http 10\n" +
                "htm 1\n" +
                "hosted 1\n" +
                "home 3\n" +
                "heavily 1\n" +
                "have 1\n" +
                "has 1\n" +
                "group 4\n" +
                "great 1\n" +
                "gold 1\n" +
                "functioning 1\n" +
                "ftp 2\n" +
                "from 1\n" +
                "frameworks 1\n" +
                "framework 1\n" +
                "forms 1\n" +
                "format 1\n" +
                "form 1\n" +
                "files 1\n" +
                "feature 1\n" +
                "faq 2\n" +
                "fairly 1\n" +
                "external 1\n" +
                "example 1\n" +
                "examine 1\n" +
                "ensure 1\n" +
                "emulates 1\n" +
                "either 2\n" +
                "edu 1\n" +
                "easy 2\n" +
                "download 4\n" +
                "donations 1\n" +
                "dom 1\n" +
                "documentation 1\n" +
                "distribution 1\n" +
                "distributed 1\n" +
                "direct 1\n" +
                "developers 1\n" +
                "develop 2\n" +
                "database 1\n" +
                "created 1\n" +
                "copyright 1\n" +
                "cookies 1\n" +
                "cookbook 1\n" +
                "containers 1\n" +
                "container 1\n" +
                "come 1\n" +
                "combined 1\n" +
                "com 2\n" +
                "code 3\n" +
                "citations 1\n" +
                "case 1\n" +
                "can 1\n" +
                "calls 1\n" +
                "bypass 1\n" +
                "by 2\n" +
                "but 1\n" +
                "bug 2\n" +
                "browser 2\n" +
                "being 2\n" +
                "behavior 1\n" +
                "begun 1\n" +
                "be 2\n" +
                "basic 1\n" +
                "available 2\n" +
                "automatic 1\n" +
                "automated 1\n" +
                "authentication 1\n" +
                "aug 1\n" +
                "atid 2\n" +
                "asjunit 1\n" +
                "as 3\n" +
                "are 1\n" +
                "apr 2\n" +
                "application 2\n" +
                "and 7\n" +
                "an 1\n" +
                "also 1\n" +
                "allows 1\n" +
                "access 1\n" +
                "able 1\n" +
                "a 13";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSortingValueDescending() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/");
        driver.findElement(By.xpath("(//input[@name='userChoice'])[4]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = "http://www.httpunit.org/\n" +
                "Word:\n" +
                "Count :\n" +
                "httpunit 13\n" +
                "a 13\n" +
                "to 12\n" +
                "http 10\n" +
                "the 8\n" +
                "and 7\n" +
                "released 6\n" +
                "sourceforge 6\n" +
                "net 6\n" +
                "web 5\n" +
                "of 5\n" +
                "test 4\n" +
                "id 4\n" +
                "in 4\n" +
                "site 4\n" +
                "download 4\n" +
                "group 4\n" +
                "you 3\n" +
                "as 3\n" +
                "home 3\n" +
                "is 3\n" +
                "tracker 3\n" +
                "www 3\n" +
                "code 3\n" +
                "java 2\n" +
                "apr 2\n" +
                "want 2\n" +
                "easy 2\n" +
                "javascript 2\n" +
                "be 2\n" +
                "javadoc 2\n" +
                "by 2\n" +
                "bug 2\n" +
                "available 2\n" +
                "project 2\n" +
                "either 2\n" +
                "atid 2\n" +
                "develop 2\n" +
                "support 2\n" +
                "tutorial 2\n" +
                "xprogramming 2\n" +
                "with 2\n" +
                "what 2\n" +
                "repository 2\n" +
                "faq 2\n" +
                "if 2\n" +
                "work 2\n" +
                "being 2\n" +
                "it 2\n" +
                "mar 2\n" +
                "ftp 2\n" +
                "may 2\n" +
                "com 2\n" +
                "used 2\n" +
                "that 2\n" +
                "lists 2\n" +
                "browser 2\n" +
                "on 2\n" +
                "or 2\n" +
                "subversion 2\n" +
                "application 2\n" +
                "theextreme 1\n" +
                "servlet 1\n" +
                "your 1\n" +
                "without 1\n" +
                "xml 1\n" +
                "portions 1\n" +
                "fairly 1\n" +
                "documentation 1\n" +
                "patches 1\n" +
                "prdownloads 1\n" +
                "them 1\n" +
                "an 1\n" +
                "switched 1\n" +
                "items 1\n" +
                "usingservletunit 1\n" +
                "access 1\n" +
                "emulates 1\n" +
                "examine 1\n" +
                "manual 1\n" +
                "cookies 1\n" +
                "example 1\n" +
                "gold 1\n" +
                "citations 1\n" +
                "same 1\n" +
                "are 1\n" +
                "behavior 1\n" +
                "makes 1\n" +
                "svn 1\n" +
                "txt 1\n" +
                "such 1\n" +
                "listinfo 1\n" +
                "automated 1\n" +
                "s 1\n" +
                "calls 1\n" +
                "asjunit 1\n" +
                "combined 1\n" +
                "returned 1\n" +
                "isi 1\n" +
                "open 1\n" +
                "but 1\n" +
                "notes 1\n" +
                "sites 1\n" +
                "incorporated 1\n" +
                "written 1\n" +
                "has 1\n" +
                "programming 1\n" +
                "which 1\n" +
                "making 1\n" +
                "ensure 1\n" +
                "need 1\n" +
                "this 1\n" +
                "list 1\n" +
                "rfc 1\n" +
                "external 1\n" +
                "aug 1\n" +
                "tests 1\n" +
                "containers 1\n" +
                "page 1\n" +
                "oftesting 1\n" +
                "nov 1\n" +
                "htm 1\n" +
                "news 1\n" +
                "junit 1\n" +
                "org 1\n" +
                "way 1\n" +
                "bypass 1\n" +
                "functioning 1\n" +
                "form 1\n" +
                "xp 1\n" +
                "servlets 1\n" +
                "container 1\n" +
                "very 1\n" +
                "copyright 1\n" +
                "software 1\n" +
                "developers 1\n" +
                "frameworks 1\n" +
                "tested 1\n" +
                "quickly 1\n" +
                "program 1\n" +
                "when 1\n" +
                "tables 1\n" +
                "text 1\n" +
                "write 1\n" +
                "case 1\n" +
                "zip 1\n" +
                "oct 1\n" +
                "testing 1\n" +
                "distributed 1\n" +
                "come 1\n" +
                "practitioners 1\n" +
                "works 1\n" +
                "dom 1\n" +
                "donations 1\n" +
                "servletunit 1\n" +
                "direct 1\n" +
                "range 1\n" +
                "requests 1\n" +
                "mailing 1\n" +
                "distribution 1\n" +
                "feature 1\n" +
                "cookbook 1\n" +
                "russell 1\n" +
                "have 1\n" +
                "verify 1\n" +
                "great 1\n" +
                "framework 1\n" +
                "able 1\n" +
                "php 1\n" +
                "submission 1\n" +
                "use 1\n" +
                "several 1\n" +
                "begun 1\n" +
                "hosted 1\n" +
                "pages 1\n" +
                "intro 1\n" +
                "from 1\n" +
                "links 1\n" +
                "maintained 1\n" +
                "new 1\n" +
                "including 1\n" +
                "created 1\n" +
                "format 1\n" +
                "most 1\n" +
                "relevant 1\n" +
                "license 1\n" +
                "techniques 1\n" +
                "edu 1\n" +
                "files 1\n" +
                "basic 1\n" +
                "part 1\n" +
                "can 1\n" +
                "database 1\n" +
                "relies 1\n" +
                "working 1\n" +
                "redirection 1\n" +
                "included 1\n" +
                "authentication 1\n" +
                "allows 1\n" +
                "automatic 1\n" +
                "also 1\n" +
                "heavily 1\n" +
                "rudimentarycookbook 1\n" +
                "user 1\n" +
                "methodology 1\n" +
                "simply 1\n" +
                "forms 1";
        assertEquals(expectedResult, actualResult);
    }
    @Ignore
    @Test
    public void testConsolidatedResult() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("userRequest")).clear();
        driver.findElement(By.id("userRequest")).sendKeys("http://www.httpunit.org/, " +
                "http://www.httpunit.org/doc/developers.html");
        driver.findElement(By.xpath("(//input[@name='typeStatisticResult'])[2]")).click();
        driver.findElement(By.id("myButton")).click();
        sleep(timeWait * 2);
        String actualResult = driver.findElement(By.id("ajaxResponse")).getText();
        String expectedResult = new String(Files.readAllBytes(Paths.get("src\\test\\resources\\ConsolidatedResult.txt")));
        assertEquals(expectedResult, actualResult);
    }

    @After
    public void tearDown() throws Exception {
        EntryPoint.jettyStop();
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    private String getTextFromFile(String fileName) {
        InputStream in = this.getClass().getResourceAsStream("/" + fileName);
        String text = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        return text;
    }
}
