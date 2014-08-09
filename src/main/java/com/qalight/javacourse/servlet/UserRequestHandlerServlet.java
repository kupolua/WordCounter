package com.qalight.javacourse.servlet;

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
public class UserRequestHandlerServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHtmlFormLoaderServlet.class);
    private static final long serialVersionUID = 1L;
    //todo: try throws ServletException, IOException -> try
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

        try {
            UserRequestRouter userRequestRouter = new UserRequestRouter();
            System.out.println("userRequestRouter.getResponse(request).toString(): " + userRequestRouter.getResponse(request).toString());
            out.println(userRequestRouter.getResponse(request).toString());
        } catch (NullPointerException npeUserRequestRouter) {
            LOG.error("No data to show", npeUserRequestRouter);
        } finally {
            try {
                out.close();
            } catch (NullPointerException npeOutClose) {
                LOG.error("Can't close connection", npeOutClose);
            }
        }
    }
}
