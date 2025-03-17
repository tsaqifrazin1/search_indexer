package test.csg.search_indexer.indexing;

import java.util.List;

public interface IndexingRule {
    String apply(List<String> words);
    String getTitle();
}

