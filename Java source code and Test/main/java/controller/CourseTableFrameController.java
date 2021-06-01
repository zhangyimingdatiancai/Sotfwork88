package controller;

import dao.WorkUtil;
import domain.Lession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */
public class CourseTableFrameController {
    @FXML
    private Label period1;

    @FXML
    private Label period2;

    @FXML
    private Label period3;

    @FXML
    private Label period4;

    @FXML
    private Label period5;

    @FXML
    private Label period6;

    @FXML
    private Label period7;

    @FXML
    private Label period8;

    @FXML
    private Label period9;

    @FXML
    private Label period10;


    @FXML
    private void initialize(){
        List<Integer> lessionid;
        Label[] labels={period1,period2,period3,period4,period5,period6,period7,period8,period9,period10};
        if(MainFrameController.isStu){
            lessionid=WorkUtil.mapOfStu.get(MainFrameController.id).getLessionID();
        }else{
            lessionid=WorkUtil.mapOfCoach.get(MainFrameController.id).getLessionID();
        }
        for(int i: lessionid){
            Lession lession =WorkUtil.mapOfLession.get(i);
            int period =lession.getTimeperiod();
            System.out.println(period);
            System.out.println(labels.length);
            labels[period-1].setText(lession.getName());
        }
    }
}
