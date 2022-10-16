package model;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartClient extends Application {
    private Stage authStage;
    private Stage primaryStage;
    private MainController mainController;

    private void openAuthDialog() throws IOException {
        FXMLLoader authLoader = new FXMLLoader(StartClient.class.getResource("auth.fxml"));
        authStage = new Stage();
        Scene scene = new Scene(authLoader.load());

        authStage.setScene(scene);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.initOwner(primaryStage);
        authStage.setTitle("Authentication");
        authStage.setAlwaysOnTop(true);
        authStage.show();
    }

    private void createMainDialog() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);

        mainController = fxmlLoader.getController();

    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}