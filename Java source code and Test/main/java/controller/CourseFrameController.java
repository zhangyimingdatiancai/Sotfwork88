package controller;

import dao.VideoImage;
import dao.WorkUtil;
import domain.Coach;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
/**
 * @authorYiMing Zhang
 * @date Created in 2021 2021/4/30

 */

public class CourseFrameController {

    public static  AnchorPane contentpane;

    public static int courseid;

   // public static String coursename;

    public final static String video_standard = "(mp4|flv|avi|rm|rmvb|wmv)";

    private  static String videopath=new File(VideoImage .class.getResource("").getPath()).getParent()+"\\data\\video\\";


    @FXML
    private AnchorPane sec;

    @FXML
    private ScrollPane sc;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label coach;

    @FXML
    private Label personal;

    private static  String DEFAULT_IMAGE=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image";

    static {
        try {
            DEFAULT_IMAGE = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\image","utf-8");
            videopath = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\video\\","utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button upload;

    @FXML
    private Button backbutton;

    @FXML
    private void initialize() throws IOException, InterruptedException {

        if(MainFrameController.isStu){
            upload.setDisable(true);
            upload.setVisible(false);
        }

    //    backbutton.setVisible(false);
        String coursename=WorkUtil.mapOfLession.get(courseid).getName();
        name.setText(WorkUtil.mapOfLession.get(courseid).getName());
        Coach co=WorkUtil.mapOfCoach.get(WorkUtil.mapOfLession.get(courseid).getCoachid());
        if(co==null){
            coach.setText("Coach: not know");
        }else{
            coach.setText("Coach: "+WorkUtil.mapOfCoach.get(WorkUtil.mapOfLession.get(courseid).getCoachid()).getName());
        }
        if(WorkUtil.mapOfLession.get(courseid).getPersonal_course()){
            if(WorkUtil.mapOfLession.get(courseid).getOwner()){
                String stu= WorkUtil.mapOfStu.get(WorkUtil.mapOfLession.get(courseid).getPersonal_id()).getName();
                personal.setText("Personal Training: yes with Student "+stu);
            }else{
                personal.setText("Personal Training: yes but not start");
            }

        }else{
            personal.setText("Personal Training: No");
        }
        File file=new File(DEFAULT_IMAGE);
        ArrayList<String> filelist=new ArrayList<String>(Arrays.asList(file.list()));
        String path=WorkUtil.mapOfLession.get(courseid).getImageName();
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
        path = java.net.URLDecoder.decode(path,"utf-8");
        Image image0= new Image(path);
        image.setImage(image0);
       // ScrollPane sc= new ScrollPane();
        sc.setPrefHeight(810);
        sc.setPrefWidth(880);

        GridPane gr = new GridPane();
        gr.setStyle("-fx-background-color: black;");
        gr.setPrefHeight(610);
        gr.setPrefWidth(880);
        sc.setContent(gr);
        gr.setHgap(30);
        gr.setVgap(20);
        gr.setPadding(new Insets(30,30,30,30));
           File directory= new File(videopath+coursename);
        System.out.println(directory.getPath());
           if(!directory.exists()){
               directory.mkdirs();
           }
        File[] videos = directory.listFiles();
        System.out.println(videos.length);
        int j=0,k=0;

        for (int i = 0; i < videos.length; i++) {
            if (videos[i].isFile()) {
                 Pattern p = Pattern.compile(video_standard);
                if(p.matcher(videos[i].getName()).find()){

                    System.out.println(videos[i].getName());
                    String name=coursename+videos[i].getName().split("\\.")[0];
                    if(VideoImage.processImg(videos[i].getPath(),name)){
                        Thread.sleep(500);
                    }

                    System.out.println("file:"+VideoImage.picture_path+"\\"+name+".jpg");
                    ImageView im=new ImageView(new Image("file:"+VideoImage.picture_path+"\\"+name+".jpg"));
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
                    v.setAlignment(Pos.CENTER);

                    Label label =new Label(videos[i].getName().split("\\.")[0]);
                    label.setTextFill(Paint.valueOf("white"));
                    v.getChildren().add(im);

                    v.getChildren().add(label);
                    v.setStyle("-fx-background-color: black;");
                    String videoname=videos[i].getName();


                    v.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event)
                        {
                            ImageView im= (ImageView)v.getChildren().get(0);
                            ColorAdjust blackout = new ColorAdjust();
                            blackout.setBrightness(0);
                            im.setEffect(blackout);
                            im.setCache(true);
                            im.setCacheHint(CacheHint.SPEED);

                        }
                    });

                    v.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event)
                        {
                            try {
                                new VideoFrameController().Click_video_event(event,videoname);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    });

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
                    gr.add(v,k,j);
                    k++;
                    if(k==5){
                        k=0;
                        j++;
                    }
                }


            }
            if (videos[i].isDirectory()) {
            }
        }



    }
    @FXML
    private AnchorPane courseframe;



    @FXML
    void Mouse_enter_event(MouseEvent event) {
        backbutton.setStyle("-fx-background-color: #999393");

    }

    @FXML
    void Mouse_exit_event(MouseEvent event) {
        backbutton.setStyle("-fx-background-color: black");
    }

    @FXML
    void mouse_enter(MouseEvent event) {
        upload.setStyle("-fx-background-color: #999393");
    }

    @FXML
    void mouse_exit(MouseEvent event) {
        upload.setStyle("-fx-background-color: black");
    }

    @FXML
    void back_event(MouseEvent event) throws IOException {
        contentpane.getChildren().clear();
        if(MainFrameController.isStu){
            contentpane.getChildren().add(MainFrameController.ViewofCourseOfStu(WorkUtil.mapOfStu.get(MainFrameController.id).getLessionID(),false));
        }else{
            contentpane.getChildren().add(MainFrameController.ViewofCourseOfStu(WorkUtil.mapOfCoach.get(MainFrameController.id).getLessionID(),false));
        }
    }


    @FXML
    void uploadvideo(MouseEvent event) throws IOException {
         UploadVideoFrameController.courseid=courseid;
         UploadVideoFrameController.contentpane=contentpane;
         contentpane.getChildren().clear();
         Parent root = FXMLLoader.load(MainFrameController.class.getResource("/fxml/UploadVideoFrame.fxml"));
         contentpane.getChildren().add(root);
    }

    public static void main(String[] args) {
           String i="sss.mp4";
         System.out.println(i.split("\\.")[0]);;
    }

}

