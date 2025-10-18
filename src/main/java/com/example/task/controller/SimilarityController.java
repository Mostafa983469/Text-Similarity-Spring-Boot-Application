package com.example.task.controller;

import com.example.task.model.ApiResponse;
import com.example.task.model.FileScore;
import com.example.task.serivce.SimilarityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/similarity")
public class SimilarityController {

    private final SimilarityService similarityService;

    public SimilarityController(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FileScore>>> getSimilarityScores() throws IOException {
        try {
            return ResponseEntity.ok(similarityService.calculateScores());
        } catch (Exception | Error e) {
            throw new EOFException(e.getLocalizedMessage());
        }
    }
}
