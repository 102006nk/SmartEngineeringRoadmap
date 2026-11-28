
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

        ListView<String> roadmapList =
                new ListView<>();

        Label message = new Label();

        generateButton.setOnAction(event -> {

            try {

                int roadmapId =
                        roadmapService.generateRoadmap(
                                careerName);

                List<RoadmapItem> items =
                        roadmapService.getRoadmapItems(
                                roadmapId);

                roadmapList.getItems().clear();

                for (RoadmapItem item : items) {

                    roadmapList.getItems().add(
                            item.getSequenceOrder()
                            + ". "
                            + item.getItemTitle()
                            + " ["
                            + item.getStatus()
                            + "]");
                }

                message.setText(
                        "Roadmap generated successfully. "
                        + "ID: " + roadmapId);

            } catch (Exception e) {

                message.setText(
                        "Roadmap generation failed: "
                        + e.getMessage());
            }
        });

        root.getChildren().addAll(
                title,
                generateButton,
                roadmapList,
                message
        );
    }

    public Parent getView() {
        return root;
    }
}
