package test.csg.search_indexer;

import test.csg.search_indexer.indexing.IndexingEngine;
import test.csg.search_indexer.indexing.IndexingRule;
import test.csg.search_indexer.indexing.LongWordsRule;
import test.csg.search_indexer.indexing.UpperCaseWordCountRule;

import java.util.Arrays;
import java.util.List;

public class SearchIndexer {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage example: java -jar target/search_indexer-1.0-SNAPSHOT.jar <file1> <file2> ...");
            return;
        }

        List<IndexingRule> rules = Arrays.asList(
                new UpperCaseWordCountRule(),
                new LongWordsRule()
        );

        IndexingEngine engine = new IndexingEngine(rules);
        for (String file : args) {
            engine.processFile(file);
        }
    }
}
