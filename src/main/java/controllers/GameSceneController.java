package controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Packet;
import util.Util;
import workers.Player;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/*
* Note: sometimes I refer to 'X' as player1. please bear with this naming inconsistency.
*/

public class GameSceneController implements EventHandler {
    @FXML MenuItem viewInstructionsItem;
    @FXML ChoiceBox numOfGamesChoice;
    @FXML ChoiceBox levelPlayer1;
    @FXML ChoiceBox levelPlayer2;
    @FXML Button startBtn;
    @FXML Button exitBtn;
    @FXML GridPane gameGrid;
    @FXML Label infoLabel;

    int numOfGames = 0;
    int gamesPlayed = 0;
    String player1Skill;
    String player2Skill;

    ExecutorService ex;

    public void initialize(){
       viewInstructionsItem.setOnAction(this);
       startBtn.setOnAction(this);
       exitBtn.setOnAction(this);
       infoLabel.setText(Util.infoInitial);
       infoLabel.setTextFill(Color.ROYALBLUE);
       ex = Executors.newFixedThreadPool(5);
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == viewInstructionsItem){
            Alert instructionAlert = new Alert(Alert.AlertType.INFORMATION);
            instructionAlert.setContentText(Util.instructionText);
            instructionAlert.show();
        }
        else if (event.getSource() == startBtn){
            numOfGames = Integer.valueOf((numOfGamesChoice.getValue().toString().substring(0,2).trim()));    // get the integer value from choiceBox item
            player1Skill = levelPlayer1.getValue().toString();
            player2Skill = levelPlayer2.getValue().toString();
            initializeAndStartGamePlay();

        }
        else if (event.getSource() == exitBtn){
            ex.shutdownNow();
            Platform.exit();
            System.exit(0);
        }

    }

    /* Clear grid without actually removing the children */
    public void clearGrid(){
        for(int i=0; i<9; i++){
            ((Label)gameGrid.getChildren().get(i)).setText("");
        }
    }

    /* setup that needs to be done before all repeated gameplay*/
    void initializeAndStartGamePlay(){
        clearGrid();
        String[] gameState = Util.initialBoardValue.split(" ");    // blank board
        // randomly select a player. if playerTurn is true, x plays first. else, o plays first
        boolean playerTurn;
        Random random = new Random();
        if ((random.nextInt(9)+1)%2 == 0) playerTurn = true;
        else playerTurn = false;
        playGame(playerTurn, gameState);
    }


    /*
     * Main gameplay. Instead of a for loop to loop "numOfGames" times,
     * the playGame function gets called recursively numOfGames
     *  times or until a draw or someone wins
     */
    public void playGame(boolean isPlayer_1Turn, String[] gameState){
        startBtn.setDisable(true);
        infoLabel.setText(Util.infoDuring);
        infoLabel.setTextFill(Color.ROYALBLUE);
        Future<Packet> player_1Response;
        Future<Packet> player_2Response;
        boolean isDraw;
        String winner;
        try {
            if (isPlayer_1Turn){
                player_1Response = ex.submit(new Player(gameState, isPlayer_1Turn, player1Skill));
                Packet packet = player_1Response.get();
                gameState = packet.board;
                isDraw = packet.isDraw;
                winner = packet.winner;

                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                String[] finalGameState1 = gameState;
                delay.setOnFinished(event -> {
                    if (isDraw || !winner.equals(""));
                    else playGame(false, finalGameState1);       // call the game again if no one won
                });
                delay.play();

            }
            else{
                player_2Response = ex.submit(new Player(gameState, isPlayer_1Turn, player2Skill));
                Packet packet = player_2Response.get();

                gameState = packet.board;
                isDraw = packet.isDraw;
                winner = packet.winner;

                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                String[] finalGameState1 = gameState;
                delay.setOnFinished(event -> {
                    if (isDraw || !winner.equals(""));
                    else playGame(true, finalGameState1);       // call the game again if no one won
                });
                delay.play();
            }
            updateGuiBoard(gameState);
            if (isDraw || !winner.equals("")){
                showResults(isPlayer_1Turn, winner);
            }
                System.out.println(gameState);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // populate gui-board with values in the board array
    void updateGuiBoard(String[] board){
        int count = 0;
        for (String val: board){
            Label currentLabel = ((Label) gameGrid.getChildren().get(count));
            if (val.equals("b")) val = "";
            else if(val.equals("O")){
                currentLabel.setTextFill(Color.INDIANRED);
            }
            else{
                currentLabel.setTextFill(Color.DARKSEAGREEN);
            }
            currentLabel.setText(val);
            count ++;
        }
    }


    /* Switch to the scene that shows the results, and switch back */
    public void showResults(boolean playedFirst, String winner) {
        Scene currentScene = startBtn.getScene();
        Parent resultScene = null;
        FXMLLoader resultLoader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/resultScene.fxml"
                )
        );
        Stage stage = (Stage) currentScene.getWindow();
        // Swap screen

        PauseTransition launchDelay = new PauseTransition(Duration.seconds(2));
        launchDelay.setOnFinished( event -> {
            try {
                stage.setScene(new Scene(resultLoader.load()));
                ResultSceneController resultSceneController = resultLoader.getController();
                resultSceneController.initData(playedFirst, winner);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        launchDelay.play();

        PauseTransition returnDelay = new PauseTransition(Duration.seconds(6));
        returnDelay.setOnFinished( event -> {
            stage.setScene(currentScene);
            stage.show();
            gamesPlayed++;
            if (gamesPlayed<numOfGames) initializeAndStartGamePlay();
            else {
                gamesPlayed = 0;
                startBtn.setDisable(false);
                infoLabel.setText(Util.infoInitial);
                infoLabel.setTextFill(Color.ROYALBLUE);
            }
        });
        returnDelay.play();

    }

    public GridPane getGrid(){
        return gameGrid;
    }
}
