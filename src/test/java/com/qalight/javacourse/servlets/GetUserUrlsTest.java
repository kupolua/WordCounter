package com.qalight.javacourse.servlets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetUserUrlsTest {

    @Test
    public void getUserUrlsTest() throws Exception {
        //given
        Server server = new Server(8021);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/webform");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new GetUserUrlsForm()), "/inputUrls");
        context.addServlet(new ServletHolder(new GetUserUrls()), "/GetUserUrls");

        server.start();

    }

    public static class GetUserUrlsForm extends HttpServlet {

        private static final long serialVersionUID = -6154475799000019575L;

        private static final String greeting = "<html>\n" +
                "<body>\n" +
                "<form action=\"GetUserUrls\" method=\"POST\">\n" +
                "    <p>Input url. Comma delimiter [,] <Br>\n" +
                "   <textarea name=\"listUrls\" cols=\"240\" rows=\"13\"></textarea></p>\n" +
                "    <input type=\"submit\" value=\"Submit\" />\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";


        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException,
                IOException {

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(greeting);
        }
        public void doPost(HttpServletRequest request,
                           HttpServletResponse response)
                throws ServletException, IOException {
            doGet(request, response);
        }

    }


}