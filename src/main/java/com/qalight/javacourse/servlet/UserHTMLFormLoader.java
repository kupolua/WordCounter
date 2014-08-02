package com.qalight.javacourse.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kpl on 23.07.2014.
 */

public class UserHTMLFormLoader extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UserHTMLFormLoader.class);
    private static final long serialVersionUID = -6154475799000019575L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String userHTMLForm;

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        userHTMLForm = loadUserHTMLForm("index.html");

        try {
            LOG.info("Printing user HTML form.");
            response.getWriter().println(userHTMLForm);
        } catch (IOException e) {
            LOG.error("userHTMLForm can't be printed.", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public String loadUserHTMLForm(String fileName) {
        HTMLFormReader htmlFormReader = new HTMLFormReader();
        return htmlFormReader.readHtmlSourceFile(fileName);
    }
}
