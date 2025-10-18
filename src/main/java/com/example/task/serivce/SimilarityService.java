package com.example.task.serivce;

import com.example.task.config.AppProperties;
import com.example.task.model.ApiResponse;
import com.example.task.model.FileScore;
import com.example.task.util.TextUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SimilarityService {

    private final AppProperties appProperties;

    public SimilarityService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public ApiResponse<List<FileScore>> calculateScores() {
        try {
            Path fileAPath = Paths.get(appProperties.getFileAPath());
            Path poolDir = Paths.get(appProperties.getPoolPath());

            Set<String> baseWords = TextUtils.extractWords(fileAPath);

            List<FileScore> results;
            try (Stream<Path> files = Files.list(poolDir)) {
                results = files
                        .filter(Files::isRegularFile)
                        .map(path -> {
                            try {
                                Set<String> otherWords = TextUtils.extractWords(path);
                                double score = TextUtils.calculateSimilarity(baseWords, otherWords);
                                return new FileScore(path.getFileName().toString(), score);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return new FileScore(path.getFileName().toString(), 0.0);
                            }
                        })
                        .sorted(Comparator.comparingDouble(FileScore::getScore).reversed())
                        .collect(Collectors.toList());
            }

            return new ApiResponse<>(
                    200,
                    true,
                    "Similarity calculated successfully",
                    results.size(),
                    results
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(
                    500,
                    false,
                    "Error calculating similarity: " + e.getMessage(),
                    0,
                    Collections.emptyList()
            );
        }
    }
}
