package test.csg.search_indexer.indexing;

import java.util.List;
import java.util.stream.Collectors;

public class LongWordsRule implements IndexingRule {
    private final String title = "Long Word (>5 chars) count";

    @Override
    public String apply(List<String> words) {
        List<String> longWords = words.stream()
                .filter(word -> word.length() > 5)
                .toList();
        return String.valueOf(longWords);
    }

    @Override
    public String getTitle() {
        return title;
    }


}