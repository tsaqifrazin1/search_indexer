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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class IndexingEngineTest {
    private IndexingEngine indexingEngine;
    private IndexingRule mockRule;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));

        mockRule = mock(IndexingRule.class);
        when(mockRule.getTitle()).thenReturn("Mock Rule");
        when(mockRule.apply(anyList())).thenReturn("2");

        indexingEngine = new IndexingEngine(List.of(mockRule));
    }

    @Test
    void testProcessFile_ValidFile() throws Exception {
        outputStream.reset();

        Path tempFile = Files.createTempFile("test-file", ".txt");
        Files.write(tempFile, List.of("Hello", "World", "Test"));

        try (MockedStatic<FileProcessor> mockedFileProcessor = mockStatic(FileProcessor.class)) {
            mockedFileProcessor.when(() -> FileProcessor.readWordsFromFile(tempFile.toString()))
                    .thenReturn(List.of("Hello", "World", "Test"));

            indexingEngine.processFile(tempFile.toString());

            verify(mockRule).apply(List.of("Hello", "World", "Test"));

            String consoleOutput = outputStream.toString().trim();
            assertTrue(consoleOutput.contains("Processing file: " + tempFile.toAbsolutePath()));
            assertTrue(consoleOutput.contains("Mock Rule : 2"));
        }
    }

    @Test
    void testProcessFile_FileNotFound_AbsoluteFile() {
        String fakeFilePath = "/test/nonexistent.txt";
        indexingEngine.processFile(fakeFilePath);

        String consoleOutput = errorStream.toString().trim();
        assertTrue(consoleOutput.contains("Error processing file: /test/nonexistent.txt - /test/nonexistent.txt"));
    }

    @Test
    void testProcessFile_FileNotFound_UsesDefaultDirectory() {
        indexingEngine.processFile("sample.txt");

        String consoleOutput = errorStream.toString().trim();
        String expected = "Error processing file: sample.txt - " + Path.of("./data/sample.txt");
        assertTrue(consoleOutput.contains(expected));
    }
}
