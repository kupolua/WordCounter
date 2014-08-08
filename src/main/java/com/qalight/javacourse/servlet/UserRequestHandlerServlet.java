package com.qalight.javacourse.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qalight.javacourse.StringUrlsParser;
import com.qalight.javacourse.UserRequestRouter;
import com.qalight.javacourse.WordsSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by kpl on 23.07.2014.
 */
// todo: 90+ all servlets should end with Servlet word
// todo: 91+ rename servlet classes to meaningful name. all servlets handle requests or load response
public class UserRequestHandlerServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHtmlFormLoaderServlet.class);
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        setResponseHeaders(response);
        getResponseWriter(request, response);

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

    private void getResponseWriter(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LOG.error("Can't get writer", e);
            // todo: throw exception here
        }
        String userRequest = request.getParameter("userRequest");
        String sortingParamString = request.getParameter("userCheck");
        WordsSorter sortingParam;
        try {
            sortingParam = WordsSorter.valueOf(sortingParamString);
        } catch (IllegalArgumentException e) {
            // todo: Print message to user form
            throw new RuntimeException("Invalid sorting parameter: " + sortingParamString);
            // todo: Add message to log
            // todo: Stop code execute
        }
        String typeStatisticResult = request.getParameter("typeStatisticResult");


        UserRequestRouter userRequestRouter = UserRequestRouter.valueOf(typeStatisticResult);
        List<List<Map.Entry<String, Integer>>> countedWords = userRequestRouter.getCountedWords(userRequest, sortingParamString);

        Gson gson = new Gson();
        JsonElement countedWordsList = gson.toJsonTree(countedWords);

        StringUrlsParser stringUrlsParser = new StringUrlsParser();
        JsonElement listUsersUrls = gson.toJsonTree(stringUrlsParser.parseUrlList(userRequest));

        JsonObject myObj = new JsonObject();
        myObj.addProperty("success", true);
        myObj.add("response", countedWordsList);
        myObj.add("listUsersUrls", listUsersUrls);

        try {
            out.println(myObj.toString());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } finally {
            out.close();
        }
    }
}
