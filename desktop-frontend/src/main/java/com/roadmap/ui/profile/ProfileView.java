
package com.roadmap.ui.profile;

import com.roadmap.dao.BranchDAO;
import com.roadmap.dao.SemesterDAO;
import com.roadmap.model.StudentProfile;
import com.roadmap.service.StudentProfileService;
import com.roadmap.ui.MainApp;
import com.roadmap.ui.career.CareerView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Map;

public class ProfileView {

    private final StudentProfileService profileService =
            new StudentProfileService();

    private final VBox root = new VBox(12);

    private final ComboBox<Integer> branchCombo =
            new ComboBox<>();

    private final ComboBox<Integer> semesterCombo =
            new ComboBox<>();

    private final TextField interestsField =
            new TextField();

    private final TextField existingSkillsField =
            new TextField();

    private final TextField careerGoalField =
            new TextField();

    private final TextField studyHoursField =
            new TextField();

    private final Label message = new Label();

    private final Map<Integer, String> branches;
    private final Map<Integer, String> semesters;

    private StudentProfile existingProfile;

    public ProfileView() {

        Map<Integer, String> loadedBranches;
        Map<Integer, String> loadedSemesters;

        try {

            loadedBranches =
                    new BranchDAO().getAllBranches();

            loadedSemesters =
                    new SemesterDAO().getAllSemesters();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to load branches/semesters: "
                    + e.getMessage(), e);
        }

        branches = loadedBranches;
        semesters = loadedSemesters;

        buildUI();
        loadExistingProfile();
    }

    private void buildUI() {

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle(
                "-fx-background-color: "
                + MainApp.BG + ";");

        VBox card = new VBox(16);
        card.setMaxWidth(650);
        card.setPadding(new Insets(30));

        MainApp.styleRegion(card);

        Label title = new Label(
                "Complete Your Profile");

        MainApp.styleTitle(title);

        Label subtitle = new Label(
                "Tell us about your academic journey.");

        MainApp.styleLabel(subtitle);

        branchCombo.setPromptText(
                "Select Branch");

        branchCombo.getItems().addAll(
                branches.keySet());

        branchCombo.setCellFactory(list ->
                new ListCell<Integer>() {
            @Override
            protected void updateItem(
                    Integer id, boolean empty) {

                super.updateItem(id, empty);

                setText(empty || id == null
                        ? null
                        : branches.get(id));
            }
        });

        branchCombo.setButtonCell(
                new ListCell<Integer>() {
            @Override
            protected void updateItem(
                    Integer id, boolean empty) {

                super.updateItem(id, empty);

                setText(empty || id == null
                        ? null
                        : branches.get(id));
            }
        });

        semesterCombo.setPromptText(
                "Select Semester");

        semesterCombo.getItems().addAll(
                semesters.keySet());

        semesterCombo.setCellFactory(list ->
                new ListCell<Integer>() {
            @Override
            protected void updateItem(
                    Integer id, boolean empty) {

                super.updateItem(id, empty);

                setText(empty || id == null
                        ? null
                        : semesters.get(id));
            }
        });

        semesterCombo.setButtonCell(
                new ListCell<Integer>() {
            @Override
            protected void updateItem(
                    Integer id, boolean empty) {

                super.updateItem(id, empty);

                setText(empty || id == null
                        ? null
                        : semesters.get(id));
            }
        });

        styleCombo(branchCombo);
        styleCombo(semesterCombo);

        interestsField.setPromptText(
                "Your interests (e.g. AI, Web Development)");

        existingSkillsField.setPromptText(
                "Existing skills (e.g. Java, SQL)");

        careerGoalField.setPromptText(
                "Career goal (e.g. Software Engineer)");

        studyHoursField.setPromptText(
                "Daily study hours");

        MainApp.styleField(interestsField);
        MainApp.styleField(existingSkillsField);
        MainApp.styleField(careerGoalField);
        MainApp.styleField(studyHoursField);

        Button saveButton =
                new Button("Save Profile");

        MainApp.styleButton(saveButton, true);

        Button careerButton =
                new Button("Explore Careers");

        MainApp.styleButton(careerButton, false);

        MainApp.styleMessage(message);

        saveButton.setOnAction(event ->
                saveProfile());

        careerButton.setOnAction(event -> {

            MainApp.setScene(
                    MainApp.createScene(
                            new CareerView().getView(),
                            1000, 750));
        });

        card.getChildren().addAll(
                title,
                subtitle,
                branchCombo,
                semesterCombo,
                interestsField,
                existingSkillsField,
                careerGoalField,
                studyHoursField,
                saveButton,
                careerButton,
                message
        );

        root.getChildren().add(card);
    }

    private void styleCombo(
            ComboBox<Integer> combo) {

        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setMinHeight(45);

        combo.setStyle(
                "-fx-background-color: #1E293B;"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 5;");
    }

    private void loadExistingProfile() {

        try {

            existingProfile =
                    profileService.getCurrentProfile();

            if (existingProfile == null) {

                message.setText(
                        "Create your profile.");

                return;
            }

            branchCombo.setValue(
                    existingProfile.getBranchId());

            semesterCombo.setValue(
                    existingProfile.getSemesterId());

            interestsField.setText(
                    existingProfile.getInterests());

            existingSkillsField.setText(
                    existingProfile.getExistingSkills());

            careerGoalField.setText(
                    existingProfile.getCareerGoal());

            studyHoursField.setText(
                    String.valueOf(
                            existingProfile
                            .getDailyStudyHours()));

            message.setText(
                    "Existing profile loaded.");

        } catch (Exception e) {

            message.setText(
                    "Profile loading error: "
                    + e.getMessage());
        }
    }

    private void saveProfile() {

        try {

            Integer branchId =
                    branchCombo.getValue();

            Integer semesterId =
                    semesterCombo.getValue();

            if (branchId == null
                    || semesterId == null) {

                message.setText(
                        "Select branch and semester.");

                return;
            }

            double hours = Double.parseDouble(
                    studyHoursField.getText().trim());

            StudentProfile profile =
                    new StudentProfile(
                            existingProfile == null
                                    ? 0
                                    : existingProfile
                                    .getProfileId(),

                            0,
                            branchId,
                            semesterId,
                            interestsField.getText(),
                            existingSkillsField.getText(),
                            careerGoalField.getText(),
                            hours
                    );

            if (existingProfile == null) {

                int profileId =
                        profileService.saveProfile(
                                profile);

                message.setText(
                        "Profile saved. ID: "
                        + profileId);

                existingProfile =
                        profileService
                        .getCurrentProfile();

            } else {

                boolean updated =
                        profileService
                        .updateCurrentProfile(
                                profile);

                message.setText(
                        updated
                        ? "Profile updated successfully."
                        : "Profile update failed.");
            }

        } catch (NumberFormatException e) {

            message.setText(
                    "Enter valid daily study hours.");

        } catch (Exception e) {

            message.setText(
                    "Error: " + e.getMessage());
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
