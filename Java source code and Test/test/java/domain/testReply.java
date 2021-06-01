package domain;

import org.junit.Test;
import domain.Reply;

import static org.junit.Assert.assertEquals;

public class testReply {
    @Test
    public void testcreat(){
        Reply reply = new Reply(100001,200001,"Wang","How can I be stronger?","2021-05-30");
        assertEquals(100001,reply.getUserId());
        assertEquals(200001,reply.getContentId());
        assertEquals("Wang",reply.getName());
        assertEquals("How can I be stronger?",reply.getContent());
        assertEquals("2021-05-30",reply.getPostTime());
    }
}
