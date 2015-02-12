package info.deepidea.wordcounter.crawler;

import java.util.List;

public interface ParsingProcessor {
    List<String> parsePage(String url);
}
