package edu.eci.arsw.highlandersim;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestInmortals{
    ControlFrame frame;

    public TestInmortals() {
    }

    @Before
    public void setUp() {
        frame = new ControlFrame();
    }

    @Test
    public  void testInvariant(){
        frame.actionStart();
        int sum;
        for (int i=0;i<100;i++){
            frame.actionPause();
            sum= frame.getSum();
            assertEquals(300,sum);
        }
        frame.actionStop();

    }

    



}
