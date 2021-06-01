package domain;

import dao.WorkUtil;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testCoach {
    @Test
    public void testcreat(){
        Coach Coach = new Coach(WorkUtil.getIdOfCoach(),"Wang","13521376425","112133","beijing","male","VIP","0","0",false,new ArrayList<Integer>());
        assertEquals(WorkUtil.getIdOfCoach()-1,Coach.getId());
        assertEquals("Wang",Coach.getName());
        assertEquals("13521376425",Coach.getPhonenum());
        assertEquals("112133",Coach.getPassword());
        assertEquals("beijing",Coach.getAddress());
        assertEquals("male",Coach.getGender());
        assertEquals(false,Coach.getDeleted());
    }
}
