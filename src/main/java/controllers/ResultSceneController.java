package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;




public class ResultSceneController {
    @FXML Label winnerTextLabel;
    @FXML Label firstPlayerLabel;
    @FXML Label wonLabel;
    @FXML ImageView trophyImg;


    public void initData(boolean playedFirst, String winner){
        if (playedFirst) {
            firstPlayerLabel.setText("X");
            firstPlayerLabel.setTextFill(Color.DARKSEAGREEN);
        }
        else {
            firstPlayerLabel.setText("O");
            firstPlayerLabel.setTextFill(Color.INDIANRED);
        }

        if (winner.equals("X")) {
            winnerTextLabel.setText("X");
            winnerTextLabel.setTextFill(Color.DARKSEAGREEN);
            Image image = new Image("trophy_pic.png", 200, 300, true, true);
            trophyImg.setImage(image);
        }
        else if (winner.equals("O")) {
            winnerTextLabel.setText("O");
            winnerTextLabel.setTextFill(Color.INDIANRED);
            Image image = new Image("trophy_pic.png", 200, 300, true, true);
            trophyImg.setImage(image);
        }
        else if (winner.equals("")){
            winnerTextLabel.setText("Draw");
            wonLabel.setVisible(false);
        }


    }

}
