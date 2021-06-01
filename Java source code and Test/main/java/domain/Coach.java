package domain;

import dao.WorkUtil;

import java.util.List;

public class Coach extends User {
    private String accountProject;
    private String accountWallet;
    private String accountLevel;
    public Coach(){

    }

    public Coach(int id, String name, String phonenum, String password, String address, String gender, String accountProject, String accountWallet, String accountLevel, boolean isDeleted, List<Integer> lessionID){
        super.setDeleted(isDeleted);
        super.setName(name);
        super.setPhonenum(phonenum);
        super.setPassword(password);
        super.setAddress(address);
        super.setGender(gender);
        super.setId(id);
        super.setLessionID(lessionID);
        this.setAccountProject(accountProject);
        this.setAccountWallet(accountWallet);
        this.setAccountLevel(accountLevel);
    }

    public static void addCoach(int id,String name,String phonenum,String password,String address,String gender,String accountProject,String accountWallet,String accountLevel,boolean isDeleted,List<Integer> lessionID){
        Coach Coach = new Coach(id,name,phonenum,password,address,gender,accountProject,accountWallet,accountLevel,isDeleted,lessionID);
        WorkUtil.addUser(Coach);
    }
    public String getAccountProject() {
        return accountProject;
    }

    public void setAccountProject(String accountProject) {
        this.accountProject = accountProject;
    }

    public String getAccountWallet() {
        return accountWallet;
    }

    public void setAccountWallet(String accountWallet) {
        this.accountWallet = accountWallet;
    }
    public String getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    public static void replaceCoach(int id, Coach coach){
        WorkUtil.replaceCoach(id,coach);
    }

    public static Coach getCoach(int id){
        return WorkUtil.getCoach(id);
    }

    public static void deleteCoach(int id){
        Coach Coach = WorkUtil.getCoach(id);
        Coach.setDeleted(true);
        WorkUtil.replaceCoach(id,Coach);
    }

    public static void main(String[] args) {
        addCoach(WorkUtil.getIdOfCoach(),"ming","112133","112133","ddddd","male","baskball","2222","good",false,null);
    }
}
