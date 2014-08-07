package com.qalight.javacourse;

import com.qalight.javacourse.servlet.UserHTMLFormHandler;
import com.qalight.javacourse.servlet.UserHTMLFormLoader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kpl on 23.07.2014.
 */
public class EntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(EntryPoint.class);
    public static void main(String[] args) {
        try {
            LOG.debug("Starting Jetty server!!!");
            Server server = new Server(8021);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/inputForm");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new UserHTMLFormLoader()), "/UserHTMLFormLoader");
            context.addServlet(new ServletHolder(new UserHTMLFormHandler()), "/UserHTMLFormHandler");

            server.start();
            LOG.info("Jetty server has started.");
        } catch (Exception e) {
            LOG.error("Can't start embedded Jetty server.", e);
        }
    }
}
