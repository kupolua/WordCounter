package info.deepidea.wordcounter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowserInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(BrowserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String pageForRedirection = "http://whatbrowser.org/";
        final String headerName = "User-Agent";
        final String firstIeFeature = "Trident";
        final String secondIeFeature = "MSIE";

        final String userAgent = request.getHeader(headerName);

        if (userAgent.contains(firstIeFeature) || userAgent.contains(secondIeFeature)) {
            response.sendRedirect(pageForRedirection);
            LOG.info("Request has been intercepted and redirected to " + pageForRedirection);
            return false;
        }

        return true;
    }
}
