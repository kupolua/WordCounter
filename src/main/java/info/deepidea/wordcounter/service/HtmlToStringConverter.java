package info.deepidea.wordcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

@Component("html")
public class HtmlToStringConverter implements DocumentToStringConverter {
    private static final Pattern ANY_HOST_FILTER = Pattern.compile("^http(s)?://(www\\.)?[\\w\\d\\W&&[^#]]+$");
    private static final Pattern BINARY_TYPES = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
                                                                + "|png|tiff?|mid|mp2|mp3|mp4"
                                                                + "|wav|avi|mov|mpeg|ram|m4v|pdf"
                                                                + "|rm|svg|wmv|swf|wma|zip|rar|gz))$");
    private ParsingProcessor processor;

    @Autowired
    public HtmlToStringConverter(ParsingProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean isEligible(TextType documentType) {
        boolean isEligible = false;
        if (documentType instanceof HtmlTextTypeImpl) {
            isEligible = true;
        }

        return isEligible;
    }

    @Override
    public ConvertedDataContainer convertToString(RequestContainer userRequest) {
        final ConvertedDataContainer container = processor.parsePage(userRequest);

        if (userRequest.isCrawlingRequired()) {
            addRelatedLinksToContainer(userRequest, container);
        }

        return container;
    }

    private void addRelatedLinksToContainer(RequestContainer userRequest, ConvertedDataContainer container) {
        final Map<String, Set<String>> relatedLinks = new HashMap<>(1);
        final Set<String> filteredUrls = filterUrls(userRequest, container);
        relatedLinks.put(userRequest.getClientRequest(), filteredUrls);
        container.setRelatedLinks(relatedLinks);
    }

    private Set<String> filterUrls(RequestContainer userRequest, ConvertedDataContainer container) {
        final Set<String> processedUrls;

        if (userRequest.isInternalOnly()) {
            processedUrls = getInternalUrls(container.getSourceName(), container.getRawUrls());
        } else {
            processedUrls = getAnyUrls(container.getRawUrls());
        }

        return processedUrls;
    }

    private Set<String> getInternalUrls(String url, Set<String> rawUrls) {
        final String host = getHost(url);
        final Set<String> internalUrls = new HashSet<String>();
        final String pattern = "^http(s)?://(www\\.)?" + host + "[\\w\\d\\W&&[^#]]+$";

        for (String each : rawUrls) {
            if (Pattern.matches(pattern, each)
                    && !BINARY_TYPES.matcher(each.toLowerCase()).matches()) {
                internalUrls.add(each);
            }
        }
        return internalUrls;
    }

    private Set<String> getAnyUrls(Set<String> rawUrls) {
        final Set<String> anyUrls = new HashSet<String>();

        for (String each : rawUrls) {
            if (ANY_HOST_FILTER.matcher(each).matches()
                    && !BINARY_TYPES.matcher(each.toLowerCase()).matches()) {
                anyUrls.add(each);
            }
        }
        return anyUrls;
    }

    private String getHost(String url) {
        final String host;
        try {
            host = new URL(url).getHost();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create a URL object from " + url, e);
        }
        return host;
    }
}
