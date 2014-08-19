package com.qalight.javacourse;

import com.qalight.javacourse.servlet.UserHtmlFormLoaderServlet;
import com.qalight.javacourse.servlet.UserRequestHandlerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(EntryPoint.class);
    public static final int JETTY_PORT = 8021;
    private Server server;

    public static void main(String[] args) {

        new EntryPoint().jettyStart(JETTY_PORT);
    }

    public void jettyStart(int port) {
        try {
            LOG.debug("Starting Jetty server!!!");

            server = new Server(port);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/inputForm");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new UserHtmlFormLoaderServlet()), "/UserHtmlFormLoaderServlet");
            context.addServlet(new ServletHolder(new UserRequestHandlerServlet()), "/UserRequestHandlerServlet");

            server.start();
        } catch (Throwable e) {
            LOG.error("Exception during starting server ", e);
            throw new RuntimeException(e);
        }
        LOG.info("Jetty server has started.");
    }

    public void jettyStop() {
        try {
            server.stop();
        } catch (Exception e) {
            LOG.error("Exception during stopping server ", e);
        }
    }
}
