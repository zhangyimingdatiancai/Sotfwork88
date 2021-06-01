package controller;

import com.alibaba.fastjson.JSONArray;
import domain.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import dao.WorkUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class forum_addReply_controller implements Initializable {
    Question question;

    @FXML
    private ImageView back;

    @FXML
    private Button postButton;

    @FXML
    private TextArea contentField;

    @FXML
    private AnchorPane addReplyPane;

    public static AnchorPane contentpane;

    @FXML
    void postButtonClick(ActionEvent event) {
        List<Reply> replyList;
        if(!question.getReplyListString().equals(""))
            replyList = JSONArray.parseArray(question.getReplyListString()).toJavaList(Reply.class);
        else
            replyList = new ArrayList<Reply>();

        Reply reply = new Reply();
        reply.setContentId(ForumIO.getIdOfReply());
        reply.setContent(contentField.getText());
        reply.setUserId(MainFrameController.id);
        Calendar calendar = Calendar.getInstance();
        reply.setPostTime(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE));
        if(MainFrameController.isStu){
            System.out.println("11");
            Student student = WorkUtil.getStudent(MainFrameController.id);
            reply.setName(student.getName());

        }else{
            Coach coach = WorkUtil.getCoach(MainFrameController.id);
            reply.setName(coach.getName());
        }
        replyList.add(reply);

        question.setReplyListString(JSONArray.toJSONString(replyList));
        ForumIO.replaceQuestion(question.getContentId(),question);

        Pane pane;
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/forum_content.fxml"));
            forum_content_controller content_controller = new forum_content_controller();
            content_controller.initData(question);
            loader.setController(content_controller);

            pane = loader.load();
            contentpane.getChildren().clear();
            contentpane.getChildren().add(pane);
           // addReplyPane.getScene().setRoot(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData(Question question){
        this.question = question;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String path=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\image\\";
        try {
            path = java.net.URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(path+"back.png");
        Image image =new Image(file.toURI().toString());
        back.setImage(image);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Pane pane;
                FXMLLoader loader;
                try {
                    loader = new FXMLLoader(getClass().getResource("/fxml/forum_content.fxml"));
                    forum_content_controller content_controller = new forum_content_controller();
                    content_controller.initData(question);
                    loader.setController(content_controller);

                    pane = loader.load();
                    contentpane.getChildren().clear();
                    contentpane.getChildren().add(pane);
                    //addReplyPane.getScene().setRoot(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
