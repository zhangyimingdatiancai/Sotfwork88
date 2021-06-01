package controller;

import dao.WorkUtil;
import domain.Coach;
import domain.Lession;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */

public class CreateFrameController {

    private File myfile;


    @FXML
    private Label mark;

    @FXML
    private TextField Name;

    @FXML
    private TextField Type;

    @FXML
    private TextArea Describition;

    @FXML
    private TextField image;

    @FXML
    private Button imagebutton;

    @FXML
    private Label Mark;

    @FXML
    private AnchorPane drag;

    @FXML
    private ChoiceBox<Integer> time;

    @FXML
    private ChoiceBox<Boolean> Personal;

    @FXML
    private void initialize() {
         time.setItems(FXCollections.observableArrayList(
                 1,2,3,4,5,6,7,8,9,10));
         time.setValue(1);

         Personal.setItems(FXCollections.observableArrayList(
                 true,false));

         Personal.setValue(false);
    }

    @FXML
    void Drag_drop_event(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (event.isAccepted()) {
            File file = dragboard.getFiles().get(0);
            image.setText(file.getAbsolutePath());
            myfile=file;
        }
    }



    @FXML
    void Drag_detected_event(MouseEvent event) {
        Dragboard dragboard = drag.startDragAndDrop(TransferMode.ANY);
    }

    @FXML
    void Drag_enter_event(DragEvent event) {
       // Dragboard dragboard =drag.startDragAndDrop(TransferMode.COPY);
       Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);

            if (file.getAbsolutePath().endsWith(".png")||file.getAbsolutePath().endsWith(".jpg")) { //用来过滤拖入类型
                event.acceptTransferModes(TransferMode.COPY);
            }
        }

    }

    @FXML
    void submit(MouseEvent event) throws FileNotFoundException {
        String name= Name.getText();
        if(name==null||"".equals(name)){
            Mark.setText("name can not be empty");
            return;
        }else{
            for(Lession l:WorkUtil.mapOfLession.values()){
                if(l.getName().equals(name)){
                    Mark.setText("already have the name");
                    return;
                }
            }
        }
        String type= Type.getText();
        if(type==null||"".equals(type)){
            Mark.setText("type can not be empty");
            return;
        }
        int num = time.getValue();
        List<Integer> le=WorkUtil.mapOfCoach.get(MainFrameController.id).getLessionID();
        if(le==null){
        }else{
            for(int w:le){
                if(num==WorkUtil.mapOfLession.get(w).getTimeperiod()){
                    Mark.setText("you already the other course on this time");
                    return;
                }
            }
        }

        String des= Type.getText();
        if(des==null||"".equals(des)){
            Mark.setText("describtion can not be empty");
            return;
        }
        String imagename;
        if(myfile==null){
            imagename="";
            System.out.println("no pic");
        }else{
            if(myfile.getAbsolutePath().endsWith(".png")){
                imagename=name+"image"+".png";
            }else{
                imagename=name+"image"+".jpg";
            }
            save(new FileInputStream(myfile),imagename);
            System.out.println("get pic");
        }
        int id=WorkUtil.getIdOfLession();
        Lession l =new Lession(id,name,des,"",imagename,MainFrameController.id,type,num,false,false,0);

        if(Personal.getValue()){
             l.setPersonal_course(true);
             l.setOwner(false);
        }else{
            l.setPersonal_course(false);
            l.setOwner(false);
        }
        Coach c=WorkUtil.mapOfCoach.get(MainFrameController.id);
        if(c.getLessionID()==null){
            c.setLessionID(new ArrayList<Integer>(id));
        }else{
            List<Integer> li=c.getLessionID();
            li.add(id);
            System.out.println(id);
            c.setLessionID(li);
        }
        WorkUtil.replaceCoach(MainFrameController.id,c);
        WorkUtil.addLession(l);
        Mark.setText("successfully");
    }

    @FXML
    void button_clicked(MouseEvent event)  {
        Stage stage= new Stage();
        FileChooser chooser= new FileChooser();
        chooser.setTitle("choose onew file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("picture","*.jpg","*.png")
        );
        File file= chooser.showOpenDialog(stage);
        if(file==null){
            return;
        }else{
            image.setText(file.getAbsolutePath());
            myfile=file;
        }
    }

    private void save(InputStream inputStream, String fileName) {

        OutputStream os = null;
        try {
            String path = new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image";
            byte[] bs = new byte[1024];
            int len;
            path = java.net.URLDecoder.decode(path,"utf-8");
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
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

}
