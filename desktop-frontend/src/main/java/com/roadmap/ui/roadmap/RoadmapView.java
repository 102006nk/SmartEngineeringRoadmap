
package com.roadmap.ui.roadmap;

import com.roadmap.model.RoadmapItem;
import com.roadmap.service.RoadmapService;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class RoadmapView {

    private final String careerName;

    private final RoadmapService roadmapService =
            new RoadmapService();

    private final VBox root = new VBox(15);

    private final VBox itemContainer =
            new VBox(12);

    private final Label message = new Label();

    public RoadmapView(String careerName) {
        this.careerName = careerName;
        buildUI();
    }

    private void buildUI() {

        root.setPadding(new Insets(25));

        Label title = new Label(
                careerName + " Roadmap");

        Button generateButton =
                new Button("Generate Roadmap");

        generateButton.setOnAction(event -> {

            try {

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

        root.getChildren().addAll(
                title,
                generateButton,
                message,
                itemContainer
        );
    }

    private void loadItems(int roadmapId) {

        try {

            itemContainer.getChildren().clear();

            List<RoadmapItem> items =
                    roadmapService.getRoadmapItems(
                            roadmapId);

            for (RoadmapItem item : items) {

                VBox card = new VBox(8);

                card.setPadding(
                        new Insets(12));

                Label itemTitle = new Label(
                        item.getSequenceOrder()
                        + ". "
                        + item.getItemTitle());

                Label description = new Label(
                        item.getItemDescription());

                description.setWrapText(true);

                Label date = new Label(
                        "Target date: "
                        + item.getTargetDate());

                ComboBox<String> statusCombo =
                        new ComboBox<>();

                statusCombo.getItems().addAll(
                        "PENDING",
                        "IN_PROGRESS",
                        "COMPLETED");

                statusCombo.setValue(
                        item.getStatus());

                statusCombo.setOnAction(event -> {

                    try {

                        boolean updated =
                                roadmapService.updateItemStatus(
                                        item.getItemId(),
                                        statusCombo.getValue());

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
                        new Label("Status:"),
                        statusCombo
                );

                itemContainer.getChildren().add(card);
            }

        } catch (Exception e) {

            message.setText(
                    "Loading error: "
                    + e.getMessage());
        }
    }

    public Parent getView() {
        return root;
    }
}
