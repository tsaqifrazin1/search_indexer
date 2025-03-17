package test.csg.search_indexer.indexing;

import test.csg.search_indexer.utils.FileProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class IndexingEngine {
    private static final String DEFAULT_DIRECTORY = "./data/";

    private final IndexingRule rule;

    public IndexingEngine(IndexingRule rule) {
        this.rule = rule;
    }

    public String processFile(String filePath) {
        String result;
        try {
            Path fullPath = Paths.get(filePath).isAbsolute()
                    ? Paths.get(filePath)
                    : Paths.get(DEFAULT_DIRECTORY, filePath);

            List<String> words = FileProcessor.readWordsFromFile(fullPath.toString());

            result = rule.apply(words);
        } catch (Exception e) {
            result = "Error processing file: " + filePath + " - " + e.getMessage();
        }

        return result;

    }
}

