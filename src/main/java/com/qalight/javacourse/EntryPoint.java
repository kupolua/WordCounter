package com.qalight.javacourse;

import com.qalight.javacourse.service.WordCounterServiceImpl;
import com.qalight.javacourse.servlet.UserHtmlFormLoaderServlet;
import com.qalight.javacourse.servlet.UserRequestHandlerServlet;
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
    public static final int JETTY_PORT = 8021;

    public static void main(String[] args) {
        WordCounterServiceImpl wordCounterService = new WordCounterServiceImpl();
        System.out.println("Return response to user(json type):"
                + wordCounterService.getWordCounterResult("http://www.eslfast.com/supereasy/se/supereasy006.htm", "KEY_ASCENDING"));

    }


//    public static void main(String[] args) {
//        new EntryPoint().jettyStart();
//    }

    public void jettyStart() {
        try {
            LOG.debug("Starting Jetty server!!!");

            Server server = new Server(JETTY_PORT);

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
}
