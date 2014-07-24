package com.qalight.javacourse.servlet;

import com.qalight.javacourse.ReadFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kpl on 23.07.2014.
 */
public class UserHTMLFormLoader extends HttpServlet {

    private static final long serialVersionUID = -6154475799000019575L;

    protected void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException,
        IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(new UserHTMLFormLoader().userHTMLFormLoader("index.html"));
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public String userHTMLFormLoader (String fileName) {
        ReadFile readFile = new ReadFile();
        return readFile.readFile(fileName);
    }
}
