
package com.roadmap.ui.profile;

import com.roadmap.model.StudentProfile;
import com.roadmap.service.StudentProfileService;
import com.roadmap.ui.MainApp;
import com.roadmap.ui.career.CareerView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ProfileView {

    private final StudentProfileService profileService =
            new StudentProfileService();

    private final VBox root = new VBox(12);

    public ProfileView() {
        buildUI();
    }

    private void buildUI() {

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Student Profile");

        TextField branchIdField = new TextField();
        branchIdField.setPromptText("Branch ID");

        TextField semesterIdField = new TextField();
        semesterIdField.setPromptText("Semester ID");

        TextField interestsField = new TextField();
        interestsField.setPromptText("Interests");

        TextField existingSkillsField = new TextField();
        existingSkillsField.setPromptText("Existing skills");

        TextField careerGoalField = new TextField();
        careerGoalField.setPromptText("Career goal");

        TextField studyHoursField = new TextField();
        studyHoursField.setPromptText("Daily study hours");

        Button saveButton = new Button("Save Profile");

        Button careerButton =
                new Button("Explore Careers");

        Label message = new Label();

        saveButton.setOnAction(event -> {

            try {

                int branchId = Integer.parseInt(
                        branchIdField.getText());

                int semesterId = Integer.parseInt(
                        semesterIdField.getText());

                double hours = Double.parseDouble(
                        studyHoursField.getText());

                StudentProfile profile =
                        new StudentProfile(
                                0,
                                0,
                                branchId,
                                semesterId,
                                interestsField.getText(),
                                existingSkillsField.getText(),
                                careerGoalField.getText(),
                                hours
                        );

                int profileId =
                        profileService.saveProfile(profile);

                message.setText(
                        "Profile saved. ID: " + profileId);

            } catch (Exception e) {

                message.setText(
                        "Error: " + e.getMessage());
            }
        });

        careerButton.setOnAction(event -> {

            MainApp.setScene(
                    new javafx.scene.Scene(
                            new CareerView().getView(),
                            800, 600));
        });

        root.getChildren().addAll(
                title,
                branchIdField,
                semesterIdField,
                interestsField,
                existingSkillsField,
                careerGoalField,
                studyHoursField,
                saveButton,
                careerButton,
                message
        );
    }

    public Parent getView() {
        return root;
    }
}
