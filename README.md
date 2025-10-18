Text Similarity Spring Boot Application

This project is a performance-efficient Spring Boot application that compares a reference text file (file A) with a pool of other text files in a directory and calculates a similarity score for each file based on shared words.

------------------------------------------------------------

Problem Description

The application reads:
- The path of a reference file (file A)
- The path of a directory containing multiple text files

For each file in the pool, it computes a similarity score that measures how much it matches file A.

Rules:
1. Only alphabetic chunks are considered words.
2. Word order does not matter.
3. Comparison is case-insensitive.
4. A perfect match (same words) → 100%.
5. No common words → 0%.
6. Supports up to 20 files, each up to 10 million words.

------------------------------------------------------------

Technologies Used
- Java 17+
- Spring Boot 3
- Maven
- Lombok (optional)
- JUnit 5 for testing

------------------------------------------------------------

Project Structure

src/main/java/com/example/textsimilarity/
    TextSimilarityApplication.java
    config/AppProperties.java
    controller/SimilarityController.java
    service/SimilarityService.java
    util/TextUtils.java
    model/ApiResponse.java
    model/FileScore.java

src/main/resources/
    application.properties
    data/fileA.txt
    data/pool/file1.txt
    data/pool/file2.txt
    data/pool/file3.txt

------------------------------------------------------------

Configuration

Edit the file src/main/resources/application.properties:

files.fileAPath=src/main/resources/data/fileA.txt
files.poolPath=src/main/resources/data/pool

------------------------------------------------------------

API Endpoint

GET /api/similarity

Returns similarity results for all files in the pool compared to file A.

Example Successful Response:
{
  "statusCode": 200,
  "success": true,
  "message": "Similarity calculated successfully",
  "size": 3,
  "entity": [
    { "fileName": "file1.txt", "score": 100.0 },
    { "fileName": "file3.txt", "score": 71.43 },
    { "fileName": "file2.txt", "score": 42.86 }
  ]
}

Example Error Response:
{
  "statusCode": 500,
  "success": false,
  "message": "Error calculating similarity: <details>",
  "size": 0,
  "entity": []
}

------------------------------------------------------------

How It Works

1. Extract words
   - Reads each file line by line.
   - Keeps only alphabetic words using regex [A-Za-z]+.
   - Converts to lowercase and stores unique words in a Set.

2. Compute similarity
   score = (number of common words / total words in file A) * 100
   Rounded to two decimals.

3. Return result
   The result is wrapped in an ApiResponse object with metadata (status, message, size, and data).

------------------------------------------------------------

Run the Application

Build:
mvn clean package

Run:
mvn spring-boot:run

Access API:
http://localhost:8080/api/similarity

------------------------------------------------------------

Example Data

fileA.txt:
The quick brown fox jumps over the lazy dog

file1.txt:
The quick brown fox jumps over the lazy dog

file2.txt:
The lazy dog sleeps all day

file3.txt:
A fast brown fox leaps over a sleepy dog

------------------------------------------------------------

Performance Notes
- Uses Java Streams and HashSets (O(n) lookup)
- Efficient for large files (up to 10M words)
- Processes files line-by-line to avoid memory overload
