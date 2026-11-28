
package com.roadmap.ui.login;

import com.roadmap.model.User;
import com.roadmap.service.AuthService;
import com.roadmap.ui.MainApp;
import com.roadmap.ui.profile.ProfileView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

    private final AuthService authService =
            new AuthService();

    private final VBox root = new VBox(15);

    public LoginView() {
        buildUI();
    }

    private void buildUI() {

        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Label title = new Label(
                "Smart Engineering Roadmap");

        title.setStyle(
                "-fx-font-size: 24px;"
                + "-fx-font-weight: bold;");

        TextField emailField =
                new TextField();

        emailField.setPromptText("Email");

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText("Password");

        Button loginButton =
                new Button("Login");

        Button registerButton =
                new Button("Create Account");

        Label message = new Label();

        loginButton.setOnAction(event -> {

            try {

                User user = authService.login(
                        emailField.getText(),
                        passwordField.getText());

                if (user != null) {

                    message.setText("Login successful!");

                    MainApp.setScene(
                            new javafx.scene.Scene(
                                    new ProfileView().getView(),
                                    700, 600));

                } else {

                    message.setText(
                            "Invalid email or password.");
                }

            } catch (Exception e) {

                message.setText(
                        "Login failed: " + e.getMessage());
            }
        });

        registerButton.setOnAction(event -> {

            MainApp.setScene(
                    new javafx.scene.Scene(
                            new RegisterView().getView(),
                            600, 500));
        });

        root.getChildren().addAll(
                title,
                emailField,
                passwordField,
                loginButton,
                registerButton,
                message
        );
    }

    public Parent getView() {
        return root;
    }
}
