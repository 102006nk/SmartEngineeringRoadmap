
package com.roadmap.ui.career;

import com.roadmap.model.Career;
import com.roadmap.model.Skill;
import com.roadmap.service.CareerService;
import com.roadmap.ui.MainApp;
import com.roadmap.ui.roadmap.RoadmapView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class CareerView {

    private final CareerService careerService =
            new CareerService();

    private final VBox root = new VBox(20);

    public CareerView() {
        buildUI();
    }

    private void buildUI() {
        Button backButton = new Button("← Back to Profile");

         MainApp.styleButton(backButton, false);

         backButton.setOnAction(event ->
             MainApp.showProfile());

        root.setPadding(new Insets(30));
        root.setStyle(
                "-fx-background-color: "
                + MainApp.BG + ";");

        Label title = new Label(
                "Explore Careers");

        MainApp.styleTitle(title);

        Label subtitle = new Label(
                "Discover paths that match your ambitions.");

        MainApp.styleLabel(subtitle);

        ListView<Career> careerList =
                new ListView<>();

        careerList.setMinHeight(220);

        careerList.setStyle(
                "-fx-background-color: #111C30;"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: #25334A;"
                + "-fx-border-radius: 12;"
                + "-fx-control-inner-background: #111C30;"
                + "-fx-padding: 5;");

        careerList.setCellFactory(list ->
                new ListCell<Career>() {

            @Override
            protected void updateItem(
                    Career career, boolean empty) {

                super.updateItem(career, empty);

                if (empty || career == null) {

                    setText(null);

                } else {

                    setText(
                            career.getCareerName());

                    setStyle(
                            "-fx-text-fill: #F1F5F9;"
                            + "-fx-padding: 12;"
                            + "-fx-font-size: 14px;");
                }
            }
        });

        TextArea descriptionArea =
                new TextArea();

        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPromptText(
                "Select a career to see its description.");

        MainApp.styleField(descriptionArea);

        ListView<String> skillsList =
                new ListView<>();

        skillsList.setPrefHeight(180);

        skillsList.setStyle(
                "-fx-background-color: #111C30;"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: #25334A;"
                + "-fx-border-radius: 12;"
                + "-fx-control-inner-background: #111C30;");

        Label skillsTitle = new Label(
                "Required Skills");

        skillsTitle.setStyle(
                "-fx-text-fill: #F1F5F9;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;");

        Button generateButton =
                new Button("Generate My Roadmap");

        MainApp.styleButton(generateButton, true);

        Label message = new Label();
        MainApp.styleMessage(message);

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
                .addListener((observable,
                        oldValue, newValue) -> {

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
                    MainApp.createScene(
                            new RoadmapView(
                                    selectedCareer
                                    .getCareerName())
                            .getView(),
                            1000, 750));
        });

        VBox leftCard = new VBox(12);
        leftCard.setPadding(new Insets(20));
        MainApp.styleRegion(leftCard);

        Label chooseTitle = new Label(
                "Career Options");

        chooseTitle.setStyle(
                "-fx-text-fill: #F1F5F9;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;");

        leftCard.getChildren().addAll(
                chooseTitle,
                careerList);

        VBox rightCard = new VBox(12);
        rightCard.setPadding(new Insets(20));
        MainApp.styleRegion(rightCard);

        rightCard.getChildren().addAll(
                new Label("Career Overview"),
                descriptionArea,
                skillsTitle,
                skillsList,
                generateButton,
                message);

        ((Label)rightCard.getChildren().get(0))
                .setStyle(
                        "-fx-text-fill: #F1F5F9;"
                        + "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;");

        HBox content = new HBox(20);
        content.setAlignment(Pos.TOP_CENTER);

        leftCard.setPrefWidth(350);
        rightCard.setPrefWidth(550);

        HBox.setHgrow(rightCard,
                Priority.ALWAYS);

        content.getChildren().addAll(
                leftCard,
                rightCard);

        root.getChildren().addAll(
                backButton,
                title,
                subtitle,
                content);
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
