package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import dao.WorkUtil;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class login_controller implements Initializable {

    @FXML
    private TextField accountField;

    @FXML
    private Label titleLabel;

    @FXML
    private Button sign_in_btn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private ChoiceBox userType_ChoiceBox;

    @FXML
    private Button login_btn;

    int choice = 0;

    public static Stage loginstage;

    @FXML
    void sign_in_btn_click(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sign_in.fxml"));
        loginPane.getChildren().clear();
        loginPane.getChildren().add(root);
    }

    @FXML
    void login_btn_click(ActionEvent event) throws IOException {
        String account = accountField.getText();
        String password = passwordField.getText();
        if(choice==0&&WorkUtil.loginCheckForStu(account,password)==true){
            MainFrameController.isStu=true;
            MainFrameController.id=WorkUtil.mapOfStuForLogin.get(account).getId();
            loginstage.close();
            shownew_stage();


        }else if(choice==1&&WorkUtil.loginCheckForCoach(account,password)==true){
            MainFrameController.isStu=false;
            MainFrameController.id=WorkUtil.mapOfCoachForLogin.get(account).getId();
            loginstage.close();
            shownew_stage();
        }else{
            titleLabel.setText("Wrong Password or Username!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userType_ChoiceBox.setItems(FXCollections.observableArrayList(
                " Student ","  Coach  ")
        );
        userType_ChoiceBox.getSelectionModel().selectFirst();
        userType_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue)->{
                    choice = newValue.intValue();
                });
    }

    public void shownew_stage() throws IOException {
        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainFrame.fxml"));
        Scene s=new Scene(root, 1000, 800);
        URL i=this.getClass().getResource("/css/scollbar.css");
        s.getStylesheets().add(i.toExternalForm());
        stage.setScene(s);
        stage.setTitle("");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

}
