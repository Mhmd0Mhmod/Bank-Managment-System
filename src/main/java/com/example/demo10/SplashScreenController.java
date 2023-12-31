package com.example.demo10;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {


    @FXML
    private ProgressBar progressIndicator;

    @FXML
    private Label percentagenumber;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new LoadingScreen().start();

    }



    public class LoadingScreen extends Thread {
        @Override
        public void run(){
            try {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(100), event -> {
                            double currentProgress = progressIndicator.getProgress();
                            if (currentProgress < 1.0) {
                                // Convert progress from 0-1 to 1-100
                                int percentage = (int) Math.round((currentProgress * 100));
                                percentagenumber.setText( percentage + "%");
                                progressIndicator.setProgress(currentProgress + 0.02);
                            } else {
                                // Reset progress when it reaches 100%
                                progressIndicator.setProgress(0.0);
                            }
                        })
                );
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                Thread.sleep(5000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //Changing the scene of the same stage by getting stage from label
                        try {
                            new LoadScene("login.fxml", ( percentagenumber.getScene())).createScene();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
