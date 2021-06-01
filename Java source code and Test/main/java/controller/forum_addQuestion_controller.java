package controller;

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
import domain.ForumIO;
import domain.Question;
import dao.WorkUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class forum_addQuestion_controller implements Initializable {
    int userId;

    @FXML
    private AnchorPane addQuestionPane;

    @FXML
    private TextField titleField;

    @FXML
    private ImageView back;

    @FXML
    private Button postButton;

    @FXML
    private TextArea contentField;

    public static AnchorPane contentpane;


    @FXML
    void postButtonClick(ActionEvent event) throws IOException {
        Question question = new Question();

        question.setTitle(titleField.getText());
        question.setContent(contentField.getText());
        question.setUserId(userId);
        Calendar calendar = Calendar.getInstance();
        question.setPostTime(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE));
        if(MainFrameController.isStu){
            question.setName(WorkUtil.getStudent(userId).getName());
        }else{
            question.setName(WorkUtil.getCoach(userId).getName());
        }

        ForumIO.addQuestion(question);
        contentpane.getChildren().clear();
        contentpane.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
       // addQuestionPane.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
    }

    public void initData(int userId){
        this.userId = userId;
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
                try {
                   // addQuestionPane.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
                    contentpane.getChildren().clear();
                    contentpane.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
                   // addQuestionPane.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/forum.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
