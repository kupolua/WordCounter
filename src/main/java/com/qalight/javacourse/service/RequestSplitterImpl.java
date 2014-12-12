package com.qalight.javacourse.service;

import com.qalight.javacourse.core.SupportedHttpProtocol;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RequestSplitterImpl implements RequestSplitter {
    @Override
    public Collection<String> getSplitRequests(String userRequest) {
        Assertions.assertStringIsNotNullOrEmpty(userRequest);
        List<String> result = new ArrayList<>();

        boolean isWeb = SupportedHttpProtocol.isWebProtocol(userRequest);
        if (!isWeb) {
            result.add(userRequest);
        } else {
            result.addAll(Arrays.asList(userRequest.split("\\s")));
        }
        result.removeAll(Arrays.asList(""));
        return result;
    }
}
