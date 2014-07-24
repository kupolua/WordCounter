package com.qalight.javacourse.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qalight.javacourse.Executor;

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
    //test: commit and push
    private static final long serialVersionUID = -6154475799000019575L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userRequest = request.getParameter("userRequest");

        PrintWriter out = response.getWriter();
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
        Executor executor = new Executor();
//        executor.inputUrls(userRequest);
        //getJasonObj

        JsonElement countryObj = gson.toJsonTree(executor.inputUrls(userRequest));
//        JsonElement countryObj = gson.toJsonTree(countryInfo);
//        if(countryInfo.getName() == null){
            myObj.addProperty("success", true);
//        }
//        else {
//            myObj.addProperty("success", true);
//        }
        myObj.add("response", countryObj);
        System.out.println("myObj.toString(): " + myObj.toString());
        out.println(myObj.toString());


        out.close();

    }
}
