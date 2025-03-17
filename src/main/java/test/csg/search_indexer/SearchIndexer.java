package test.csg.search_indexer;

import test.csg.search_indexer.indexing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class SearchIndexer {
    private static final int THREAD_POOL_SIZE = 4;
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        if (args.length == 0) {
            System.err.println("Usage example: java -jar target/search_indexer-1.0-SNAPSHOT.jar <file1> <file2> ...");
            return;
        }

        List<IndexingRule> rules = IndexingRuleFactory.loadAllRules();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Callable<String>> tasks = new ArrayList<>();

        for (String file : args) {
            for (IndexingRule rule : rules) {
                tasks.add(() -> {
                    IndexingEngine engine = new IndexingEngine(rule);
                    String result = engine.processFile(file);
                    return "Processed file [" + file + "] with rule: " + rule.getTitle() + " and result: " + result;
                });
            }
        }

        try {
            List<Future<String>> futures = executor.invokeAll(tasks);
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error during processing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
