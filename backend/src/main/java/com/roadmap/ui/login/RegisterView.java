
package com.roadmap.ui.login;

import com.roadmap.service.AuthService;
import com.roadmap.ui.MainApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterView {

    private final AuthService authService =
            new AuthService();

    private final VBox root = new VBox(15);

    public RegisterView() {
        buildUI();
    }

    private void buildUI() {

        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Student Registration");

        title.setStyle(
                "-fx-font-size: 24px;"
                + "-fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Full name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password (minimum 8 characters)");

        Button registerButton =
                new Button("Register");

        Button backButton =
                new Button("Back to Login");

        Label message = new Label();

        registerButton.setOnAction(event -> {

            try {

                int userId = authService.registerStudent(
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText());

                message.setText(
                        "Registration successful. "
                        + "User ID: " + userId);

                MainApp.showLogin();

            } catch (Exception e) {

                message.setText(
                        "Registration failed: "
                        + e.getMessage());
            }
        });

        backButton.setOnAction(event ->
                MainApp.showLogin());

        root.getChildren().addAll(
                title,
                nameField,
                emailField,
                passwordField,
                registerButton,
                backButton,
                message
        );
    }

    public Parent getView() {
        return root;
    }
}
