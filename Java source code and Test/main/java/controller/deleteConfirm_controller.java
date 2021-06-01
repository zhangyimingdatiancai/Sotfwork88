package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import domain.ForumIO;
import domain.Question;

import java.io.IOException;

public class deleteConfirm_controller {
    @FXML
    private AnchorPane deleteQuestionPane;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    Question question;

    public static AnchorPane contentpane;

    @FXML
    void yesButtonClick(ActionEvent event) throws IOException {
        ForumIO.deleteQuestion(question);
        contentpane.getChildren().clear();
        contentpane.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
    }

    @FXML
    void noButtonClick(ActionEvent event) throws IOException {
        Pane pane2;
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/fxml/forum_content.fxml"));
        forum_content_controller content_controller = new forum_content_controller();
        content_controller.initData(question);
        loader.setController(content_controller);
        pane2 = loader.load();
        contentpane.getChildren().clear();
        contentpane.getChildren().add(pane2);
    }

    public void initData(Question question){
        this.question = question;
    }
}
