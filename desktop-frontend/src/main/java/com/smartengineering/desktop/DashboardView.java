package com.smartengineering.desktop;

import com.smartengineering.desktop.service.DashboardApiService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import com.fasterxml.jackson.databind.JsonNode;

public class DashboardView {

    private static final long TEST_USER_ID = 1L;

    private final BorderPane root;
    private final DashboardApiService apiClient;

    // Dashboard statistics
    private final Label roadmapValue = new Label("Loading...");
    private final Label taskValue = new Label("Loading...");
    private final Label completedValue = new Label("Loading...");
    private final Label resourceValue = new Label("Loading...");

    private final Label progressValue = new Label("Loading...");
    private final ProgressBar overallProgress = new ProgressBar(0);

    private final Label scheduleValue = new Label("Loading...");
    private final Label statusLabel = new Label("Connecting to server...");

    public DashboardView() {

        root = new BorderPane();
        apiClient = new DashboardApiService();

        root.setStyle("-fx-background-color: #F4F6FB;");

        root.setLeft(createSidebar());
        root.setCenter(createMainContent());

        // Load real information for test user ID 1
        loadDashboard(TEST_USER_ID);
    }

    public Parent getView() {
        return root;
    }

    // Left navigation sidebar
    private VBox createSidebar() {

        VBox sidebar = new VBox(25);

        sidebar.setPadding(new Insets(30, 20, 25, 20));
        sidebar.setPrefWidth(230);

        sidebar.setStyle("-fx-background-color: #172554;");

        Label logo = new Label("SMART ROADMAP");

        logo.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 19px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Engineering Student Portal"
        );

        subtitle.setStyle(
                "-fx-text-fill: #A5B4FC;" +
                "-fx-font-size: 11px;"
        );

        VBox branding = new VBox(7, logo, subtitle);

        Label menuTitle = new Label("MAIN MENU");

        menuTitle.setStyle(
                "-fx-text-fill: #94A3B8;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );

        VBox menu = new VBox(18);

        String[] menuItems = {
                "▦   Dashboard",
                "◎   Career Exploration",
                "◇   Learning Roadmap",
                "▤   Learning Resources",
                "▣   Study Schedule",
                "▥   My Progress"
        };

        for (String item : menuItems) {

            Label menuItem = new Label(item);

            menuItem.setStyle(
                    "-fx-text-fill: #E2E8F0;" +
                    "-fx-font-size: 14px;" +
                    "-fx-padding: 10 8 10 8;"
            );

            if (item.contains("Dashboard")) {

                menuItem.setStyle(
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: #334B85;" +
                        "-fx-background-radius: 7;" +
                        "-fx-padding: 10 8 10 8;"
                );
            }

            menu.getChildren().add(menuItem);
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label footer = new Label(
                "Smart Engineering Roadmap\nVersion 1.0"
        );

        footer.setStyle(
                "-fx-text-fill: #94A3B8;" +
                "-fx-font-size: 11px;"
        );

        sidebar.getChildren().addAll(
                branding, menuTitle, menu, spacer, footer
        );

        return sidebar;
    }

    // Main dashboard content
    private VBox createMainContent() {

        VBox content = new VBox(25);

        content.setPadding(new Insets(35));

        Label welcome = new Label(
                "Welcome to Your Dashboard!"
        );

        welcome.setStyle(
                "-fx-font-size: 27px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #172554;"
        );

        Label description = new Label(
                "Plan your learning, manage your time " +
                "and track your progress."
        );

        description.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #64748B;"
        );

        VBox heading = new VBox(8, welcome, description);

        // Statistics cards
        GridPane cards = new GridPane();

        cards.setHgap(15);
        cards.setVgap(15);

        cards.add(createCard(
                "Total Roadmaps",
                roadmapValue,
                "Career learning paths"
        ), 0, 0);

        cards.add(createCard(
                "Study Tasks",
                taskValue,
                "Tasks assigned"
        ), 1, 0);

        cards.add(createCard(
                "Completed Tasks",
                completedValue,
                "Tasks finished"
        ), 2, 0);

        cards.add(createCard(
                "Learning Resources",
                resourceValue,
                "Resources tracked"
        ), 3, 0);

        for (int i = 0; i < 4; i++) {

            javafx.scene.layout.ColumnConstraints column =
                    new javafx.scene.layout.ColumnConstraints();

            column.setPercentWidth(25);
            column.setHgrow(Priority.ALWAYS);

            cards.getColumnConstraints().add(column);
        }

        // Overall progress
        VBox progressBox = new VBox(15);

        progressBox.setPadding(new Insets(22));

        progressBox.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;"
        );

        Label progressTitle = new Label(
                "Overall Learning Progress"
        );

        progressTitle.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #172554;"
        );

        progressValue.setStyle(
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #2563EB;"
        );

        overallProgress.setMaxWidth(
                Double.MAX_VALUE
        );

        overallProgress.setPrefHeight(15);

        progressBox.getChildren().addAll(
                progressTitle,
                progressValue,
                overallProgress
        );

        // Today's schedule information
        VBox scheduleBox = new VBox(15);

        scheduleBox.setPadding(new Insets(22));

        scheduleBox.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;"
        );

        Label scheduleTitle = new Label(
                "Today's Study Schedule"
        );

        scheduleTitle.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #172554;"
        );

        scheduleValue.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #64748B;"
        );

        statusLabel.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #64748B;"
        );

        scheduleBox.getChildren().addAll(
                scheduleTitle,
                scheduleValue,
                statusLabel
        );

        content.getChildren().addAll(
                heading,
                cards,
                progressBox,
                scheduleBox
        );

        return content;
    }

    // Reusable statistics card
    private VBox createCard(
            String title,
            Label valueLabel,
            String description) {

        VBox card = new VBox(12);

        card.setPadding(new Insets(20));
        card.setMinWidth(130);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;"
        );

        Label titleLabel = new Label(title);

        titleLabel.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #64748B;"
        );

        valueLabel.setStyle(
                "-fx-font-size: 27px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #172554;"
        );

        Label descriptionLabel = new Label(description);

        descriptionLabel.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: #94A3B8;"
        );

        card.getChildren().addAll(
                titleLabel,
                valueLabel,
                descriptionLabel
        );

        return card;
    }

    // Fetch dashboard data from Spring Boot
    private void loadDashboard(long userId) {

        // DashboardApiService uses sendAsync, so the request does not block the JavaFX thread.
        apiClient.getDashboardSummary(userId)
                .thenAccept(data -> {

                    Platform.runLater(() -> updateDashboard(data));

                })
                .exceptionally(error -> {

                    Platform.runLater(() -> {
                        roadmapValue.setText("Unavailable");
                        taskValue.setText("Unavailable");
                        completedValue.setText("Unavailable");
                        resourceValue.setText("Unavailable");
                        progressValue.setText("Unavailable");
                        scheduleValue.setText("Study schedule unavailable.");

                        statusLabel.setText(
                                "Dashboard data is unavailable. " +
                                "Please check that the backend is running."
                        );

                        statusLabel.setStyle(
                                "-fx-text-fill: #DC2626;"
                        );

                    });

                    // Keep the failure visible in the console for troubleshooting.
                    error.printStackTrace();

                    return null;
                });
    }

    // Update dashboard using actual API values
    private void updateDashboard(JsonNode data) {

        int totalRoadmaps =
                data.path("totalRoadmaps").asInt(0);

        int totalTasks =
                data.path("totalTasks").asInt(0);

        int completedTasks =
                data.path("completedTasks").asInt(0);

        int totalResources =
                data.path("totalResources").asInt(0);

        int pendingTasks =
                data.path("pendingTasks").asInt(0);

        double progress =
                data.path("overallProgress").asDouble(0);

        roadmapValue.setText(
                String.valueOf(totalRoadmaps)
        );

        taskValue.setText(
                String.valueOf(totalTasks)
        );

        completedValue.setText(
                String.valueOf(completedTasks)
        );

        resourceValue.setText(
                String.valueOf(totalResources)
        );

        progressValue.setText(
                String.format("%.1f%%", progress)
        );

        // ProgressBar accepts values between 0 and 1
        overallProgress.setProgress(
                Math.max(0, Math.min(1, progress / 100.0))
        );

        scheduleValue.setText(
                "Pending study tasks: " + pendingTasks
        );

        statusLabel.setText(
                "Dashboard data loaded successfully."
        );

        statusLabel.setStyle(
                "-fx-text-fill: #16A34A;"
        );
    }
}