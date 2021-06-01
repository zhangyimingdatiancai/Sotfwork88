package domain;

import org.junit.Test;
import domain.Student;
import dao.WorkUtil;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testStudent {
    @Test
    public void testcreat(){
        Student student = new Student(WorkUtil.getIdOfStu(),"Wang","18103470029","123123","BeiJing","Male","VIP","0",new ArrayList<Integer>());
        assertEquals(WorkUtil.getIdOfStu()-1,student.getId());
        assertEquals("Wang",student.getName());
        assertEquals("18103470029",student.getPhonenum());
        assertEquals("123123",student.getPassword());
        assertEquals("BeiJing",student.getAddress());
        assertEquals("Male",student.getGender());
        assertEquals("VIP",student.getAccountType());
        assertEquals("0",student.getAccountBalance());
        assertEquals(false,student.getDeleted());
    }
}
