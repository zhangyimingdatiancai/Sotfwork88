package controller;

import domain.Coach;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import domain.Student;
import domain.User;
import dao.WorkUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;


public class sign_in_controller implements Initializable {
    @FXML
    private AnchorPane sign_in_pane;

    @FXML
    private TextField userNameField;

    @FXML
    private ChoiceBox userType_ChoiceBox;

    @FXML
    private TextField phoneNumField;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView back;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button sign_in_finish_btn;

    @FXML
    private TextField addressField;

    @FXML
    private ChoiceBox gender_ChoiceBox;

    int typeChoice = 0;
    int genderChoice = 0;

    @FXML
    void sign_in_finish_btn_click(ActionEvent event) throws IOException {

        int flag = 0;

        String username = userNameField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String phonenum = phoneNumField.getText();
        User s;
        if(typeChoice==0){
            s=new Student();
        }else{
            s=new Coach();
        }

        if(WorkUtil.stringCheck(username)==false){
            titleLabel.setText("Username illegal");
            flag++;
        }
        if(WorkUtil.stringCheck(password)==false){
            titleLabel.setText("Password illegal");
            flag++;
        }
        if(WorkUtil.stringCheck(address)==false){
            titleLabel.setText("Address illegal");
            flag++;
        }
        if(WorkUtil.numCheck(phonenum)==false){
            titleLabel.setText("PhoneNum illegal");
            flag++;
        }

        if(flag==0){
            if(WorkUtil.hasMember(username)){
                titleLabel.setText("Name already exist!");
                flag++;
            }
            if(flag==0){
                s.setName(username);
                s.setPassword(password);
                s.setAddress(address);
                s.setPhonenum(phonenum);
                s.setLessionID(new ArrayList<>());
                if(genderChoice==0)
                    s.setGender("Male");
                else if(genderChoice==1)
                    s.setGender("Female");
                if(typeChoice==0)
                    s.setId(WorkUtil.getIdOfStu());
                else if(typeChoice==1)
                    s.setId(WorkUtil.getIdOfCoach());
                WorkUtil.addUser(s);
                titleLabel.setText("Account create succeed!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender_ChoiceBox.setItems(FXCollections.observableArrayList(
                "  Male  "," Female ")
        );
        gender_ChoiceBox.getSelectionModel().selectFirst();
        gender_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue)->{
                    genderChoice = newValue.intValue();
                });

        userType_ChoiceBox.setItems(FXCollections.observableArrayList(
                " Student ","  Coach  ")
        );
        userType_ChoiceBox.getSelectionModel().selectFirst();
        userType_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue)->{
                    typeChoice = newValue.intValue();
                });
        String path=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\image\\";
        try {
            path = java.net.URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("22222222222222222"+new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\image\\");

        File file = new File(path+"back.png");
        Image image =new Image(file.toURI().toString());
        back.setImage(image);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    sign_in_pane.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
