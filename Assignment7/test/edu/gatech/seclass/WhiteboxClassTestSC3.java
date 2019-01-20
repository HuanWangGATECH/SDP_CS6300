package edu.gatech.seclass;

import org.junit.Test;

public class WhiteboxClassTestSC3 {
    @Test
    public void testwhiteboxMethod3() throws Exception {
        WhiteboxClass whiteboxClass = new WhiteboxClass();
        whiteboxClass.whiteboxMethod3(5,0);
        whiteboxClass.whiteboxMethod3(6,0);
        whiteboxClass.whiteboxMethod3(7,4);
    }
}
