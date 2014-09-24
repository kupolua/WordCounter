package com.qalight.javacourse.service;

import com.qalight.javacourse.core.SupportedHttpProtocol;
import com.qalight.javacourse.util.Assertions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RequestSplitterImpl implements RequestSplitter {
    @Override
    public Collection<String> getSplitRequests(String userRequest) {
        Assertions.assertStringIsNotNullOrEmpty(userRequest);
        Set<String> result = new HashSet<>();

        boolean isWeb = SupportedHttpProtocol.isWebProtocol(userRequest);
        if (!isWeb) {
            result.add(userRequest);
        } else {
            result.addAll(Arrays.asList(userRequest.split("\\s")));
        }
        result.remove("");
        return result;
    }
}
