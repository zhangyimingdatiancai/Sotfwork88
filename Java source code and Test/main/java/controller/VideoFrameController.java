package controller;

import dao.WorkUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.print.DocFlavor;
import java.io.File;
import java.io.UnsupportedEncodingException;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @modified
 * @version:1.1
 */

public class VideoFrameController {
    @FXML
    private ImageView image1;

    private Double end = new Double(0);
    private Double current = new Double(0);


    @FXML
    void Click_video_event(MouseEvent event,String mp4name) throws UnsupportedEncodingException {
        String coursename= WorkUtil.mapOfLession.get(CourseFrameController.courseid).getName();
        String path= new File(VideoFrameController.class.getResource("").getPath()).getParent()+"\\data\\video\\"+coursename+"\\"+mp4name;
        path = java.net.URLDecoder.decode(path,"utf-8");
        java.io.File file = new java.io.File(path);
        String url = file.toURI().toString();
        Media media = new Media(url);
        final MediaPlayer  mplayer = new MediaPlayer(media);
        MediaView mView = new MediaView(mplayer);
        System.out.println(media.getSource());


        Label lbCurrentTime = new Label();
        Slider voicesl = new Slider();

        setslider(voicesl);

        Slider time = new Slider();
        time.setPrefWidth(200);

        BorderPane pane = new BorderPane();
//        System.out.println(new File(VideoFrameController.class.getResource("").getPath()).getParent()+"\\image\\1.png");
//
//        ImageView im1 =new ImageView(i1);
//        Image i2 = new Image(new File(VideoFrameController.class.getResource("").getPath()).getParent()+"\\image\\2.png");
//        ImageView im2 =new ImageView(i1);

        mView.fitWidthProperty().bind(pane.widthProperty());
        mView.fitHeightProperty().bind(pane.heightProperty().subtract(30));
        Button btnPlay = new Button("play");
        btnPlay.setId("Play");
        if(btnPlay.getText()==null){
            System.out.println("text==null");
        }
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (btnPlay.getText().equals("Play")){

                        btnPlay.setText("Pause");
                        mplayer.play();
                    }
                    else{
                        btnPlay.setText("Play");
                       // btnPlay.setGraphic(im2);
                        mplayer.pause();
                    }
                }
            }

        );

        mplayer.stop();
        btnPlay.setText("Pause");
        media = new Media(file.toURI().toString());
        mView.setMediaPlayer(mplayer);

        mplayer.setOnEndOfMedia(() -> {
            mplayer.stop();
            btnPlay.setText("Play");
        });

//        Button Replay = new Button("Replay");
//        Replay.setOnAction(e->{
//            mplayer.stop();
//            btnPlay.setText("Play");
//        });

        if(mplayer.getAudioSpectrumInterval()==0.00){
            System.out.println("Replay==null");
        }
        mplayer.setOnReady(() -> {
            end = mplayer.getStopTime().toSeconds();
        });
        mplayer.setOnEndOfMedia(() -> {
            mplayer.stop();
            mplayer.seek(Duration.ZERO);
            btnPlay.setText("Play");
        });
        mplayer.currentTimeProperty().addListener(ov->{
            current = mplayer.getCurrentTime().toSeconds();
            lbCurrentTime.setText(Seconds2Str(current)+"/"+Seconds2Str(end));
            int v= current.intValue();
            if(v%5==0){
                time.setValue(current / end *100);
            }
        });
        time.valueProperty().addListener(ov->{
            if (time.isValueChanging()){
                mplayer.seek(mplayer.getTotalDuration().multiply(time.getValue()/100));
            }
        });
        time.setOnMouseClicked(ev->{
            mplayer.seek(mplayer.getTotalDuration().multiply(time.getValue()/100));
        });
        mplayer.volumeProperty().bind(time.valueProperty().divide(100));
        mplayer.play();

        HBox paneCtl = new HBox(15);
        paneCtl.setAlignment(Pos.CENTER);
        paneCtl.getChildren().addAll(lbCurrentTime,time,btnPlay,new Label("Volume"),voicesl);
        paneCtl.setStyle("-fx-background-color: gray");
        pane.setCenter(mView);
        pane.setBottom(paneCtl);
        Stage primaryStage=new Stage();
        Scene scene = new Scene(pane,800,480);
        primaryStage.setTitle("MediaDemo");

        primaryStage.setScene(scene);
        primaryStage.show();

        mplayer.play();
        primaryStage.setOnHidden(e->{
            mplayer.stop();
        });

    }

    private String Seconds2Str(Double seconds){
        Integer Minutes=getm(seconds);
        Integer Hours=gethours(seconds);
        Integer count=getcount(seconds);
        String m=Minutes.toString();
        String s=count.toString();
        if(m==null){
            System.out.println("m==null");
        }
        if(Minutes<10){
            m="0"+m;
        }
        if(count<10){
            s="0"+s;
        }
        String str = Hours.toString()+":"+m+":"+s;
        return str;
    }

    private  Integer getcount(Double seconds){
        Integer count = seconds.intValue();
        Integer Hours = count / 3600;
        count = count % 3600;
        Integer Minutes = count /60;
        count = count % 60;
        return count;
    }

    private  Integer gethours(Double seconds){
        Integer count = seconds.intValue();
        Integer Hours = count / 3600;
        return Hours;
    }

    private  Integer getm(Double seconds){
        Integer count = seconds.intValue();
        Integer Hours = count / 3600;
        count = count % 3600;
        Integer Minutes = count /60;
        return Minutes;
    }

    public void setslider(Slider voicesl){
        voicesl.setPrefWidth(150);
        voicesl.setValue(50);
        voicesl.setShowTickLabels(true);
        voicesl.setShowTickMarks(true);
    }

}
