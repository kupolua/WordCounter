package com.qalight.javacourse.servlet;

import com.qalight.javacourse.service.WordCounterService;
import com.qalight.javacourse.service.WordCounterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kpl on 23.07.2014.
 */
public class UserRequestHandlerServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHtmlFormLoaderServlet.class);
    private static final long serialVersionUID = 1L;

    private final WordCounterService wordCounterService;

    public UserRequestHandlerServlet() {
        wordCounterService = new WordCounterServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setResponseHeaders(response);
        response(request, response);
    }

    private void response (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dataSources = request.getParameter("userRequest");
        String sortingParam = request.getParameter("userChoice");
//        String getTypeStatisticResult = request.getParameter("getTypeStatisticResult");

        try (PrintWriter out = response.getWriter()) {

            String result = wordCounterService.getWordCounterResult (dataSources, sortingParam);

            out.println(result);
        }
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
    }
}
