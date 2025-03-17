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
        String errorOutput = errContent.toString();

        assertTrue(output.contains("Uppercase words count : 12"));
        assertTrue(output.contains("Long Word (>5 chars) count : [Another]"));

        assertTrue(errorOutput.isEmpty(), "There should be no errors");
    }

    @Test
    void testInvalidSearchIndexer() {
        SearchIndexer.main(new String[]{"nonexistent.txt"});

        String errorOutput = errContent.toString();

        assertTrue(!errorOutput.isEmpty(), "There should be errors");
        assertTrue(errorOutput.contains("Error processing file: nonexistent.txt - ./data/nonexistent.txt"));
    }

    @Test
    void testMultipleSearchIndexer() {
        SearchIndexer.main(new String[]{TEST_FILE, "nonexistent.txt"});

        String output = outContent.toString();
        String errorOutput = errContent.toString();

        assertTrue(output.contains("Uppercase words count : 12"));
        assertTrue(output.contains("Long Word (>5 chars) count : [Another]"));

        assertTrue(!errorOutput.isEmpty(), "There should be errors");
        assertTrue(errorOutput.contains("Error processing file: nonexistent.txt - ./data/nonexistent.txt"));
    }
}
