package domain;

import dao.WorkUtil;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String accountType;
    private String accountBalance;
    private int accountPoints;

    public int getViplevel() {
        return viplevel;
    }

    public void setViplevel(int viplevel) {
        this.viplevel = viplevel;
    }

    private int viplevel;
    public Student(){

    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getAccountPoints() {
        return accountPoints;
    }

    public void setAccountPoints(int accountPoints) {
        this.accountPoints = accountPoints;
    }

    public void changeAccountPoints(int change) {
        this.accountPoints += change;
    }


    public Student(int id, String name, String phonenum, String password, String address, String gender, String accountType, String accountBalance,List<Integer> lessionID){
        super.setDeleted(false);
        super.setName(name);
        super.setPhonenum(phonenum);
        super.setPassword(password);
        super.setAddress(address);
        super.setGender(gender);
        super.setId(id);
        this.setAccountType(accountType);
        this.setAccountBalance(accountBalance);
        this.setAccountPoints(accountPoints);
        this.setViplevel(0);
        this.setLessionID(lessionID);
    }

    public static void addStudent(int id,String name,String phonenum,String password,String address,String gender,String accountType,String accountBalance){
        Student student = new Student(id,name,phonenum,password,address,gender,accountType,accountBalance,null);
        student.setLessionID(null);
        student.setViplevel(0);



        WorkUtil.addUser(student);
    }

    public static void replaceStudent(int id, Student stu){
        WorkUtil.replaceStu(id,stu);
    }

    public static Student getStudent(int id){
        return WorkUtil.getStudent(id);
    }

    public static void deleteStudent(int id){
        Student student = WorkUtil.getStudent(id);
        student.setDeleted(true);
        WorkUtil.replaceStu(id,student);
    }

    public static void main(String[] args) {
        Student s=new Student(WorkUtil.getIdOfStu(),"ming","112133","112133","dddd","female","weixin","32",null);
        s.setLessionID(new ArrayList<Integer>());
        s.getLessionID().add(300000);
        s.getLessionID().add(300001);
        s.getLessionID().add(300002);
        WorkUtil.addUser(s);
    }
}
