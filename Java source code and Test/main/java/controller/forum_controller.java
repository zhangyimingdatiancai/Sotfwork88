package controller;

import com.alibaba.fastjson.JSONArray;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import domain.ForumIO;
import domain.Question;
import domain.Reply;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class forum_controller implements Initializable {

    List<Question> questionList = ForumIO.getQuestionList();

    public static AnchorPane contentpane;

    @FXML
    private AnchorPane forumPane;

    @FXML
    private ScrollPane questionPane;

    @FXML
    private Button addQuestion;

    @FXML
    void addQuestionClick(ActionEvent event) {
        Pane pane;
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/forum_addQuestion.fxml"));
            forum_addQuestion_controller addQuestion_controller = new forum_addQuestion_controller();
            addQuestion_controller.initData(MainFrameController.id);
            loader.setController(addQuestion_controller);

            pane = loader.load();
            forum_addQuestion_controller.contentpane=contentpane;
            contentpane.getChildren().clear();
            contentpane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane pane = new AnchorPane();
        pane.setLayoutY(80);
        pane.setPrefWidth(805);
        //pane.setPrefHeight(1);

        questionPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        questionPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        for(int i=0;i<questionList.size();i++){
            pane.getChildren().add(addQuestion(i));
        }
        questionPane.setContent(pane);

    }

    public AnchorPane addQuestion(int num){
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(60);
        pane.setPrefWidth(805);
        pane.setLayoutY(60*num);

        if(num%2==1)
            pane.setStyle("-fx-background-color:#bae0ff;");
        else
            pane.setStyle("-fx-background-color:#ffffff;");

        Question question = questionList.get(num);

        Label title = new Label();
        title.setPrefHeight(60);
        title.setPrefWidth(470.0);
        title.setLayoutX(30.0);
        title.setText("[Q]"+question.getTitle());
        title.setStyle("-fx-font-family:'Calibri';-fx-font-size:20px;-fx-font-weight:bold;");

        Label name = new Label();
        name.setPrefHeight(30.0);
        name.setPrefWidth(170.0);
        name.setLayoutX(630.0);
        name.setLayoutY(16.0);
        name.setText(question.getName());
        name.setStyle("-fx-font-family:'Calibri';-fx-font-size:20px;-fx-font-weight:bold;-fx-alignment:center;");

        Label postTime = new Label();
        postTime.setPrefHeight(30.0);
        postTime.setPrefWidth(100.0);
        postTime.setLayoutX(500.0);
        postTime.setLayoutY(16.0);
        postTime.setText(question.getPostTime());
        postTime.setStyle("-fx-font-family:'Calibri';-fx-font-size:20px;-fx-font-weight:bold;-fx-alignment:center;");

        pane.getChildren().add(title);
        pane.getChildren().add(name);
        pane.getChildren().add(postTime);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Pane pane2;
                FXMLLoader loader;
                try {
                    loader = new FXMLLoader(getClass().getResource("/fxml/forum_content.fxml"));
                    forum_content_controller content_controller = new forum_content_controller();
                    content_controller.initData(question);
                    loader.setController(content_controller);

                    pane2 = loader.load();
                    forum_content_controller.contentpane=contentpane;
                    contentpane.getChildren().clear();
                    contentpane.getChildren().add(pane2);
                    //  forumPane.getChildren().add(pane2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return pane;
    }

}
