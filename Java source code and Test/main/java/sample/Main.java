package sample;

import controller.login_controller;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.junit.Test;



import java.net.URL;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }



    @Test
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

       // Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainFrame.fxml"));



        Scene s=new Scene(root, 1000, 800);
        URL i=this.getClass().getResource("/css/scollbar.css");
        s.getStylesheets().add(i.toExternalForm());
        primaryStage.setScene(s);
        primaryStage.setTitle("");
      // primaryStage.initStyle(StageStyle.TRANSPARENT);
        login_controller.loginstage=primaryStage;
        primaryStage.show();


    }

}
