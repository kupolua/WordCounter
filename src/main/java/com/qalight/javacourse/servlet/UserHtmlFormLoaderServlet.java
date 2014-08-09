package com.qalight.javacourse.servlet;

import com.qalight.javacourse.HtmlFormReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by kpl on 23.07.2014.
 */

public class UserHtmlFormLoaderServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserHtmlFormLoaderServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String INDEX_FILE = "index.html";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String userHTMLForm;
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        userHTMLForm = loadUserHtmlForm(INDEX_FILE);

        PrintWriter writer = null;
        try {
            LOG.info("Printing user html form.");
            writer = response.getWriter();
            writer.println(userHTMLForm);
        } catch (IOException e) {
            String msg = "userHTMLForm can't be printed.";
            LOG.error(msg, e);
            throw new IllegalStateException(msg, e);
        } finally {
            closeWriter(writer);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public String loadUserHtmlForm(String fileName) {
        HtmlFormReader htmlFormReader = new HtmlFormReader();
        return htmlFormReader.readHtmlSourceFile(fileName);
    }

    private void closeWriter(Writer writer){
        if (writer != null){
            try {
                writer.close();
            } catch (IOException e) {
                LOG.error("cannot close writer" , e);
            }
        }
    }
}
