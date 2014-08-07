package com.qalight.javacourse.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qalight.javacourse.StringUrlsParser;
import com.qalight.javacourse.UserRequestRouter;
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
// todo: all servlets should end with Servlet word
// todo: rename servlet classes to meaningful name. all servlets handle requests or load response
public class UserHTMLFormHandler extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHTMLFormLoader.class);
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // todo: refactor. too big method
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String userRequest = request.getParameter("userRequest");
        String sortingParam = request.getParameter("userCheck");

        String typeStatisticResult = request.getParameter("typeStatisticResult");

        // todo: move to separate method
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LOG.error("Can't get writer", e);
            // todo: throw exception here
        }

        setResponseHeaders(response);

        Gson gson = new Gson();
        JsonObject myObj = new JsonObject();
        StringUrlsParser stringUrlsParser = new StringUrlsParser();
        //todo: + mainfuly countryObj class: UserHTMLFormHandler
        UserRequestRouter userRequestRouter = UserRequestRouter.valueOf(typeStatisticResult);

        List<List<Map.Entry<String, Integer>>> countedWords = userRequestRouter.getCountedWords(userRequest, sortingParam);
        JsonElement countedWordsList = gson.toJsonTree(countedWords);
        JsonElement listUsersUrls = gson.toJsonTree(stringUrlsParser.parseUrlList(userRequest));

        myObj.addProperty("success", true);
        myObj.add("response", countedWordsList);
        myObj.add("listUsersUrls", listUsersUrls);
        //todo: + NullPointerExeption try catch UserHTMLFormHandler
        out.println(myObj.toString());

        // todo: close in finally block, otherwise you can face memory leaks
        out.close();

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
