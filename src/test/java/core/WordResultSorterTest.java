package core;

import com.qalight.javacourse.core.WordResultSorter;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class WordResultSorterTest {

    @Test
    public void testEnumeration() {

        // given
        final Set<String> expectedEnumValues = new TreeSet<String>() {{
            add("KEY_ASCENDING");  // todo use constant
            add("KEY_DESCENDING");
            add("VALUE_ASCENDING");
            add("VALUE_DESCENDING");
        }};

        //when
        Set<String> actualEnumValues = new TreeSet<String>();
        for (WordResultSorter wordResultSorter : WordResultSorter.values()) {
            actualEnumValues.add(wordResultSorter.toString());
        }

        //then
        Assert.assertEquals(expectedEnumValues, actualEnumValues);

    }

    @Test
    public void testGetSortedWords_KeyAsc() {

        //given
        final Map<String, Integer> givenHashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final String expectedResultByKA = "[one=1, three=3, two=2]";

        //when
        List<Map.Entry<String, Integer>> actualResultByKA = WordResultSorter.valueOf("KEY_ASCENDING").getSortedWords(givenHashMap);

        //then
        Assert.assertEquals(expectedResultByKA, actualResultByKA.toString());

    }

    @Test
    public void testGetSortedWords_KeYDesc() {

        //given
        final Map<String, Integer> givenHashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final String expectedResultByKD = "[two=2, three=3, one=1]";

        //when
        List<Map.Entry<String, Integer>> actualResultByKD = WordResultSorter.valueOf("KEY_DESCENDING").getSortedWords(givenHashMap);

        //then
        Assert.assertEquals(expectedResultByKD, actualResultByKD.toString());

    }

    @Test
    public void testGetSortedWords_ValAsc() {

        //given
        final Map<String, Integer> givenHashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final String expectedResultByVA = "[one=1, two=2, three=3]";

        //when
        List<Map.Entry<String, Integer>> actualResultByVA = WordResultSorter.valueOf("VALUE_ASCENDING").getSortedWords(givenHashMap);

        //then
        Assert.assertEquals(expectedResultByVA, actualResultByVA.toString());

    }

    @Test
    public void testGetSortedWords_ValDesc() {

        //given
        final Map<String, Integer> givenHashMap = new HashMap<String, Integer>() {{
            put("three", 3);
            put("one", 1);
            put("two", 2);
        }};

        final String expectedResultByVD = "[three=3, two=2, one=1]";

        //when
        List<Map.Entry<String, Integer>> actualResultByVD = WordResultSorter.valueOf("VALUE_DESCENDING").getSortedWords(givenHashMap);

        //then
        Assert.assertEquals(expectedResultByVD, actualResultByVD.toString());

    }

}