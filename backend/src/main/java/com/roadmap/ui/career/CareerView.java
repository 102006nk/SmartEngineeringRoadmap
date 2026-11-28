
package com.roadmap.ui.career;

import com.roadmap.model.Career;
import com.roadmap.model.Skill;
import com.roadmap.service.CareerService;
import com.roadmap.ui.MainApp;
import com.roadmap.ui.roadmap.RoadmapView;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class CareerView {

    private final CareerService careerService =
            new CareerService();

    private final VBox root = new VBox(15);

    public CareerView() {
        buildUI();
    }

    private void buildUI() {

        root.setPadding(new Insets(25));

        Label title = new Label("Career Explorer");

        ListView<Career> careerList =
                new ListView<>();

        careerList.setCellFactory(list -> {

            return new ListCell<Career>() {

                @Override
                protected void updateItem(
                        Career career, boolean empty) {

                    super.updateItem(career, empty);

                    if (empty || career == null) {
                        setText(null);
                    } else {
                        setText(career.getCareerName());
                    }
                }
            };
        });

        TextArea descriptionArea =
                new TextArea();

        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);

        ListView<String> skillsList =
                new ListView<>();

        Button generateButton =
                new Button("Generate Roadmap");

        Label message = new Label();

        try {

            careerList.getItems().addAll(
                    careerService.getAllCareers());

        } catch (Exception e) {

            message.setText(
                    "Failed to load careers: "
                    + e.getMessage());
        }

        careerList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (newValue == null) {
                        return;
                    }

                    descriptionArea.setText(
                            newValue.getDescription());

                    try {

                        List<Skill> skills =
                                careerService
                                .getRequiredSkillsForCareer(
                                        newValue.getCareerName());

                        skillsList.getItems().clear();

                        for (Skill skill : skills) {
                            skillsList.getItems().add(
                                    skill.getSkillName());
                        }

                    } catch (Exception e) {

                        message.setText(
                                "Failed to load skills: "
                                + e.getMessage());
                    }
                });

        generateButton.setOnAction(event -> {

            Career selectedCareer =
                    careerList.getSelectionModel()
                    .getSelectedItem();

            if (selectedCareer == null) {

                message.setText(
                        "Please select a career.");
                return;
            }

            MainApp.setScene(
                    new javafx.scene.Scene(
                            new RoadmapView(
                                    selectedCareer.getCareerName())
                            .getView(),
                            800, 600));
        });

        root.getChildren().addAll(
                title,
                careerList,
                descriptionArea,
                new Label("Required Skills"),
                skillsList,
                generateButton,
                message
        );
    }

    public Parent getView() {
        return root;
    }
}
