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

/**
 * Created by kpl on 23.07.2014.
 */
public class UserHTMLFormHandler extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHTMLFormLoader.class);
    private static final long serialVersionUID = -6154475799000019575L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userRequest = request.getParameter("userRequest");
        String sortingParam = request.getParameter("userCheck");

        String typeStatisticResult = request.getParameter("typeStatisticResult");

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LOG.error("Can't get writer", e);
        }
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        Gson gson = new Gson();
        JsonObject myObj = new JsonObject();
        StringUrlsParser stringUrlsParser = new StringUrlsParser();
        //todo: + mainfuly countryObj UserHTMLFormHandler
        JsonElement countedWordsList = gson.toJsonTree(UserRequestRouter.valueOf(typeStatisticResult).getCountedWords(userRequest, sortingParam));
        JsonElement listUsersUrls = gson.toJsonTree(stringUrlsParser.parseUrslList(userRequest));

        myObj.addProperty("success", true);
        myObj.add("response", countedWordsList);
        myObj.add("listUsersUrls", listUsersUrls);
        //todo: + NullPointerExeption try catch UserHTMLFormHandler
        out.println(myObj.toString());

        out.close();

    }
}
