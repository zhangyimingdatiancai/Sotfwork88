package controller;

import dao.WorkUtil;
import domain.Coach;
import domain.Lession;
import domain.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */

public class CourseInfoFrameController {

    private static  String DEFAULT_IMAGE=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image";
    static {
        try {
            DEFAULT_IMAGE = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static AnchorPane contentpane;

    @FXML
    private Label name;

    @FXML
    private ImageView image;

    @FXML
    private Label coach;

    @FXML
    private Label content;

    @FXML
    private Button back;

    @FXML
    private Button subscribe;

    @FXML
    private Label personal;


    public static int id_lession;
    @FXML
    private void initialize(){
        name.setText(WorkUtil.mapOfLession.get(id_lession).getName());
        Coach co=WorkUtil.mapOfCoach.get(WorkUtil.mapOfLession.get(id_lession).getCoachid());
        if(co==null){
            coach.setText("Coach: not know");
        }else{
            coach.setText("Coach: "+WorkUtil.mapOfCoach.get(WorkUtil.mapOfLession.get(id_lession).getCoachid()).getName());
        }
        System.out.println("222222222222"+WorkUtil.mapOfLession.get(id_lession).getPersonal_course());
        if(WorkUtil.mapOfLession.get(id_lession).getPersonal_course()){
            personal.setText("Personal Training: Yes");
        }else{
            personal.setText("Personal Training: No");
        }
        File file=new File(DEFAULT_IMAGE);
        ArrayList<String> filelist=new ArrayList<String>(Arrays.asList(file.list()));
        String path=WorkUtil.mapOfLession.get(id_lession).getImageName();
        if(path==null){
            path= DEFAULT_IMAGE+"\\default.jpg";
        }else{
            if(filelist.contains(path)){
                path= DEFAULT_IMAGE+"\\"+path;
            }else{
                path= DEFAULT_IMAGE+"\\default.jpg";
            }
        }
        path="file:"+path;
        Image image0= new Image(path);
        image.setImage(image0);
        System.out.println(WorkUtil.mapOfLession.get(id_lession).getName());
        System.out.println(WorkUtil.mapOfLession.get(id_lession).getDeacribition());
        content.setText(WorkUtil.mapOfLession.get(id_lession).getDeacribition());

        if(WorkUtil.mapOfStu.get(MainFrameController.id).getLessionID().contains(id_lession)){
            subscribe.setText("Already have");
        }else{
            subscribe.setText("Subscribe");
        }
    }


    @FXML
    void Back_event(MouseEvent event) throws UnsupportedEncodingException {
        contentpane.getChildren().clear();
        List<Integer> list =new ArrayList<>();
        for(Lession w:WorkUtil.mapOfLession.values()){
            list.add(w.getId());
        }
        contentpane.getChildren().add(MainFrameController.ViewofCourseOfStu(list,true));
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
    void Mouse_enter_event2(MouseEvent event) {
        subscribe.setStyle("-fx-background-color: #999393");

    }

    @FXML
    void Mouse_exit_event2(MouseEvent event) {
        subscribe.setStyle("-fx-background-color: black");
    }

    @FXML
    void Subscribe_event(MouseEvent event) {

        Student student=WorkUtil.mapOfStu.get(MainFrameController.id);
        if(!student.getLessionID().contains(id_lession)){
            int period=WorkUtil.mapOfLession.get(id_lession).getTimeperiod();
            boolean ispersonal=WorkUtil.mapOfLession.get(id_lession).getPersonal_course();
            int coachid=WorkUtil.mapOfLession.get(id_lession).getCoachid();
            for(int i:student.getLessionID()){
                Lession lesson=WorkUtil.mapOfLession.get(i);
                if(lesson.getTimeperiod()==period){
                    subscribe.setText("time occupied");
                    return;
                }
                if(ispersonal){
                    if(lesson.getPersonal_course()&&lesson.getCoachid()==coachid){
                        subscribe.setText("have the personal course with this coach");
                        return;
                    }
                }
            }
            student.getLessionID().add(id_lession);
            if(student.getLessionID().size()==1){
                student.setViplevel(1);
            }else if(student.getLessionID().size()>=3){
                student.setViplevel(2);
                if(student.getLessionID().size()>=5){
                    student.setViplevel(3);
                }
            }
            Student.replaceStudent(student.getId(), student);
            if(WorkUtil.mapOfLession.get(id_lession).getPersonal_course()){
                Lession le=WorkUtil.mapOfLession.get(id_lession);
                le.setOwner(true);
                le.setPersonal_id(student.getId());
                WorkUtil.replaceLession(le.getId(),le);
            }
            subscribe.setText("Already have");
        }

    }

}
