package controller;

import com.alibaba.fastjson.JSONArray;
import dao.WorkUtil;
import domain.ForumIO;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import domain.Question;
import domain.Reply;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class forum_content_controller implements Initializable {
    @FXML
    private AnchorPane forumContentPane;

    @FXML
    private AnchorPane titlePane;

    @FXML
    private Label title;

    @FXML
    private TextArea questionContent;

    @FXML
    private Label questionBadCounter;

    @FXML
    private Label questionGoodCounter;

    @FXML
    private AnchorPane questionGoodField;

    @FXML
    private AnchorPane questionBadField;

    @FXML
    private ImageView back;

    @FXML
    private ImageView avatar;

    @FXML
    private Label quizzerName;

    @FXML
    private Label questionPostTime;

    @FXML
    private Button commentButton;

    @FXML
    private ScrollPane replyPane;

    public static AnchorPane contentpane;

    Question question;
    List<Reply> replyList;

    @FXML
    void commentButtonClick(ActionEvent event) {
        Pane pane;
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/forum_addReply.fxml"));
            forum_addReply_controller addReply_controller = new forum_addReply_controller();
            addReply_controller.initData(question);
            loader.setController(addReply_controller);

            pane = loader.load();
            forum_addReply_controller.contentpane=contentpane;
            contentpane.getChildren().clear();
            contentpane.getChildren().add(pane);
         //   forumContentPane.getScene().setRoot(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(MainFrameController.id);
        System.out.println(question.getUserId());
        if(MainFrameController.id==question.getUserId())
            addDeleteButton();
        String path=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\image\\";
        try {
            path = java.net.URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(path+"back.png");
        File file2 = new File(path+"default_avatar.jpeg");
        Image image =new Image(file.toURI().toString());
        Image image2 =new Image(file2.toURI().toString());
        back.setImage(image);
        avatar.setImage(image2);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    contentpane.getChildren().clear();
                    contentpane.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
                  //  forumContentPane.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        questionGoodField.setOnMouseClicked(mouseEvent -> {
            if(question.checkBadCounter(MainFrameController.id)==0){
                question.changeGoodCounter(MainFrameController.id);
                ForumIO.replaceQuestion(question.getContentId(),question);
                questionGoodCounter.setText(Integer.toString(question.returnGoodCounterList()));
            }
        });

        questionBadField.setOnMouseClicked(mouseEvent -> {
            if(question.checkGoodCounter(MainFrameController.id)==0) {
                question.changeBadCounter(MainFrameController.id);
                ForumIO.replaceQuestion(question.getContentId(),question);
                questionBadCounter.setText(Integer.toString(question.returnBadCounterList()));
            }
        });

        title.setText(question.getTitle());
        questionContent.setText(question.getContent());
        questionContent.setDisable(true);
        questionGoodCounter.setText(Integer.toString(question.returnGoodCounterList()));
        questionBadCounter.setText(Integer.toString(question.returnBadCounterList()));
        quizzerName.setText(question.getName());
        questionPostTime.setText(question.getPostTime());

        AnchorPane pane = new AnchorPane();
        pane.setLayoutY(235);
        pane.setPrefWidth(800);
        pane.setPrefHeight(365);

        replyPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        replyPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        if(!question.getReplyListString().equals("")){
            replyList = JSONArray.parseArray(question.getReplyListString()).toJavaList(Reply.class);
            for(int i=0;i<replyList.size();i++){
                pane.getChildren().add(addReply(i));
            }
        }
        replyPane.setContent(pane);
    }

    public void initData(Question question1) {
        question =question1;
    }

    public AnchorPane addReply(int num){
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(90);
        pane.setPrefWidth(800.0);
        pane.setLayoutY(90*num);

        Reply reply = replyList.get(num);

        Label name = new Label();
        name.setPrefHeight(60);
        name.setPrefWidth(150.0);
        name.setText(reply.getName());
        name.setStyle("-fx-background-color:#c1c8e8;-fx-font-family:'Arial Black';-fx-font-size:20px;-fx-alignment:center");

        TextArea replyContent = new TextArea();
        replyContent.setPrefHeight(60);
        replyContent.setPrefWidth(650.0);
        replyContent.setLayoutX(150);
        replyContent.setText(reply.getContent());
        replyContent.setDisable(true);
        replyContent.setStyle("-fx-font-family:'Arial Black';-fx-font-size:16px;");

        Label replyLevel = new Label();
        replyLevel.setPrefHeight(30);
        replyLevel.setPrefWidth(150.0);
        replyLevel.setLayoutY(60);
        if(reply.getUserId()>=100000&&reply.getUserId()<200000)
            replyLevel.setText("Coach");
        else if(reply.getUserId()>=200000&&reply.getUserId()<300000){
            Student student = WorkUtil.getStudent(reply.getUserId());
            replyLevel.setText("LEVEL:VIP"+student.getViplevel());
        }

        replyLevel.setStyle("-fx-background-color:#fbf5d9;-fx-font-family:'Arial Black';-fx-font-size:15px;-fx-text-fill:#666666;-fx-alignment:center;");

        AnchorPane pane1 = new AnchorPane();
        AnchorPane goodCounterContainer = new AnchorPane();
        AnchorPane badCounterContainer = new AnchorPane();

        Label goodCounterContainerFront = new Label();
        goodCounterContainerFront.setText("Good(");
        goodCounterContainerFront.setPrefWidth(50);
        goodCounterContainerFront.setPrefHeight(20);
        goodCounterContainerFront.setLayoutX(10);
        goodCounterContainerFront.setLayoutY(2);
        goodCounterContainerFront.setStyle("-fx-text-fill:#666666;");

        Label goodCounterNum = new Label();
        goodCounterNum.setText(Integer.toString(reply.returnGoodCounterList()));
        goodCounterNum.setPrefWidth(18);
        goodCounterNum.setPrefHeight(15);
        goodCounterNum.setLayoutX(59);
        goodCounterNum.setLayoutY(3);
        goodCounterNum.setStyle("-fx-text-fill:#666666;");

        Label goodCounterContainerTail = new Label();
        goodCounterContainerTail.setText(")");
        goodCounterContainerTail.setPrefWidth(18);
        goodCounterContainerTail.setPrefHeight(15);
        goodCounterContainerTail.setLayoutX(70);
        goodCounterContainerTail.setLayoutY(2);
        goodCounterContainerTail.setStyle("-fx-text-fill:#666666;");

        goodCounterContainer.setPrefWidth(70);
        goodCounterContainer.setPrefHeight(20);
        goodCounterContainer.setLayoutX(50);
        goodCounterContainer.setLayoutY(4);
        goodCounterContainer.getChildren().add(goodCounterContainerFront);
        goodCounterContainer.getChildren().add(goodCounterNum);
        goodCounterContainer.getChildren().add(goodCounterContainerTail);
        goodCounterContainer.setOnMouseClicked(mouseEvent -> {
            if(reply.checkBadCounter(MainFrameController.id)==0){
                reply.changeGoodCounter(MainFrameController.id);
                question.updateReply(reply);
                ForumIO.replaceQuestion(question.getContentId(),question);
                goodCounterNum.setText(Integer.toString(reply.returnGoodCounterList()));
            }
        });

        Label badCounterContainerFront = new Label();
        badCounterContainerFront.setText("Bad(");
        badCounterContainerFront.setPrefWidth(40);
        badCounterContainerFront.setPrefHeight(20);
        badCounterContainerFront.setLayoutX(10);
        badCounterContainerFront.setLayoutY(2);
        badCounterContainerFront.setStyle("-fx-text-fill:#666666;");

        Label badCounterNum = new Label();
        badCounterNum.setText(Integer.toString(reply.returnGoodCounterList()));
        badCounterNum.setPrefWidth(18);
        badCounterNum.setPrefHeight(15);
        badCounterNum.setLayoutX(49);
        badCounterNum.setLayoutY(3);
        badCounterNum.setStyle("-fx-text-fill:#666666;");

        Label badCounterContainerTail = new Label();
        badCounterContainerTail.setText(")");
        badCounterContainerTail.setPrefWidth(18);
        badCounterContainerTail.setPrefHeight(15);
        badCounterContainerTail.setLayoutX(60);
        badCounterContainerTail.setLayoutY(2);
        badCounterContainerTail.setStyle("-fx-text-fill:#666666;");

        badCounterContainer.setPrefWidth(70);
        badCounterContainer.setPrefHeight(20);
        badCounterContainer.setLayoutX(150);
        badCounterContainer.setLayoutY(4);
        badCounterContainer.getChildren().add(badCounterContainerFront);
        badCounterContainer.getChildren().add(badCounterNum);
        badCounterContainer.getChildren().add(badCounterContainerTail);
        badCounterContainer.setOnMouseClicked(mouseEvent -> {
            if(reply.checkGoodCounter(MainFrameController.id)==0){
                reply.changeBadCounter(MainFrameController.id);
                question.updateReply(reply);
                ForumIO.replaceQuestion(question.getContentId(),question);
                badCounterNum.setText(Integer.toString(reply.returnBadCounterList()));
            }
        });

        Label postTimeFront = new Label();
        postTimeFront.setPrefWidth(80);
        postTimeFront.setPrefHeight(20);
        postTimeFront.setLayoutX(338);
        postTimeFront.setLayoutY(5);
        postTimeFront.setText("Post Time:");
        postTimeFront.setStyle("-fx-text-fill:#666666;");

        Label postTimeTail = new Label();
        postTimeTail.setPrefWidth(90);
        postTimeTail.setPrefHeight(20);
        postTimeTail.setLayoutX(427);
        postTimeTail.setLayoutY(5);
        postTimeTail.setText(reply.getPostTime());
        postTimeTail.setStyle("-fx-text-fill:#666666;");

        pane1.setPrefWidth(650);
        pane1.setPrefHeight(30);
        pane1.setLayoutX(150);
        pane1.setLayoutY(60);
        pane1.setStyle("-fx-background-color:#c1c8e8;-fx-font-family:'Arial Black';-fx-font-size:13px;");
        pane1.getChildren().add(goodCounterContainer);
        pane1.getChildren().add(badCounterContainer);
        pane1.getChildren().add(postTimeFront);
        pane1.getChildren().add(postTimeTail);

        pane.getChildren().add(name);
        pane.getChildren().add(replyContent);
        pane.getChildren().add(replyLevel);
        pane.getChildren().add(pane1);

        return pane;
    }

    public void addDeleteButton(){
        Button button = new Button();
        button.setPrefWidth(65);
        button.setPrefHeight(24);
        button.setLayoutX(730);
        button.setLayoutY(8);
        button.setText("DELETE");
        button.setStyle("-fx-font-family:'Arial Black';-fx-font-size:11px;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                Pane pane2;
                FXMLLoader loader;
                try {
                    loader = new FXMLLoader(getClass().getResource("/fxml/deleteConfirm.fxml"));
                    deleteConfirm_controller delete_controller = new deleteConfirm_controller();
                    delete_controller.initData(question);
                    loader.setController(delete_controller);

                    deleteConfirm_controller.contentpane =contentpane;
                    pane2 = loader.load();
                    contentpane.getChildren().clear();
                    contentpane.getChildren().add(pane2);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        titlePane.getChildren().add(button);
    }
}
