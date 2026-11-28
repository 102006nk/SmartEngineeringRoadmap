package com.roadmap.ui.login;

import com.roadmap.service.AuthService;
import com.roadmap.ui.MainApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterView {

    private final AuthService authService =
            new AuthService();

    private final StackPane root = new StackPane();

    public RegisterView() {
        buildUI();
    }

    private void buildUI() {

        root.setStyle(
                "-fx-background-color: "
                + MainApp.BG + ";");

        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(420);
        card.setPadding(new Insets(40));

        MainApp.styleRegion(card);

        Label title = new Label(
                "Create Your Account");

        MainApp.styleTitle(title);

        Label subtitle = new Label(
                "Start building your engineering career.");

        MainApp.styleLabel(subtitle);

        TextField nameField = new TextField();
        nameField.setPromptText("Full name");
        MainApp.styleField(nameField);

        TextField emailField = new TextField();
        emailField.setPromptText("Email address");
        MainApp.styleField(emailField);

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password (minimum 8 characters)");

        MainApp.styleField(passwordField);

        Button registerButton =
                new Button("Create Account");

        MainApp.styleButton(registerButton, true);

        Button backButton =
                new Button("Back to Login");

        MainApp.styleButton(backButton, false);

        Label message = new Label();
        MainApp.styleMessage(message);

        registerButton.setOnAction(event -> {

            try {

                int userId =
                        authService.registerStudent(
                                nameField.getText().trim(),
                                emailField.getText().trim(),
                                passwordField.getText());

                message.setText(
                        "Registration successful!");

                MainApp.showLogin();

            } catch (Exception e) {

                message.setText(
                        "Registration failed: "
                        + e.getMessage());
            }
        });

        backButton.setOnAction(event ->
                MainApp.showLogin());

        card.getChildren().addAll(
                title,
                subtitle,
                nameField,
                emailField,
                passwordField,
                registerButton,
                backButton,
                message
        );

        root.getChildren().add(card);
    }

    public Parent getView() {
        return root;
    }
}
