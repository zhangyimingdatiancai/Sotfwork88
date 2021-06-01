package domain;

import dao.WorkUtil;
/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @version:1.1
 */
public class Lession {
    private int id;
    private String name;
    private String deacribition;
    private String videoName;
    private String imageName;
    private int coachid;
    private int timeperiod;
    private String type;
    private boolean isDeleted;



    public int getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(int personal_id) {
        this.personal_id = personal_id;
    }

    public boolean getPersonal_course() {
        return personal_course;
    }

    public void setPersonal_course(boolean personal_course) {
        this.personal_course = personal_course;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    private boolean personal_course;
    private boolean owner;
    private int personal_id;


    public Lession(int id, String name, String deacribition, String videoName, String imageName, int coachid, String type,int timeperiod,boolean personal_course,boolean owner,int personal_id) {
        this.id = id;
        this.name = name;
        this.deacribition = deacribition;
        this.videoName = videoName;
        this.imageName = imageName;
        this.coachid = coachid;
        this.type = type;
        this.isDeleted=false;
        this.timeperiod=timeperiod;
        this.personal_course=personal_course;
        this.owner=owner;
        this.personal_id=personal_id;
    }



    public int getTimeperiod() {
        return timeperiod;
    }

    public void setTimeperiod(int timeperiod) {
        this.timeperiod = timeperiod;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeacribition() {
        return deacribition;
    }

    public void setDeacribition(String deacribition) {
        this.deacribition = deacribition;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getCoachid() {
        return coachid;
    }

    public void setCoachid(int coachid) {
        this.coachid = coachid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }







}
