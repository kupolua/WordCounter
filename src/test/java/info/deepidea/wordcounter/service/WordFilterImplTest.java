package info.deepidea.wordcounter.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WordFilterImplTest {
    private static final boolean isFilterRequired = true;

    @Test
    public void removeUnimportantWords_removeOneWordEng() {
        // given
        final WordFilter filter = new WordFilterImpl("the", "", "");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("world", 1);
            put("the", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, isFilterRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_filterIsNotRequired() {
        // given
        final boolean filterIsNotRequired = false;
        final WordFilter filter = new WordFilterImpl("the", "", "");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("world", 1);
            put("the", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){
            {
                put("world", 1);
                put("the", 1);
                put("love", 1);
            }};
        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, filterIsNotRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeOneWordRu() {
        // given
        final WordFilter filter = new WordFilterImpl("", "и", "");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("мир", 1);
            put("и", 1);
            put("любовь", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("мир", 1);
            put("любовь", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, isFilterRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeOneWordUa() {
        // given
        final WordFilter filter = new WordFilterImpl("", "", "і");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("і", 1);
            put("любов", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, isFilterRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeWordsInDifferentLanguages() {
        // given
        final WordFilter filter = new WordFilterImpl("the", "и", "і");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("і", 1);
            put("любов", 1);
            put("мир", 1);
            put("и", 1);
            put("любовь", 1);
            put("world", 1);
            put("the", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, isFilterRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeVarietyWordsInDifferentLanguages() {
        // given
        final WordFilter filter = new WordFilterImpl("the your they with", "и что его на", "і він їх лише");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("і", 1);
            put("любов", 1);
            put("мир", 1);
            put("и", 1);
            put("любовь", 1);
            put("world", 1);
            put("the", 1);
            put("love", 1);
            put("він", 1);
            put("їх", 1);
            put("лише", 1);
            put("что", 1);
            put("его", 1);
            put("на", 1);
            put("your", 1);
            put("they", 1);
            put("with", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, isFilterRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void removeUnimportantWords_removeWordsFromCleanRefinedList() {
        // given
        final WordFilter filter = new WordFilterImpl("the your they with", "и что его на", "і він їх лише");
        final Map<String, Integer> refinedWords = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};
        final Map<String, Integer> expectedResult = new HashMap<String, Integer>(){{
            put("світ", 1);
            put("любов", 1);
            put("мир", 1);
            put("любовь", 1);
            put("world", 1);
            put("love", 1);
        }};

        // when
        final Map<String, Integer> actualResult = filter.removeUnimportantWords(refinedWords, isFilterRequired);

        // then
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUnimportantWords_null() {
        //given
        final WordFilter filter = new WordFilterImpl("the your they with", "и что его на", "і він їх лише");
        final Map<String, Integer> countedWords = null;

        //when
        filter.removeUnimportantWords(countedWords, isFilterRequired);

        //then
        //expected exception
    }
}
