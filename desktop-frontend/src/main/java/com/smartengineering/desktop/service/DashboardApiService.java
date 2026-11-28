package com.smartengineering.desktop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DashboardApiService {

    private static final String BASE_URL = "http://localhost:8080/api/dashboard";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public DashboardApiService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<JsonNode> getDashboardSummary(long userId) {
        return get("/" + userId, DashboardSummary.class)
                .thenApply(summary -> objectMapper.<JsonNode>valueToTree(summary));
    }

    public CompletableFuture<ProgressSummary> getRoadmapProgress(long userId) {
        return get("/" + userId + "/roadmap", ProgressSummary.class);
    }

    public CompletableFuture<ProgressSummary> getTaskProgress(long userId) {
        return get("/" + userId + "/tasks", ProgressSummary.class);
    }

    public CompletableFuture<ProgressSummary> getResourceProgress(long userId) {
        return get("/" + userId + "/resources", ProgressSummary.class);
    }

    private <T> CompletableFuture<T> get(String endpoint, Class<T> responseType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .timeout(REQUEST_TIMEOUT)
                .header("Accept", "application/json")
                .GET()
                .build();

        return httpClient.sendAsync(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                )
                .thenApply(response -> {
                    if (response.statusCode() < 200 || response.statusCode() >= 300) {
                        throw new CompletionException(new IOException(
                                "Dashboard API request failed. HTTP status: "
                                        + response.statusCode()
                        ));
                    }

                    try {
                        return objectMapper.readValue(response.body(), responseType);
                    } catch (IOException exception) {
                        throw new CompletionException(exception);
                    }
                });
    }

    public record DashboardSummary(
            int totalRoadmaps,
            int completedRoadmaps,
            int totalTasks,
            int completedTasks,
            int pendingTasks,
            int totalResources,
            int completedResources,
            double overallProgress
    ) {
    }

    public record ProgressSummary(
            int completedItems,
            double completionPercentage,
            int pendingItems,
            int totalItems
    ) {
    }
}
