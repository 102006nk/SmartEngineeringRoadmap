
package com.roadmap.ui.roadmap;

import com.roadmap.model.RoadmapItem;
import com.roadmap.service.RoadmapService;
import com.roadmap.ui.MainApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class RoadmapView {

    private final String careerName;

    private final RoadmapService roadmapService =
            new RoadmapService();

    private final VBox root = new VBox(18);

    private final VBox itemContainer =
            new VBox(15);

    private final Label message = new Label();

    public RoadmapView(String careerName) {

        this.careerName = careerName;

        buildUI();
    }

    private void buildUI() {
        Button backButton = new Button("← Back to Careers");

MainApp.styleButton(backButton, false);

backButton.setOnAction(event ->
        MainApp.showCareer());

        root.setPadding(new Insets(30));

        root.setStyle(
                "-fx-background-color: "
                + MainApp.BG + ";");

        Label title = new Label(
                careerName + " Roadmap");

        MainApp.styleTitle(title);

        Label subtitle = new Label(
                "Your personalized learning journey.");

        MainApp.styleLabel(subtitle);

        Button generateButton =
                new Button("Generate Roadmap");

        MainApp.styleButton(generateButton, true);

        MainApp.styleMessage(message);

        generateButton.setOnAction(event -> {

            try {

                itemContainer.getChildren().clear();

                int roadmapId =
                        roadmapService.generateRoadmap(
                                careerName);

                message.setText(
                        "Roadmap generated. ID: "
                        + roadmapId);

                loadItems(roadmapId);

            } catch (Exception e) {

                message.setText(
                        "Generation failed: "
                        + e.getMessage());
            }
        });

        VBox header = new VBox(10);
        header.setPadding(new Insets(25));

        MainApp.styleRegion(header);

        header.getChildren().addAll(
                title,
                subtitle,
                generateButton,
                message);

        root.getChildren().addAll(
                header,
                itemContainer);
    }

    private void loadItems(int roadmapId) {

        try {

            itemContainer.getChildren().clear();

            List<RoadmapItem> items =
                    roadmapService.getRoadmapItems(
                            roadmapId);

            if (items == null || items.isEmpty()) {

                Label empty = new Label(
                        "No roadmap items found.");

                MainApp.styleLabel(empty);

                itemContainer.getChildren()
                        .add(empty);

                return;
            }

            for (RoadmapItem item : items) {

                VBox card = new VBox(12);

                card.setPadding(
                        new Insets(20));

                MainApp.styleRegion(card);

                Label itemTitle = new Label(
                        item.getSequenceOrder()
                        + ". "
                        + item.getItemTitle());

                itemTitle.setStyle(
                        "-fx-text-fill: #F1F5F9;"
                        + "-fx-font-size: 17px;"
                        + "-fx-font-weight: bold;");

                Label description = new Label(
                        item.getItemDescription());

                description.setWrapText(true);

                description.setStyle(
                        "-fx-text-fill: #CBD5E1;"
                        + "-fx-font-size: 14px;");

                Label date = new Label(
                        "Target date: "
                        + item.getTargetDate());

                MainApp.styleLabel(date);

                Label statusLabel = new Label(
                        "Progress Status");

                statusLabel.setStyle(
                        "-fx-text-fill: #F1F5F9;"
                        + "-fx-font-weight: bold;");

                ComboBox<String> statusCombo =
                        new ComboBox<>();

                statusCombo.getItems().addAll(
                        "PENDING",
                        "IN_PROGRESS",
                        "COMPLETED");

                statusCombo.setValue(
                        item.getStatus());

                statusCombo.setMaxWidth(
                        Double.MAX_VALUE);

                statusCombo.setStyle(
                        "-fx-background-color: #1E293B;"
                        + "-fx-background-radius: 10;"
                        + "-fx-border-color: #334155;"
                        + "-fx-border-radius: 10;"
                        + "-fx-padding: 5;"
                        + "-fx-text-fill: white;");

                statusCombo.setOnAction(event -> {

                    try {

                        boolean updated =
                                roadmapService
                                .updateItemStatus(
                                        item.getItemId(),
                                        statusCombo
                                        .getValue());

                        message.setText(
                                updated
                                ? "Status updated."
                                : "Status update failed.");

                    } catch (Exception e) {

                        message.setText(
                                "Update error: "
                                + e.getMessage());
                    }
                });

                card.getChildren().addAll(
                        itemTitle,
                        description,
                        date,
                        statusLabel,
                        statusCombo);

                itemContainer.getChildren()
                        .add(card);
            }

        } catch (Exception e) {

            message.setText(
                    "Loading error: "
                    + e.getMessage());
        }
    }

   public Parent getView() {

    ScrollPane scrollPane = new ScrollPane(root);

    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(false);
    scrollPane.setPannable(true);

    scrollPane.setStyle(
            "-fx-background: #0B1120;"
            + "-fx-background-color: #0B1120;"
            + "-fx-border-color: transparent;");

    return scrollPane;
}
}
