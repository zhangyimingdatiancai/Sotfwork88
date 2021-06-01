package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


import java.util.ArrayList;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */

public class PracticeMainFrameController {

    @FXML
    private AnchorPane a;

    @FXML
    private AnchorPane b;

    @FXML
    private AnchorPane c;

    @FXML
    private void initialize(){

        b.getChildren().add(new SlideView().getView(805,250));
    }



}
