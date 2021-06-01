package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import domain.Coach;
import domain.Student;
import dao.WorkUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class info_show_controller implements Initializable{
    @FXML
    private Label ID_field;

    @FXML
    private ImageView address_change;

    @FXML
    private TextArea signature_field;

    @FXML
    private ImageView signature_change;

    @FXML
    private ImageView phonenum_change;

    @FXML
    private ImageView photo;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label balance_field;

    @FXML
    private TextField phonenum_field;

    @FXML
    private Label accountType_field;

    @FXML
    private ImageView password_change;

    @FXML
    private TextField nickname_field;

    @FXML
    private Label accountPoints_field;

    @FXML
    private TextField address_field;

    @FXML
    private ChoiceBox gender_ChoiceBox;

    @FXML
    private ImageView nickname_change;


    int nickname_state = 0;
    int password_state = 0;
    int phonenum_state = 0;
    int address_state = 0;
    int signature_state = 0;

    public void initialize(URL url, ResourceBundle rb) {
        String path=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\image\\";
        try {
            path = java.net.URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ID_field.setText(Integer.toString(MainFrameController.id));

        File file = new File(path+"reset.png");
        File file2 = new File(path+"default_avatar.jpeg");
        File file3 = new File(path+"ensure.PNG");
        Image image =new Image(file.toURI().toString());
        Image image2 =new Image(file2.toURI().toString());
        Image image3 =new Image(file3.toURI().toString());
        address_change.setImage(image);
        phonenum_change.setImage(image);
        password_change.setImage(image);
        nickname_change.setImage(image);
        signature_change.setImage(image);
        photo.setImage(image2);

        password_field.setDisable(true);
        phonenum_field.setDisable(true);
        address_field.setDisable(true);
        nickname_field.setDisable(true);
        signature_field.setDisable(true);
        signature_field.setWrapText(true);

        if(MainFrameController.isStu==true){
            Student object = WorkUtil.getStudent(MainFrameController.id);
            nickname_field.setText(object.getName());
            password_field.setText(object.getPassword());
            phonenum_field.setText(object.getPhonenum());
            address_field.setText(object.getAddress());
            if(object.getGender().equals("Male")){
                gender_ChoiceBox.getSelectionModel().selectFirst();
            }else if(object.getGender().equals("Female")){
                gender_ChoiceBox.getSelectionModel().select(1);
            }
            accountType_field.setText("VIP"+object.getViplevel());
        }else if(MainFrameController.isStu==false){
            Coach object = WorkUtil.getCoach(MainFrameController.id);
            nickname_field.setText(object.getName());
            password_field.setText(object.getPassword());
            phonenum_field.setText(object.getPhonenum());
            address_field.setText(object.getAddress());
            if(object.getGender().equals("Male")){
                gender_ChoiceBox.getSelectionModel().selectFirst();
            }else if(object.getGender().equals("Female")){
                gender_ChoiceBox.getSelectionModel().select(1);
            }
            accountType_field.setText("Coach");
        }

        gender_ChoiceBox.setItems(FXCollections.observableArrayList(
                "   Male"," Female    ")
        );
        gender_ChoiceBox.getSelectionModel().selectFirst();
        gender_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue)->{
                System.out.println(newValue.intValue());
        });

        nickname_change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(nickname_state==0){
                    nickname_field.setDisable(false);
                    nickname_change.setImage(image3);
                    nickname_state=1;
                }
                else if(nickname_state==1){
                    nickname_field.setDisable(true);
                    nickname_change.setImage(image);
                    nickname_state=0;
                    if(MainFrameController.isStu==true){
                        Student object = WorkUtil.getStudent(MainFrameController.id);
                        object.setName(nickname_field.getText());
                        WorkUtil.replaceStu(MainFrameController.id,object);
                    }else if(MainFrameController.isStu==false){
                        Coach object = WorkUtil.getCoach(MainFrameController.id);
                        object.setName(nickname_field.getText());
                        WorkUtil.replaceCoach(MainFrameController.id,object);
                    }
                }
            }
        });
        password_change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(password_state==0){
                    password_field.setDisable(false);
                    password_change.setImage(image3);
                    password_state=1;
                }
                else if(password_state==1){
                    password_field.setDisable(true);
                    password_change.setImage(image);
                    password_state=0;
                    if(MainFrameController.isStu==true){
                        Student object = WorkUtil.getStudent(MainFrameController.id);
                        object.setPassword(password_field.getText());
                        WorkUtil.replaceStu(MainFrameController.id,object);
                    }else if(MainFrameController.isStu==false){
                        Coach object = WorkUtil.getCoach(MainFrameController.id);
                        object.setPassword(password_field.getText());
                        WorkUtil.replaceCoach(MainFrameController.id,object);
                    }
                }
            }
        });
        phonenum_change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(phonenum_state==0){
                    phonenum_field.setDisable(false);
                    phonenum_change.setImage(image3);
                    phonenum_state=1;
                }
                else if(phonenum_state==1){
                    phonenum_field.setDisable(true);
                    phonenum_change.setImage(image);
                    phonenum_state=0;
                    if(MainFrameController.isStu==true){
                        Student object = WorkUtil.getStudent(MainFrameController.id);
                        object.setPhonenum(phonenum_field.getText());
                        WorkUtil.replaceStu(MainFrameController.id,object);
                    }else if(MainFrameController.isStu==false){
                        Coach object = WorkUtil.getCoach(MainFrameController.id);
                        object.setPhonenum(phonenum_field.getText());
                        WorkUtil.replaceCoach(MainFrameController.id,object);
                    }
                }
            }
        });
        address_change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(address_state==0){
                    address_field.setDisable(false);
                    address_change.setImage(image3);
                    address_state=1;
                }
                else if(address_state==1){
                    address_field.setDisable(true);
                    address_change.setImage(image);
                    address_state=0;
                    if(MainFrameController.isStu==true){
                        Student object = WorkUtil.getStudent(MainFrameController.id);
                        object.setAddress(address_field.getText());
                        WorkUtil.replaceStu(MainFrameController.id,object);
                    }else if(MainFrameController.isStu==false){
                        Coach object = WorkUtil.getCoach(MainFrameController.id);
                        object.setAddress(address_field.getText());
                        WorkUtil.replaceCoach(MainFrameController.id,object);
                    }
                }
            }
        });
        signature_change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(signature_state==0){
                    signature_field.setDisable(false);
                    signature_change.setImage(image3);
                    signature_state=1;
                }
                else if(signature_state==1){
                    signature_field.setDisable(true);
                    signature_change.setImage(image);
                    signature_state=0;
                }
            }
        });

        /*
        Student s= new Student();
        if(WorkUtil.stringCheck(firstName_field.getText())==true){
            s.setName(firstName_field.getText());
        }else{

        }
        */
    }
}
