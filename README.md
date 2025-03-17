/*************  ✨ Codeium Command 🌟  *************/
# Search Indexer

The Search Indexer is a Java-based application designed to process text files and apply a set of indexing rules to analyze the content. The application can be run via the command line and processes each file specified in the command-line arguments.

## Features

- **`UpperCaseWordCountRule`**: Counts words starting with an uppercase letter.
- **`LongWordsRule`**: Lists words longer than five characters.

## Components

- **`IndexingEngine`**: Responsible for processing files and applying indexing rules.
- **`FileProcessor`**: Utility class for reading words from files.
- **`IndexingRule`**: Interface for defining different indexing rules.
- **`IndexingRuleFactory`**: Creates instances of rules implementing the `IndexingRule` interface using Reflections.
- **`UpperCaseWordCountRule`**: Counts words starting with an uppercase letter.
- **`LongWordsRule`**: Lists words longer than five characters.

## Build

The project uses Maven for building and managing dependencies. To build the project, run:

```bash
mvn clean package
```

## Usage

Before running the application, ensure you have built the project, and then execute:

```bash
java -jar target/search_indexer-1.0-SNAPSHOT.jar <file1> <file2> ...
```

Replace `<file1> <file2> ...` with the paths to the text files you want to process.

If you do not use an absolute path, the application will default to using the `./data` folder.

## Testing

The project includes tests for validating the functionality of different components:

- **`IndexingEngineTest`**: Tests file processing and rule application.
- **`UpperCaseWordCountRuleTest`** and **`LongWordsRuleTest`**: Tests for specific rules.
- **`SearchIndexerTest`**: Integration test for the main application workflow and error scenarios.

## Adding a New Indexing Rule

To add a new indexing rule to the Search Indexer, follow these steps:

1. **Create a New Rule Class**:
   - Implement the `IndexingRule` interface in a new class.
   - Define the logic for your rule in the `apply` method, which takes a list of words and returns a result as a string.
   - Implement the `getTitle` method to return a descriptive title for your rule.

   ```java
   public class CustomRule implements IndexingRule {
       private final String title = "Custom Rule Description";

       @Override
       public String apply(List<String> words) {
           // Implement your logic here
           return "Custom Result";
       }

       @Override
       public String getTitle() {
           return title;
       }
   }
   ```

2. **Build and Run**:
   - Build the project using Maven and run the application with the desired text files as arguments to see your new rule in action.

    ```bash
    # Example command to run the Search Indexer on sample files
    
    mvn clean package

    java -jar target/search_indexer-1.0-SNAPSHOT.jar data/file1.txt data/file2.html
    ```

## Requirements

- Java 17
- Maven


/******  e9c46b64-4f29-4af4-b5a8-0a77b995b58e  *******/