package controller;


import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
/**
 * @author ：YiMing Zhang
 * @date：Created in 2021 2021/4/30

 */

public class SlideView {
    private ImageView left;
    private ImageView right;
    private Double index1;
    private Double index2;
    private Double index3;
    private Double indexz=-50.0;
    private ArrayList<ImageView> i_list;
    private Duration time=Duration.seconds(0.5);
    public Pane getView(int w,int h){
        left= new ImageView("image/l.png");
        left.setPreserveRatio(true);
        left.setFitWidth(w/10);
        right= new ImageView("image/r.png");
        right.setPreserveRatio(true);
        right.setFitWidth(w/10);

        ImageView i1 =new ImageView("image/i.jpg");
        i1.setPreserveRatio(true);
        i1.setFitWidth(w/1.7);
        ImageView i2 =new ImageView("image/i3.jpg");
        i2.setPreserveRatio(true);
        i2.setFitWidth(w/1.7);
        ImageView i3 =new ImageView("image/i.jpg");
        i3.setPreserveRatio(true);
        i3.setFitWidth(w/1.7);

        index1=0.0-30;
        index2=(w/2)-(i1.getFitWidth()/2);
        index3=w-i3.getFitWidth()+30;

        Double height=(h/2)-(i1.prefHeight(-1)/2);



        AnchorPane a =new AnchorPane();
        a.getChildren().addAll(i1,i2,i3);

        i1.setTranslateX(index1);
        i2.setTranslateX(index2);
        i3.setTranslateX(index3);

        i1.setTranslateY(height);
        i2.setTranslateY(height);
        i3.setTranslateY(height);




        i1.setTranslateZ(0);
        i2.setTranslateZ(-50);
        i3.setTranslateZ(0);
        i_list=new ArrayList<ImageView>();
        i_list.add(i1);
        i_list.add(i2);
        i_list.add(i3);

        left.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                left_to_right();
            }
        });

        right.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                right_to_left();
            }
        });


        HBox hb= new HBox(w-(left.prefWidth(-1)*2));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(left,right);

        SubScene sub =new SubScene(a,w,h,true, SceneAntialiasing.BALANCED);
        PerspectiveCamera camera= new PerspectiveCamera();
        camera = new PerspectiveCamera();
        camera.getTransforms().addAll (
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Translate(0, 0, -50));
        sub.setCamera(camera);
        StackPane pane =new StackPane();
        pane.setMaxSize(w,h);
        pane.getChildren().addAll(sub,hb);
        pane.setStyle("-fx-background-color: black");
        return pane;
    }

    public void lefttomid_image(ImageView i){
        TranslateTransition t=new TranslateTransition();
        t.setDuration(time);
        t.setNode(i);

        t.setFromX(index1);
        t.setFromZ(0);

        t.setToX(index2);
        t.setToZ(indexz);

        t.play();
    }

    public void midtoright_image(ImageView i){
        TranslateTransition t=new TranslateTransition();
        t.setDuration(time);
        t.setNode(i);
        t.setFromX(index2);
        t.setFromZ(indexz);

        t.setToX(index3);
        t.setToZ(0);

        FadeTransition f= new FadeTransition(Duration.seconds(0.25));
        f.setFromValue(1);
        f.setToValue(0.6);

        FadeTransition f2= new FadeTransition(Duration.seconds(0.25));
        f.setFromValue(0.6);
        f.setToValue(1);

        SequentialTransition s =new SequentialTransition();
        s.getChildren().addAll(f,f2);

        ParallelTransition p =new ParallelTransition();
        p.setNode(i);
        p.getChildren().addAll(t,s);

        p.play();
    }

    public void righttoleft_image(ImageView i){
        TranslateTransition t=new TranslateTransition();
        t.setDuration(time);
        t.setNode(i);

        t.setFromX(index3);
        t.setFromZ(0);

        t.setToX(index1);
        t.setToZ(0);

        t.play();
    }


    public void right_to_left(){
        ImageView i1=this.i_list.get(0);
        ImageView i2=this.i_list.get(1);
        ImageView i3=this.i_list.get(2);

        lefttomid_image(i1);
        midtoright_image(i2);
        righttoleft_image(i3);

        this.i_list.clear();
        this.i_list.add(i3);
        this.i_list.add(i1);
        this.i_list.add(i2);
    }

    public void lefttoright_image(ImageView i){
        TranslateTransition t=new TranslateTransition();
        t.setDuration(time);
        t.setNode(i);

        t.setFromX(index1);
        t.setFromZ(0);

        t.setToX(index3);
        t.setToZ(0);

        t.play();
    }

    public void righttomid_image(ImageView i){
        TranslateTransition t=new TranslateTransition();
        t.setDuration(time);
        t.setNode(i);

        t.setFromX(index3);
        t.setFromZ(0);

        t.setToX(index2);
        t.setToZ(indexz);

        t.play();
    }

    public void midtoleft_image(ImageView i){
        TranslateTransition t=new TranslateTransition();
        t.setDuration(time);
        t.setNode(i);
        t.setFromX(index2);
        t.setFromZ(indexz);

        t.setToX(index1);
        t.setToZ(0);


        FadeTransition f= new FadeTransition(Duration.seconds(0.25));
        f.setFromValue(1);
        f.setToValue(0.6);

        FadeTransition f2= new FadeTransition(Duration.seconds(0.25));
        f.setFromValue(0.6);
        f.setToValue(1);

        SequentialTransition s =new SequentialTransition();
        s.getChildren().addAll(f,f2);

        ParallelTransition p =new ParallelTransition();
        p.setNode(i);
        p.getChildren().addAll(t,s);

        p.play();
    }

    public void left_to_right(){
        ImageView i1=this.i_list.get(0);
        ImageView i2=this.i_list.get(1);
        ImageView i3=this.i_list.get(2);

        righttomid_image(i3);
        midtoleft_image(i2);
        lefttoright_image(i1);

        this.i_list.clear();
        this.i_list.add(i2);
        this.i_list.add(i3);
        this.i_list.add(i1);
    }

}
