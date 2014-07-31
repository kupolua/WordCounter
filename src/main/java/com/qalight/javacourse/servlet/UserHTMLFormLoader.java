package com.qalight.javacourse.servlet;

import com.qalight.javacourse.HtmlFormReader;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String message;
        try {
            message = userHTMLFormLoader("index.html");
        } catch (Exception e) {
            // give full log message
            message = "Дорогой клиент, у тябя вот такая ошибка: " + e.getMessage();
        }

        response.getWriter().println(message);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public String userHTMLFormLoader(String fileName) {
        HtmlFormReader htmlFormReader = new HtmlFormReader();
        return htmlFormReader.readHtmlSourceFile(fileName);
    }
}
