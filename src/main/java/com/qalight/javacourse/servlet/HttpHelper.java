package com.qalight.javacourse.servlet;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by emix on 8/23/14.
 */
public class HttpHelper {

    public static void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setCharacterEncoding("UTF-8");
    }
}
