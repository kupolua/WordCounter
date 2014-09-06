package com.qalight.javacourse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pavelkulakovsky on 27.08.14.
 */
@Component
public class ResultPresentationImpl {
    private static final Logger LOG = LoggerFactory.getLogger(ResultPresentationImpl.class);

    private static Set<ResultPresentation> resultPresentations;
    static {
        resultPresentations = new HashSet<>();
        resultPresentations.add(new JsonResultPresentation());
    }
    public ResultPresentation getResultPresentation(String dataTypeResponse) {

       checkForNullOrEmpty(dataTypeResponse);

        ResultPresentation resultPresentation = null;
        for (ResultPresentation currentResultPresentation : resultPresentations) {
            if (currentResultPresentation.isEligible(dataTypeResponse)) {
                resultPresentation = currentResultPresentation;
                break;
            }
        }
        if(resultPresentation == null){
            LOG.warn("Cannot recognize a response type (" + dataTypeResponse + ")");
            throw new RuntimeException("Cannot recognize a response type (" + dataTypeResponse + ") in order to select appropriate result type.");
        }
        return resultPresentation;
    }

    private void checkForNullOrEmpty(String dataTypeResponse) {
        if (dataTypeResponse == null) {
            LOG.warn("\"dataTypeResponse\" parameter is NULL.");
            throw new IllegalArgumentException("Cannot recognize a response type. The link is null.");
        }

        if (dataTypeResponse.equals("")) {
            LOG.warn("\"dataTypeResponse\" parameter is empty.");
            throw new IllegalArgumentException("Cannot recognize a response type. The link is empty." );
        }
    }
}
