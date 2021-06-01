package dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.Coach;
import domain.Lession;
import domain.Student;
import domain.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @modified
 * @version:1.1
 */
public class WorkUtil implements WorkUtilInterface{
    private static String DEFAULT_COACHPATH=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\Coach.txt";
    private static String DEFAULT_STUPATH=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\Student.txt";
    private static String DEFAULT_LESSION=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\Lession.txt";
    private static final int DEFAULT_COACHID=100000;
    private static final int DEFAULT_STUID=200000;
    private static final int DEFAULT_LESSIONID=300000;
    private static List<Coach> coachList;
    private static List<Student> stuList;
    static {
        try {
            DEFAULT_COACHPATH = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\Coach.txt","utf-8");
            DEFAULT_STUPATH = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\Student.txt","utf-8");
            DEFAULT_LESSION = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\Lession.txt","utf-8");
            System.out.println(DEFAULT_COACHPATH);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private static int idOfCoach;
    private static int idOfStu;
    private static int idOfLession;


    public static Map<Integer, Lession> mapOfLession;
    public static Map<Integer, Coach> mapOfCoach;
    public static Map<Integer, Student> mapOfStu;
    public static Map<String, Coach> mapOfCoachForLogin;
    public static Map<String, Student> mapOfStuForLogin;
    static {
        formCoach();
        formStudent();
        formMapOfCoach();
        formMapOfStu();
        formMapOfLession();
        formMapOfLession();
    }

    public static int getIdOfCoach() {
        return idOfCoach++;
    }

    public static int getIdOfStu() {
        return idOfStu++;
    }

    public static int getIdOfLession() {
        return idOfLession++;
    }

    private static void writeCoach(){
        coachList= new ArrayList<>(mapOfCoach.values());
        writeFile(JSONArray.toJSONString(coachList),DEFAULT_COACHPATH);
    }

    private static void writeStu(){
        stuList= new ArrayList<>(mapOfStu.values());
        writeFile(JSONArray.toJSONString(stuList),DEFAULT_STUPATH);
    }

    private static void formCoach(){
        String coach=readFile(DEFAULT_COACHPATH);
        coachList=new ArrayList<Coach>();
        if(!"".equals(coach)){
            coachList=JSONArray.parseArray(coach).toJavaList(Coach.class);
        }
    }
    private static void formStudent(){
        String student=readFile(DEFAULT_STUPATH);
        stuList=new ArrayList<Student>();
        System.out.println(student);
        if(!"".equals(student)){
            stuList=JSONArray.parseArray(student).toJavaList(Student.class);
        }
        System.out.println(stuList);
    }
    private static void formMapOfCoach(){
        mapOfCoachForLogin=new HashMap<String, Coach>();
        mapOfCoach=new HashMap<Integer, Coach>();
        if(coachList.size()==0){
            idOfCoach=DEFAULT_COACHID;
        }else{
            idOfCoach=DEFAULT_COACHID;
            for(int i=0;i<coachList.size();i++){
                if(!coachList.get(i).getDeleted()){
                    mapOfCoachForLogin.put(coachList.get(i).getName(),coachList.get(i));
                }
                mapOfCoach.put(coachList.get(i).getId(),coachList.get(i));
                if(coachList.get(i).getId()+1>idOfCoach){
                    idOfCoach=coachList.get(i).getId()+1;
                }
            }
        }
    }
    private static void formMapOfStu(){
        mapOfStuForLogin=new HashMap<String, Student>();
        mapOfStu=new HashMap<Integer, Student>();
        if(stuList.size()==0){
            idOfStu=DEFAULT_STUID;
        }else{
            idOfStu=DEFAULT_STUID;
            for(int i=0;i<stuList.size();i++){
                if(!stuList.get(i).getDeleted()){
                    mapOfStuForLogin.put(stuList.get(i).getName(),stuList.get(i));
                }
                mapOfStu.put(stuList.get(i).getId(),stuList.get(i));
                if(stuList.get(i).getId()+1>idOfStu){
                    idOfStu=stuList.get(i).getId()+1;
                }
            }
        }
    }

    private static void formMapOfLession(){
        mapOfLession=new HashMap<Integer, Lession>();
        String Lession=readFile(DEFAULT_LESSION);
        List<domain.Lession> lessionList=new ArrayList<domain.Lession>();
        if(!"".equals(Lession)){
            lessionList= JSONArray.parseArray(Lession).toJavaList(domain.Lession.class);
        }
        if(lessionList.size()==0){
            idOfLession=DEFAULT_LESSIONID;
        }else{
            idOfLession=DEFAULT_LESSIONID;
            for(int i=0;i<lessionList.size();i++){
                mapOfLession.put(lessionList.get(i).getId(),lessionList.get(i));
                if(lessionList.get(i).getId()+1>idOfLession){
                    idOfLession=lessionList.get(i).getId()+1;
                }
            }
        }
    }
    public static String readFile(String path){
        File file = new File(path);
        BufferedReader brReader = null;
        String data="";
        if (file.isFile() && file.exists()){
            try {
                brReader = new BufferedReader(new FileReader(file));
                StringBuilder result = new StringBuilder();
                String s;
                while ((s = brReader.readLine()) != null)
                    result.append(s);
                data=result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public String readlession(){
        File file = new File(DEFAULT_LESSION);
        BufferedReader brReader = null;
        String data="";
        if (file.isFile() && file.exists()){
            try {
                brReader = new BufferedReader(new FileReader(file));
                StringBuilder result = new StringBuilder();
                String s;
                while ((s = brReader.readLine()) != null)
                    result.append(s);
                data=result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    public static void writeFile(String data,String path){
        try {
            File file = new File(path);
            if (!file.exists()) {
                File dirOfData = new File(file.getParent());
                dirOfData.mkdirs();
                file.createNewFile();
            }
            System.out.println(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.flush();
            bw.close();
            System.out.println("writing sussfully!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addUser(User user){
        if(user.getClass().getName().contains("Coach")){
            System.out.println("writing coach");
            coachList.add((Coach)user);
            String jsonData=JSONArray.toJSONString(coachList);
            writeFile(jsonData,DEFAULT_COACHPATH);
            mapOfCoachForLogin.put(user.getName(),(Coach) user);
            mapOfCoach.put(user.getId(),(Coach)user);
        }else if(user.getClass().getName().contains("Student")){
            stuList.add((Student) user);
            String jsonData=JSONArray.toJSONString(stuList);
            writeFile(jsonData,DEFAULT_STUPATH);
            mapOfStuForLogin.put(user.getName(),(Student)user);
            mapOfStu.put(user.getId(),(Student)user);
        }
    }

    public static void addLession(Lession lession){
        mapOfLession.put(lession.getId(),lession);
        ArrayList<Lession> lessionlist= new ArrayList<>(mapOfLession.values());
        String jsonData=JSONArray.toJSONString(lessionlist);
        writeFile(jsonData,DEFAULT_LESSION);
    }
    public static boolean hasMember(String name){
       if(mapOfCoachForLogin.containsKey(name)){
           return true;
       }
       return mapOfStuForLogin.containsKey(name);
    }

    public static boolean loginCheckForCoach(String name,String password){
        if(!mapOfCoachForLogin.containsKey(name)){
            return false;
        }else{
            return password.equals(mapOfCoachForLogin.get(name).getPassword());
        }
    }

    public static boolean loginCheckForStu(String name,String password){
        if(!mapOfStuForLogin.containsKey(name)){
            return false;
        }else{
            return password.equals(mapOfStuForLogin.get(name).getPassword());
        }
    }

    public static void replaceStu(int id, Student stu){
        if(!mapOfStu.containsKey(id)){
            return;
        }
        mapOfStu.replace(id,stu);
        writeStu();


        mapOfStuForLogin=new HashMap<>();
        stuList=new ArrayList<>(mapOfStu.values());
        for (Student s: mapOfStu.values()
             ) {
            if(!s.getDeleted()){
                mapOfStuForLogin.put(s.getName(),s);
            }
        }
    }

    public static void replaceCoach(int id, Coach coach){
        if(!mapOfCoach.containsKey(id)){
            return;
        }
        mapOfCoach.replace(id,coach);
        writeCoach();

        mapOfCoachForLogin=new HashMap<>();
        coachList=new ArrayList<>(mapOfCoach.values());
        for (Coach s: mapOfCoach.values()
        ) {
            if(!s.getDeleted()){
                mapOfCoachForLogin.put(s.getName(),s);
            }
        }
    }

    public static void replaceLession(int id, Lession lession){
        if(!mapOfLession.containsKey(id)){
            return;
        }
        mapOfLession.replace(id,lession);
        ArrayList<Lession> lessionlist= new ArrayList<>(mapOfLession.values());
        String jsonData=JSONArray.toJSONString(lessionlist);
        writeFile(jsonData,DEFAULT_LESSION);
    }

    public static Coach getCoach(int id){
        return mapOfCoach.get(id);
    }

    public static Student getStudent(int id){
        return mapOfStu.get(id);
    }
















    public static Boolean stringCheck(String string){
        String regex="^[0-9A-Za-z]+$";
        if(string.matches(regex)==true)
            return true;
        else
            return false;
    }

    public static Boolean numCheck(String string){
        String regex="^[0-9]+$";
        if(string.matches(regex)==true)
            return true;
        else
            return false;
    }


}
