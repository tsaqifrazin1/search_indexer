package test.csg.search_indexer.indexing;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpperCaseWordCountRuleTest {

    @Test
    void shouldCountWordsStartingWithUpperCase() {
        UpperCaseWordCountRule rule = new UpperCaseWordCountRule();
        List<String> words = List.of("Hello", "world", "Java", "code");

        String result = rule.apply(words);

        assertEquals("2", result);
    }

    @Test
    void shouldReturnZeroForNoUpperCaseWords() {
        UpperCaseWordCountRule rule = new UpperCaseWordCountRule();
        List<String> words = List.of("hello", "world", "java", "code");

        String result = rule.apply(words);

        assertEquals("0", result);
    }

    @Test
    void shouldIgnoreEmptyStrings() {
        UpperCaseWordCountRule rule = new UpperCaseWordCountRule();
        List<String> words = List.of("", " ", "Hello");

        String result = rule.apply(words);

        assertEquals("1", result);
    }
}

