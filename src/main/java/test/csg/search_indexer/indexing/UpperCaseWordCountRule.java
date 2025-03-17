package test.csg.search_indexer.indexing;

import java.util.List;

public class UpperCaseWordCountRule implements IndexingRule {
    private final String title = "Uppercase words count";

    @Override
    public String apply(List<String> words) {
        long count = words.stream()
                .filter(word -> !word.isEmpty() && Character.isUpperCase(word.charAt(0)))
                .count();
        return String.valueOf(count);
    }

    @Override
    public String getTitle() {
        return title;
    }
}

