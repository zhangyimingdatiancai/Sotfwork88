package domain;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question extends Reply{
    private String title;
    private String replyListString;

    public Question(){
        super();
        this.replyListString = "";

    }
    public Question(int userId,int contentId,String name,String title,String content,String postTime){
        super(userId,contentId,name,content,postTime);
        setTitle(title);
    }

    public void updateReply(Reply reply){
        List<Reply> replyList = JSONArray.parseArray(replyListString).toJavaList(Reply.class);
        Map<Integer, Reply> mapOfReply = new HashMap<Integer, Reply>();

        for(int i=0;i<replyList.size();i++){
            mapOfReply.put(replyList.get(i).getContentId(),replyList.get(i));
        }

        mapOfReply.replace(reply.getContentId(),reply);
        replyList= new ArrayList<>(mapOfReply.values());

        this.replyListString = JSONArray.toJSONString(replyList);
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setReplyListString(String replyListString){
        this.replyListString = replyListString;
    }
    public String getReplyListString(){
        return replyListString;
    }
}
