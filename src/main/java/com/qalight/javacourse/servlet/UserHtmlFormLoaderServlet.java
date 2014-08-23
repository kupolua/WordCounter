package com.qalight.javacourse.servlet;

import com.qalight.javacourse.service.HtmlFormReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserHtmlFormLoaderServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHtmlFormLoaderServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String INDEX_FILE = "index.html";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String userHTMLForm;
        response.setContentType("text/html");
        HttpHelper.setResponseHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
        userHTMLForm = loadUserHtmlForm(INDEX_FILE);

        try (PrintWriter writer = response.getWriter()) {
            LOG.info("Printing user html form.");
            writer.println(userHTMLForm);
        } catch (IOException e) {
            String msg = "userHTMLForm can't be printed.";
            LOG.error(msg, e);
            throw new IllegalStateException(msg, e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public String loadUserHtmlForm(String fileName) {
        HtmlFormReader htmlFormReader = new HtmlFormReader();
        return htmlFormReader.readHtmlSourceFile(fileName);
    }

}
