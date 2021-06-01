package domain;


import org.junit.Test;
import domain.Question;
import domain.Reply;

import static org.junit.Assert.assertEquals;

public class testQuestion {
    @Test
    public void testcreat(){
        Question question = new Question(100001,200001,"Wang","How can I be stronger?","I want to be stronger.","2021-05-30");
        assertEquals(100001,question.getUserId());
        assertEquals(200001,question.getContentId());
        assertEquals("Wang",question.getName());
        assertEquals("How can I be stronger?",question.getTitle());
        assertEquals("I want to be stronger.",question.getContent());
        assertEquals("2021-05-30",question.getPostTime());
    }
}