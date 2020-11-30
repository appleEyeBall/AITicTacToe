import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    Parent gameNode;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
        primaryStage.setTitle("AI Tic-Tac-Toe");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
        showGameScene(primaryStage);

    }

    void showGameScene(Stage primaryStage) {
        // delay for a while, then change scene
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished( event -> {
            try {
                gameNode = FXMLLoader.load(getClass().getResource("fxml/gameScene.fxml"));
                primaryStage.setScene(new Scene(gameNode, 600, 500));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }


    void showGameResultScene(Stage primaryStage){
        try {
            Parent gameNode = FXMLLoader.load(getClass().getResource("fxml/resultScene.fxml"));
            primaryStage.setScene(new Scene(gameNode, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

