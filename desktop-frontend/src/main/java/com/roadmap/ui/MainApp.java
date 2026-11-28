
package com.roadmap.ui;

import com.roadmap.ui.login.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        primaryStage.setTitle(
                "Smart Engineering Roadmap");

        showLogin();

        primaryStage.show();
    }

    public static void showLogin() {

        Scene scene = new Scene(
                new LoginView().getView(), 600, 450);

        primaryStage.setScene(scene);
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
