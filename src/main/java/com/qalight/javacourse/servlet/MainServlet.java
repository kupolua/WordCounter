package com.qalight.javacourse.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by kpl on 23.07.2014.
 */
public class MainServlet {
    public static void main(String[] args) {
        try {
            Server server = new Server(8021);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/inputForm");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new UserHTMLFormLoader()), "/UserHTMLFormLoader");
            context.addServlet(new ServletHolder(new UserHTMLFormHandler()), "/UserHTMLFormHandler");

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
