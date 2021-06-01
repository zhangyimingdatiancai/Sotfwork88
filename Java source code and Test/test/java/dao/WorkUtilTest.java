package dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dao.WorkUtil;

public class WorkUtilTest {

    @Test
    public void  testloginCheck(){
        WorkUtil w=new WorkUtil();
        assertEquals(false,WorkUtil.loginCheckForCoach("zym","112133"));
        assertEquals(false,WorkUtil.loginCheckForStu("zym","112133"));
    }

    @Test
    public void  testMember(){
        WorkUtil w=new WorkUtil();
        assertFalse(WorkUtil.hasMember("zym"));
    }

    @Test
    public void  testGetid(){
        WorkUtil w=new WorkUtil();
        assertEquals(100000,WorkUtil.getIdOfCoach());
        assertEquals(200000,WorkUtil.getIdOfStu());

    }




}
