package domain;

import com.alibaba.fastjson.JSONArray;
import dao.WorkUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumIO {
    private static final String DEFAULT_FORUMPATH=new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\forum.txt";
    private static final int DEFAULT_QUESTIONID=400000;
    private static final int DEFAULT_REPLYID=500000;

    private static int idOfQuestion;
    private static int idOfReply;
    private static List<Question> questionList;
    private static Map<Integer, Question> mapOfQuestion;

    static {
        formQuestionList();
        formMapOfQuestion();
    }

    private static String readFile(){
        return WorkUtil.readFile(DEFAULT_FORUMPATH);
    }
    private static void writeFile(String data){
        WorkUtil.writeFile(data,DEFAULT_FORUMPATH);
    }

    public static Question getQuestion(int id){
        return mapOfQuestion.get(id);
    }

    public static void addQuestion(Question question){
        question.setContentId(idOfQuestion++);
        questionList.add(question);
        writeFile(JSONArray.toJSONString(questionList));
    }

    public static void replaceQuestion(int contentId,Question question){
        if(!mapOfQuestion.containsKey(contentId)){
            return;
        }
        mapOfQuestion.replace(contentId,question);
        writeQuestion();
    }

    public static void writeQuestion(){
        questionList= new ArrayList<>(mapOfQuestion.values());
        writeFile(JSONArray.toJSONString(questionList));
    }

    private static void formQuestionList(){
        String questionListString = readFile();
        System.out.println(questionListString);
        questionList = new ArrayList<Question>();
        if(!"".equals(questionListString)){
            questionList= JSONArray.parseArray(questionListString).toJavaList(Question.class);
        }
    }

    public static void formMapOfQuestion(){
        mapOfQuestion = new HashMap<Integer, Question>();
        if(questionList.size()==0){
            idOfQuestion=DEFAULT_QUESTIONID;
            idOfReply=DEFAULT_REPLYID;
        }else{
            for(int i=0;i<questionList.size();i++){
                List<Reply> replyList;
                mapOfQuestion.put(questionList.get(i).getContentId(),questionList.get(i));
                if(i==questionList.size()-1){
                    idOfQuestion=questionList.get(i).getContentId()+1;
                }
                if(!questionList.get(i).getReplyListString().equals(""))
                    replyList = JSONArray.parseArray(questionList.get(i).getReplyListString()).toJavaList(Reply.class);
                else
                    replyList = new ArrayList<Reply>();
                for(int j=0;j<replyList.size();j++){
                    if(idOfReply<replyList.get(j).getContentId())idOfReply=replyList.get(j).getContentId();
                }
            }
            idOfReply++;
        }
    }

    public static void deleteQuestion(Question question){
        for(int i=0;i<questionList.size();i++){
            if(questionList.get(i).getContentId()==question.getContentId()){
                questionList.remove(i);
                writeFile(JSONArray.toJSONString(questionList));
                formMapOfQuestion();
                return;
            }
        }
    }

    public static List<Question> getQuestionList(){
        return questionList;
    }
    public static int getIdOfReply(){
        return idOfReply++;
    }

}
