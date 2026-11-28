
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
import javafx.scene.layout.VBox;

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

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Student Profile");

        branchCombo.setPromptText("Select Branch");
        branchCombo.getItems().addAll(
                branches.keySet());

        branchCombo.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);

                if (empty || id == null) {
                    setText(null);
                } else {
                    setText(branches.get(id));
                }
            }
        });

        branchCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);

                if (empty || id == null) {
                    setText(null);
                } else {
                    setText(branches.get(id));
                }
            }
        });

        semesterCombo.setPromptText("Select Semester");
        semesterCombo.getItems().addAll(
                semesters.keySet());

        semesterCombo.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);

                if (empty || id == null) {
                    setText(null);
                } else {
                    setText(semesters.get(id));
                }
            }
        });

        semesterCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);

                if (empty || id == null) {
                    setText(null);
                } else {
                    setText(semesters.get(id));
                }
            }
        });

        interestsField.setPromptText("Interests");
        existingSkillsField.setPromptText("Existing skills");
        careerGoalField.setPromptText("Career goal");
        studyHoursField.setPromptText("Daily study hours");

        Button saveButton = new Button("Save Profile");

        Button careerButton =
                new Button("Explore Careers");

        saveButton.setOnAction(event -> saveProfile());

        careerButton.setOnAction(event -> {

            MainApp.setScene(
                    new javafx.scene.Scene(
                            new CareerView().getView(),
                            800, 600));
        });

        root.getChildren().addAll(
                title,
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
                            existingProfile.getDailyStudyHours()));

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
                                    : existingProfile.getProfileId(),

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
                        profileService.saveProfile(profile);

                message.setText(
                        "Profile saved. ID: "
                        + profileId);

                existingProfile =
                        profileService.getCurrentProfile();

            } else {

                boolean updated =
                        profileService.updateCurrentProfile(
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
        return root;
    }
}
