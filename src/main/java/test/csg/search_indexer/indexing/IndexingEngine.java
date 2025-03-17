package test.csg.search_indexer.indexing;

import test.csg.search_indexer.utils.FileProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class IndexingEngine {
    private static final String DEFAULT_DIRECTORY = "./data/";

    private final List<IndexingRule> rules;

    public IndexingEngine(List<IndexingRule> rules) {
        this.rules = rules;
    }

    public void processFile(String filePath) {
        try {
            Path fullPath = Paths.get(filePath).isAbsolute()
                    ? Paths.get(filePath)
                    : Paths.get(DEFAULT_DIRECTORY, filePath);

            List<String> words = FileProcessor.readWordsFromFile(fullPath.toString());

            System.out.println("Processing file: " + fullPath);
            for (IndexingRule rule : rules) {
                System.out.println(rule.getTitle() + " : " + rule.apply(words));
            }
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error processing file: " + filePath + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}

