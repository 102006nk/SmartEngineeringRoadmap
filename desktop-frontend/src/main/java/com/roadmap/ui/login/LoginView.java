package com.roadmap.ui.login;

import com.roadmap.model.User;
import com.roadmap.service.AuthService;
import com.roadmap.ui.MainApp;
import com.roadmap.ui.profile.ProfileView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginView {

    private final AuthService authService =
            new AuthService();

    private final StackPane root = new StackPane();

    public LoginView() {
        buildUI();
    }

    private void buildUI() {

        root.setStyle(
                "-fx-background-color: "
                + MainApp.BG + ";");

        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(400);
        card.setPadding(new Insets(40));

        MainApp.styleRegion(card);

        Label logo = new Label("S");
        logo.setStyle(
                "-fx-background-color: #6366F1;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 28px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 16;"
                + "-fx-padding: 14 22;");

        Label title = new Label(
                "Smart Engineering Roadmap");

        MainApp.styleTitle(title);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);

        Label subtitle = new Label(
                "Build your future, one skill at a time.");

        MainApp.styleLabel(subtitle);

        TextField emailField = new TextField();
        emailField.setPromptText("Email address");
        MainApp.styleField(emailField);

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText("Password");
        MainApp.styleField(passwordField);

        Button loginButton =
                new Button("Sign In");

        MainApp.styleButton(loginButton, true);

        Button registerButton =
                new Button("Create Account");

        MainApp.styleButton(registerButton, false);

        Label message = new Label();
        MainApp.styleMessage(message);

        loginButton.setOnAction(event -> {

            try {

                User user = authService.login(
                        emailField.getText().trim(),
                        passwordField.getText());

                if (user != null) {

                    MainApp.setScene(
                            MainApp.createScene(
                                    new ProfileView().getView(),
                                    900, 700));

                } else {

                    message.setText(
                            "Invalid email or password.");
                }

            } catch (Exception e) {

                message.setText(
                        "Login failed: "
                        + e.getMessage());
            }
        });

        registerButton.setOnAction(event -> {

            MainApp.setScene(
                    MainApp.createScene(
                            new RegisterView().getView(),
                            900, 650));
        });

        card.getChildren().addAll(
                logo,
                title,
                subtitle,
                emailField,
                passwordField,
                loginButton,
                registerButton,
                message
        );

        root.getChildren().add(card);
    }

    public Parent getView() {
        return root;
    }
}
