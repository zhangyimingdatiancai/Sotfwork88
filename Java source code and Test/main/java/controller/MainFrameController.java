package controller;

import dao.WorkUtil;
import domain.Lession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */
public class MainFrameController {

    private static String DEFAULT_IMAGE=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image";
    static {
        try {
            DEFAULT_IMAGE = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public MainFrameController(){

    }

    public static int id=100000;
    public static boolean isStu=false;

    public static AnchorPane contentPaneStatic;

    @FXML
    private void initialize() throws IOException {
        System.out.println("程序启动");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/PracticeMainFrame.fxml"));
        contentPane.getChildren().add(root);
        contentPaneStatic=contentPane;
        if(!isStu){
            LessionInfo.setText("             CreateCourse");
        }
    }



    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane stagePane;

    @FXML
    private Button PracticeMain;
    private boolean PracticeMainIsselected=true;

    @FXML
    private Button LessionInfo;
    private boolean LessionInfoIsselected=false;

    @FXML
    private Button MyLessions;
    private boolean MyLessionsIsselected=false;

    @FXML
    private Button TimeTable;
    private boolean TimeTableIsselected=false;

    @FXML
    private Button personalinfo;
    private boolean personalinfoIsselected=false;


    @FXML
    private Button Forum;
    private boolean ForumIsselected=false;


    @FXML
    private Button minButton;

    @FXML
    private Button amxButton;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane rootPane;


    private Stage stage;

    private Stage getStage() { if (stage == null)
    {
        stage = (Stage) rootPane.getScene().getWindow();
    }
        return stage;
    }




    @FXML
    void amx_event(ActionEvent event) {
//        if(stage==null){
//            stage=getStage();
//        }
//        if(!stage.isMaximized())
//           stage.setMaximized(true);
//        else{
//            stage.setMaximized(false);
////            stage.setWidth(880);
////            stage.setHeight(600);
//        }
    }

    @FXML
    void close_event(ActionEvent event) {
        if(stage==null){
            stage=getStage();
        }
        stage.close();
    }

    @FXML
    void min_event(ActionEvent event) {
        if(stage==null){
            stage=getStage();
        }
         stage.setIconified(true);
    }


    @FXML
    void do_practiceMenu_event() throws IOException {
        if(!PracticeMainIsselected){
            setButton();
            PracticeMain.setTextFill(Color.WHITE);
            PracticeMainIsselected=setSelect();
            contentPane.getChildren().clear();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PracticeMainFrame.fxml"));
            contentPane.getChildren().add(root);
        }

    }

    @FXML
    void do_coursetable_event(ActionEvent event) throws IOException {
        if(!TimeTableIsselected){
            setButton();
            TimeTable.setTextFill(Color.WHITE);
            TimeTableIsselected=setSelect();
            contentPane.getChildren().clear();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CourseTableFrame.fxml"));
            contentPane.getChildren().add(root);
        }

    }

    @FXML
    void do_mylesssions_event() throws UnsupportedEncodingException {
        if(!MyLessionsIsselected){
            setButton();
            MyLessions.setTextFill(Color.WHITE);
            //System.out.println("11111111111");
            MyLessionsIsselected=setSelect();

            contentPane.getChildren().clear();
            if(isStu){
                contentPane.getChildren().add(ViewofCourseOfStu(WorkUtil.mapOfStu.get(MainFrameController.id).getLessionID(),false));
            }else{
                contentPane.getChildren().add(ViewofCourseOfStu(WorkUtil.mapOfCoach.get(MainFrameController.id).getLessionID(),false));
            }

        }
    }

    public static ScrollPane ViewofCourseOfStu(List<Integer> id,boolean isall) throws UnsupportedEncodingException {
        ScrollPane sc= new ScrollPane();
        sc.setPrefHeight(810);
        sc.setPrefWidth(820);

        GridPane gr = new GridPane();
        gr.setPrefHeight(810);
        gr.setPrefWidth(810);
        sc.setContent(gr);
        gr.setHgap(30);
        gr.setVgap(20);
        gr.setPadding(new Insets(30,30,30,30));
      //  List<Integer> id=WorkUtil.mapOfStu.get(MainFrameController.id).getLessionID();


        File file=new File(DEFAULT_IMAGE);
        ArrayList<String> filelist=new ArrayList<String>(Arrays.asList(file.list()));
//            int Hnum=id.size()/5;
//            int Vnum=id.size()%5;
        int j=0,k=0;
        if(id==null||id.size()==0){
//            System.out.println(WorkUtil.mapOfStu.get(MainFrameController.id).getLessionID());
//            System.out.println(WorkUtil.mapOfStu.get(MainFrameController.id).getPhonenum());
            Label l =new Label("no course now!!!");

              l.setTextFill(Paint.valueOf("white"));
              gr.add(l,0,0);
              gr.setStyle("-fx-background-color: black;");
        }else{
            for (int i:id) {
                Lession le =WorkUtil.mapOfLession.get(i);
                if(le!=null){
                    String path=le.getImageName();
                    path = java.net.URLDecoder.decode(path,"utf-8");
                    if(path==null||"".equals(path)){
                        path= DEFAULT_IMAGE+"\\default.jpg";
                    }else{
                        if(filelist.contains(path)){
                            path= DEFAULT_IMAGE+"\\"+path;
                        }else{
                            path= DEFAULT_IMAGE+"\\default.jpg";
                        }
                    }
                    System.out.println(path);
                    path="file:"+path;

                    ImageView im=new ImageView(new Image(path));
                    ColorAdjust blackout = new ColorAdjust();

                    blackout.setBrightness(-0.3);
                    im.setEffect(blackout);
                    im.setCache(true);
                    im.setCacheHint(CacheHint.SPEED);

                    im.setFitWidth(100);
                    im.setFitHeight(125);
                    VBox v =new VBox();
                    v.setPrefWidth(100);
                    v.setPrefHeight(140);
                    Label label =new Label(WorkUtil.mapOfLession.get(i).getName());
                    label.setTextFill(Paint.valueOf("white"));
                    v.getChildren().add(im);
                    v.setAlignment(Pos.CENTER);
                    v.getChildren().add(label);
                    v.setStyle("-fx-background-color: black;");

                    if(isall){
                        if(le.getPersonal_course()&&le.getOwner()){
                            continue;
                        }
                        v.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                if(event.getButton() == MouseButton.PRIMARY )
                                {
                                    try {
                                        CourseInfoFrameController.contentpane=contentPaneStatic;
                                        contentPaneStatic.getChildren().clear();
                                        CourseInfoFrameController.id_lession=i;
                                        Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/CourseInfoFrame.fxml"));
                                        contentPaneStatic.getChildren().add(root);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                    }else{
                        v.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event)
                            {
                                if(event.getButton() == MouseButton.PRIMARY )
                                {
                                    try {
                                        course_stu_event(WorkUtil.mapOfLession.get(i).getId(),WorkUtil.mapOfLession.get(i).getName());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }


                    v.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event)
                        {
                            ImageView im= (ImageView)v.getChildren().get(0);
                            //.setStyle("-fx-background-color: #a7a8a2;");
                            ColorAdjust blackout = new ColorAdjust();

                            blackout.setBrightness(-0.3);
                            im.setEffect(blackout);
                            im.setCache(true);
                            im.setCacheHint(CacheHint.SPEED);
                        }
                    });
                    v.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event)
                        {
                            ImageView im= (ImageView)v.getChildren().get(0);
                            //.setStyle("-fx-background-color: #a7a8a2;");
                            ColorAdjust blackout = new ColorAdjust();

                            blackout.setBrightness(0);
                            im.setEffect(blackout);
                            im.setCache(true);
                            im.setCacheHint(CacheHint.SPEED);

                        }
                    });

                    gr.add(v,k,j);
                    gr.setStyle("-fx-background-color: black;");
                    k++;
                    if(k==5){
                        k=0;
                        j++;
                    }
                }
            }
        }
        return sc;
    }

    static void  course_stu_event(int id,String name) throws IOException {
        CourseFrameController.contentpane=contentPaneStatic;
        contentPaneStatic.getChildren().clear();
        CourseFrameController.courseid=id;
       // CourseFrameController.coursename=name;
        Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/CourseFrame.fxml"));
        contentPaneStatic.getChildren().add(root);
    }

    @FXML
    void do_personalinfo_event(ActionEvent event) throws IOException {
        if(!personalinfoIsselected){
            setButton();
            personalinfo.setTextFill(Color.WHITE);

            personalinfoIsselected=setSelect();

            contentPaneStatic.getChildren().clear();
            Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/info_show.fxml"));
            contentPaneStatic.getChildren().add(root);

        }

    }


    @FXML
    void do_forum_event(ActionEvent event) throws IOException {
        if(!ForumIsselected){
            setButton();
            Forum.setTextFill(Color.WHITE);

            ForumIsselected=setSelect();
            contentPaneStatic.getChildren().clear();
            forum_controller.contentpane=contentPane;
            Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/forum.fxml"));
            contentPaneStatic.getChildren().add(root);


        }

    }

    @FXML
    void do_lessionInfo_event() throws IOException {
        if(!LessionInfoIsselected){
            setButton();
            LessionInfo.setTextFill(Color.WHITE);



            LessionInfoIsselected=setSelect();

            if (isStu){
                List<Integer> list =new ArrayList<>();
                for(Lession w:WorkUtil.mapOfLession.values()){
                    list.add(w.getId());
                }

                contentPane.getChildren().clear();
                contentPane.getChildren().add(ViewofCourseOfStu(list,true));
            }else{
                contentPane.getChildren().clear();
                Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/CreateCourseFrame.fxml"));
                contentPaneStatic.getChildren().add(root);

            }


        }
    }

    @FXML
    void mouse_enter_event(MouseEvent event) {
        Button w=(Button)event.getSource();

        w.setTextFill(Color.WHITE);
    }



    @FXML
    void mouse_out_event(MouseEvent event) {
        Button w=(Button)event.getSource();
        if(MyLessionsIsselected){
            setButton();
            MyLessions.setTextFill(Color.WHITE);
        }else if(LessionInfoIsselected){
            setButton();
            LessionInfo.setTextFill(Color.WHITE);
        }else if(PracticeMainIsselected){
            setButton();
            PracticeMain.setTextFill(Color.WHITE);
        }else if(TimeTableIsselected){
            setButton();
            TimeTable.setTextFill(Color.WHITE);
        }else if(personalinfoIsselected){
            setButton();
            personalinfo.setTextFill(Color.WHITE);
        }else if(ForumIsselected){
            setButton();
            Forum.setTextFill(Color.WHITE);
        }

    }

    private boolean setSelect(){
        PracticeMainIsselected=false;
        LessionInfoIsselected=false;
        MyLessionsIsselected=false;
        TimeTableIsselected=false;
        personalinfoIsselected=false;
        ForumIsselected=false;
        return true;
    }

    private void setButton(){
        TimeTable.setTextFill(Color.rgb(192,192,192));
        PracticeMain.setTextFill(Color.rgb(192,192,192));
        LessionInfo.setTextFill(Color.rgb(192,192,192));
        MyLessions.setTextFill(Color.rgb(192,192,192));
        personalinfo.setTextFill(Color.rgb(192,192,192));
        Forum.setTextFill(Color.rgb(192,192,192));
    }


}
