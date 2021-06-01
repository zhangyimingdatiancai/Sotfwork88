package controller;

import dao.WorkUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */

public class UploadVideoFrameController {

    public static  AnchorPane contentpane;

    public static int courseid;

    @FXML
    private TextField Name;

    @FXML
    private TextField video;

    @FXML
    private Button videobutton;

    @FXML
    private AnchorPane drag;

    @FXML
    private Button submit;

    @FXML
    private Button back;

    private File myfile;


    @FXML
    private Label mark;

    @FXML
    void button_clicked(MouseEvent event)  {
        Stage stage= new Stage();
        FileChooser chooser= new FileChooser();
        chooser.setTitle("choose onew file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("picture","*.mp4","*.flv","*.avi","*.mkv")
        );
        File file= chooser.showOpenDialog(stage);
        if(file==null){
            return;
        }else{
            video.setText(file.getAbsolutePath());
            myfile=file;
        }
    }



    @FXML
    void Drag_drop_event(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (event.isAccepted()) {
            File file = dragboard.getFiles().get(0);
            video.setText(file.getAbsolutePath());
            myfile=file;
        }
    }

    @FXML
    void Drag_enter_event(DragEvent event) {
        // Dragboard dragboard =drag.startDragAndDrop(TransferMode.COPY);
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);

            if (file.getAbsolutePath().endsWith(".mp4")||file.getAbsolutePath().endsWith(".avi")||file.getAbsolutePath().endsWith(".flv")||file.getAbsolutePath().endsWith(".mkv")) { //用来过滤拖入类型
                event.acceptTransferModes(TransferMode.COPY);
            }
        }

    }

    @FXML
    void Drag_detected_event(MouseEvent event) {
        Dragboard dragboard = drag.startDragAndDrop(TransferMode.ANY);
    }



    @FXML
    void submit(MouseEvent event) throws FileNotFoundException {
        String name= Name.getText();
        String videoname="";
        if(name==null||"".equals(name)){
            mark.setText("name can not be empty");
            return;
        }else{
            if(myfile==null){
                mark.setText("you have to upload a video");
                return;
            }else{
                if(myfile.getAbsolutePath().endsWith(".mp4")){
                    videoname=name+".mp4";
                }else if(myfile.getAbsolutePath().endsWith(".avi")){
                    videoname=name+"image"+".avi";
                }else if(myfile.getAbsolutePath().endsWith(".flv")){
                    videoname=name+"image"+".flv";
                }else if(myfile.getAbsolutePath().endsWith(".mkv")){
                    videoname=name+"image"+".mkv";
                }
                savevideo(new FileInputStream(myfile),videoname);
                mark.setText("successfully upload");
            }
        }

    }

    private void savevideo(InputStream inputStream, String fileName) {
        String coursename=WorkUtil.mapOfLession.get(courseid).getName();
        OutputStream os = null;
        try {
            String path = new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\video\\"+coursename;
            path = java.net.URLDecoder.decode(path,"utf-8");
            byte[] bs = new byte[1024];
            int len;
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
                System.out.println("we have to make dir!!!!!!");
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Mouse_enter_event(MouseEvent event) {
        back.setStyle("-fx-background-color: #999393");
    }

    @FXML
    void Mouse_exit_event(MouseEvent event) {
        back.setStyle("-fx-background-color: black");
    }

    @FXML
    void backclick(MouseEvent event) {
        contentpane.getChildren().clear();
        try {
            contentpane.getChildren().clear();
            CourseInfoFrameController.id_lession=courseid;
            Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/CourseFrame.fxml"));
            contentpane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
