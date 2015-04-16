package info.deepidea.wordcounter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowserInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(BrowserInterceptor.class);
    private final String[] incompatibleBrowsers;

    @Autowired
    public BrowserInterceptor(@Value("${list.incompatible.browsers}") String browsersList){
        incompatibleBrowsers = browsersList.split(";");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String pageForRedirection = "https://browser-update.org/update.html";
        final String headerName = "User-Agent";

        final String userAgent = request.getHeader(headerName);
        if (userAgent != null) {
            for (String eachBrowserFeature : incompatibleBrowsers) {
                if (userAgent.contains(eachBrowserFeature)) {
                    response.sendRedirect(pageForRedirection);
                    LOG.info("Request from "+ eachBrowserFeature +" has been intercepted and redirected to " + pageForRedirection);
                    return false;
                }
            }
        }

        return true;
    }
}
