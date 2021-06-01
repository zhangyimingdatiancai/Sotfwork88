package domain;

import java.util.ArrayList;
import java.util.List;

public class Reply {
    private int userId;
    private int contentId;
    private String name;
    private String content;
    private List<Integer> goodCounterList;
    private List<Integer> badCounterList;
    private String postTime;
    private boolean isDeleted;

    public Reply(){
        this.goodCounterList = new ArrayList<Integer>();
        this.badCounterList = new ArrayList<Integer>();
        isDeleted = false;
    }

    public Reply(int userId,int contentId,String name,String content,String postTime){
        this.userId = userId;
        this.contentId = contentId;
        this.name = name;
        this.content = content;
        this.postTime = postTime;
        this.goodCounterList = new ArrayList<Integer>();
        this.badCounterList = new ArrayList<Integer>();
        isDeleted = false;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return userId;
    }

    public  void setContentId(int contentId){
        this.contentId = contentId;
    }
    public int getContentId() {
        return contentId;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }

    public void changeGoodCounter(int id){
        if(goodCounterList.size()==0){
            goodCounterList.add(id);
            return;
        }
        for(int i=0;i<goodCounterList.size();i++){
            if(goodCounterList.get(i)==id){
                goodCounterList.remove(i);
                return;
            }
        }
        goodCounterList.add(id);
    }
    public int returnGoodCounterList(){
        return goodCounterList.size();
    }
    public List<Integer> getGoodCounterList(){
        return  goodCounterList;
    }
    public int checkGoodCounter(int id){
        if(goodCounterList.size()==0){
            return 0;
        }
        for(int i=0;i<goodCounterList.size();i++){
            if(goodCounterList.get(i)==id){
                return 1;
            }
        }
        return 0;
    }


    public void changeBadCounter(int id){
        if(badCounterList.size()==0){
            badCounterList.add(id);
            return;
        }
        for(int i=0;i<badCounterList.size();i++){
            if(badCounterList.get(i)==id){
                badCounterList.remove(i);
                return;
            }
        }
        badCounterList.add(id);
    }
    public int returnBadCounterList(){
        return badCounterList.size();
    }
    public List<Integer> getBadCounterList(){
        return  badCounterList;
    }
    public int checkBadCounter(int id){
        if(badCounterList.size()==0){
            return 0;
        }
        for(int i=0;i<badCounterList.size();i++){
            if(badCounterList.get(i)==id){
                return 1;
            }
        }
        return 0;
    }

    public void setPostTime(String postTime){
        this.postTime = postTime;
    }
    public String getPostTime(){
        return postTime;
    }

    public void setIsDeleted(boolean isDeleted){
        this.isDeleted = isDeleted;
    }
    public boolean getIsDeleted(){
        return isDeleted;
    }
}
