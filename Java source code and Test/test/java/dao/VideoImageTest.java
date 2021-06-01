package dao;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VideoImageTest {

    /**
     * this test is to prove the prossimg can get the first image of
     * a video. before test we have to indicate the videopath in the
     * computer and the name we want.
     *
     *
     */

    public String video_path;
    public String name;
    @Test
    public void  testimage(){
        assertEquals(true,VideoImage.processImg(video_path,name));
    }
}
