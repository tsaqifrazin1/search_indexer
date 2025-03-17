package test.csg.search_indexer.indexing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import test.csg.search_indexer.utils.FileProcessor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class IndexingEngineTest {
    private IndexingEngine indexingEngine;
    private IndexingRule mockRule;

    @BeforeEach
    void setUp() {
        mockRule = mock(IndexingRule.class);
        when(mockRule.getTitle()).thenReturn("Mock Rule");
        when(mockRule.apply(anyList())).thenReturn("2");

        indexingEngine = new IndexingEngine(mockRule);
    }

    @Test
    void testProcessFile_ValidFile() throws Exception {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        Files.write(tempFile, List.of("Hello", "World", "Test"));

        try (MockedStatic<FileProcessor> mockedFileProcessor = mockStatic(FileProcessor.class)) {
            mockedFileProcessor.when(() -> FileProcessor.readWordsFromFile(tempFile.toString()))
                    .thenReturn(List.of("Hello", "World", "Test"));

            String result = indexingEngine.processFile(tempFile.toString());

            verify(mockRule).apply(List.of("Hello", "World", "Test"));

            assertEquals("2", result);
        }
    }

    @Test
    void testProcessFile_FileNotFound_AbsoluteFile() {
        String fakeFilePath = "/test/nonexistent.txt";
        String result = indexingEngine.processFile(fakeFilePath);

        assertEquals("Error processing file: /test/nonexistent.txt - java.nio.file.NoSuchFileException: /test/nonexistent.txt", result);
    }

    @Test
    void testProcessFile_FileNotFound_UsesDefaultDirectory() {
        String result = indexingEngine.processFile("sample.txt");

        assertEquals("Error processing file: sample.txt - java.nio.file.NoSuchFileException: ./data/sample.txt", result);
    }
}
