import com.qalight.javacourse.WordCounter;
import com.qalight.javacourse.WordCounterResultSorter;
import com.qalight.javacourse.WordFilter;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by box on 12.06.2014.
 */
public class AllTests {

    @Test
    public void testWordCount() {
        WordCounter wordCounter = new WordCounter();
        // given
        final String text = "one, two, two, three, three, three.";

        // when
        final Map<String, Integer> actualResult = wordCounter.countWords(text);

        // then
        Map<String, Integer> expectedResult = new HashMap<String, Integer>();
        expectedResult.put("one", 1);
        expectedResult.put("two", 2);
        expectedResult.put("three", 3);
        Assert.assertTrue(resultMapsAreEqual(expectedResult, actualResult));
    }

    private boolean resultMapsAreEqual(Map<String, Integer> expectedResult, Map<String, Integer> actualResult) {
        if (expectedResult.size() != actualResult.size()) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : expectedResult.entrySet()) {
            String word = entry.getKey();
            int expectedCount = expectedResult.get(word);
            int actualCount = actualResult.get(word);
            if (expectedCount != actualCount) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testWordFilter(){
        WordFilter wordFilter = new WordFilter();
        final String word = "(Play;Station)";
        final String expectedWord = "PlayStation";
        Assert.assertEquals(expectedWord, wordFilter.filterWord(word));
    }

    @Test
    public void testResultSorter(){
        WordCounterResultSorter resultSorter = new WordCounterResultSorter();
        //given
        Map<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("two", 2);
        hashMap.put("three", 3);
        hashMap.put("one", 1);
        //when
        List<Map.Entry<String, Integer>> actualResult = resultSorter.sortWords(hashMap);
        //then
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("three=3");
        expectedResult.add("two=2");
        expectedResult.add("one=1");
        Assert.assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    public void testUrlValidator(){
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid("http://lan")) {
            System.out.println("url is valid");
        } else {
            System.out.println("url is invalid");
        }
    }

    @Test
    public void showResult() throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/wordsHolder", "root", "");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("select * from bbc_com");
        while (result.next()){
            System.out.println(result.getInt("id") + " - " + result.getString("word"));
        }

        statement.close();
        conn.close();

    }
    @Test
    public void testUrlParsing(){
        WordFilter wordFilter = new WordFilter();
        String url = "habrahabr.ru";
        System.out.println(wordFilter.parseUrlForDb(url));
    }

}
