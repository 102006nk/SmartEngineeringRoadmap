package com.smartengineering.desktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartengineering.desktop.model.DashboardSummaryResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class DashboardApiClient {

    private static final String BASE_URL =
            "http://localhost:8080/api/dashboard";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public DashboardApiClient() {

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.objectMapper = new ObjectMapper();
    }

    // Retrieve dashboard information for a student
    public DashboardSummaryResponse getDashboardSummary(
            long userId) throws IOException, InterruptedException {

        String url = BASE_URL + "/" + userId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "Dashboard API failed. HTTP status: "
                            + response.statusCode()
            );
        }

        return objectMapper.readValue(
                response.body(),
                DashboardSummaryResponse.class
        );
    }
}