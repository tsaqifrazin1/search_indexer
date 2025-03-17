package test.csg.search_indexer.indexing;

import org.reflections.Reflections;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IndexingRuleFactory {
    public static List<IndexingRule> loadAllRules() {
        Reflections reflections = new Reflections("test.csg.search_indexer.indexing");
        Set<Class<? extends IndexingRule>> classes = reflections.getSubTypesOf(IndexingRule.class);
        return classes.stream().map(cls -> {
            try {
                return cls.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate " + cls.getName(), e);
            }
        }).collect(Collectors.toList());
    }
}
