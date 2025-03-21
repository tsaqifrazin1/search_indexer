import org.junit.jupiter.api.*;
import test.csg.search_indexer.SearchIndexer;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class SearchIndexerTest {
    private static final String TEST_DIR = "./data/";
    private static final String TEST_FILE = "testFile.txt";
    private static Path testFilePath;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(Paths.get(TEST_DIR));
        testFilePath = Paths.get(TEST_DIR, TEST_FILE);
        Files.writeString(testFilePath, "Hello World This Is A Test File\nAnother Line With More Words");
    }

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void testValidSearchIndexer() {
        SearchIndexer.main(new String[]{TEST_FILE});

        String output = outContent.toString();

        assertTrue(output.contains("Processed file [testFile.txt] with rule: Uppercase words count and result: 12"));
        assertTrue(output.contains("Processed file [testFile.txt] with rule: Long Word (>5 chars) count and result: [Another]"));

    }

    @Test
    void testInvalidSearchIndexer() {
        SearchIndexer.main(new String[]{"nonexistent.txt"});

        String output = outContent.toString();

        assertTrue(output.contains("Processed file [nonexistent.txt] with rule: Uppercase words count and result: Error processing file: nonexistent.txt - java.nio.file.NoSuchFileException: ./data/nonexistent.txt"));
        assertTrue(output.contains("Processed file [nonexistent.txt] with rule: Long Word (>5 chars) count and result: Error processing file: nonexistent.txt - java.nio.file.NoSuchFileException: ./data/nonexistent.txt"));
    }

    @Test
    void testMultipleSearchIndexer() {
        SearchIndexer.main(new String[]{TEST_FILE, "nonexistent.txt"});

        String output = outContent.toString();

        assertTrue(output.contains("Processed file [testFile.txt] with rule: Uppercase words count and result: 12"));
        assertTrue(output.contains("Processed file [testFile.txt] with rule: Long Word (>5 chars) count and result: [Another]"));
        assertTrue(output.contains("Processed file [nonexistent.txt] with rule: Uppercase words count and result: Error processing file: nonexistent.txt - java.nio.file.NoSuchFileException: ./data/nonexistent.txt"));
        assertTrue(output.contains("Processed file [nonexistent.txt] with rule: Long Word (>5 chars) count and result: Error processing file: nonexistent.txt - java.nio.file.NoSuchFileException: ./data/nonexistent.txt"));
    }
}
