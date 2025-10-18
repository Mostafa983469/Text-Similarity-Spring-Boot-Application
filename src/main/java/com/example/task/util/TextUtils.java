package com.example.task.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextUtils {
    private static final Pattern WORD_PATTERN = Pattern.compile("[A-Za-z]+");

    public static Set<String> extractWords(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .flatMap(line -> WORD_PATTERN.matcher(line).results()
                            .map(m -> m.group().toLowerCase()))
                    .collect(Collectors.toSet());
        }
    }

    public static double calculateSimilarity(Set<String> a, Set<String> b) {
        if (a.isEmpty() && b.isEmpty()) return 100.0;
        if (a.isEmpty() || b.isEmpty()) return 0.0;

        long intersection = a.stream().filter(b::contains).count();
        double score = ((double) intersection / a.size()) * 100.0;
        return Math.round(score * 100.0) / 100.0;
    }
}
