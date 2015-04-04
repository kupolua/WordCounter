package info.deepidea.wordcounter.crawler;

        import info.deepidea.wordcounter.service.ThreadResultContainer;

public interface Crawler {
    java.util.List<ThreadResultContainer> crawl();
}
