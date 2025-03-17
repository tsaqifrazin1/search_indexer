package test.csg.search_indexer.indexing;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LongWordsRuleTest {

    @Test
    void shouldListWordsLongerThanFiveCharacters() {
        LongWordsRule rule = new LongWordsRule();
        List<String> words = List.of("simple", "test", "automation", "code");

        String result = rule.apply(words);

        assertEquals("[simple, automation]", result);
    }

    @Test
    void shouldReturnEmptyForShortWords() {
        LongWordsRule rule = new LongWordsRule();
        List<String> words = List.of("hi", "code", "yes");

        String result = rule.apply(words);

        assertEquals("[]", result);
    }

    @Test
    void shouldIgnoreEmptyStrings() {
        LongWordsRule rule = new LongWordsRule();
        List<String> words = List.of("", " ");

        String result = rule.apply(words);

        assertEquals("[]", result);
    }
}
