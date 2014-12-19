package info.deepidea.wordcounter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WordCounterImpl implements WordCounter {
    private static final Logger LOG = LoggerFactory.getLogger(WordCounterImpl.class);

    @Override
    public  Map<String, Integer> countWords(List<String> words) {
        if (words == null) {
            String msg = "words is NULL.";
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Map<String, Integer> countedWords = new HashMap<>();
        for (String eachWord : words) {
            Integer foundValue = countedWords.get(eachWord);
            if (foundValue == null) {
                countedWords.put(eachWord, 1);
            } else {
                Integer newCounter = ++foundValue;
                countedWords.put(eachWord, newCounter);
            }
        }
        countedWords.remove("");
        return countedWords;
    }
}
