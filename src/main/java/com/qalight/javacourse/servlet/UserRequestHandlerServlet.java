package com.qalight.javacourse.servlet;

import com.qalight.javacourse.service.ResultPresentation;
import com.qalight.javacourse.service.WordCounterService;
import com.qalight.javacourse.service.WordCounterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserRequestHandlerServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserRequestHandlerServlet.class);
    private static final long serialVersionUID = 1L;

    private final WordCounterService wordCounterService;

    public UserRequestHandlerServlet() {
        wordCounterService = new WordCounterServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHelper.setResponseHeaders(response);
        response(request, response);
    }

    private void response (HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String dataSources = request.getParameter("userRequest");
        final String sortingParam = request.getParameter("userChoice");
        final String dataTypeResponse = request.getParameter("dataTypeResponse");

        final String result = getResultAndCatchException(dataSources, sortingParam, dataTypeResponse);

        try (PrintWriter out = response.getWriter()) {
            LOG.debug("Showing response result: " + result);
            out.println(result);
        }
    }

    private String getResultAndCatchException(String dataSources,  String sortingParam, String dataTypeResponse){
        String result;
        try{
            result = wordCounterService.getWordCounterResult(dataSources, sortingParam, dataTypeResponse);
        } catch (Throwable e){
            LOG.error("error while processing request: " + e.getMessage(), e);
            result = ResultPresentation.valueOf(dataTypeResponse).createErrorResponse(e.getMessage());
        }
        return result;
    }
}
