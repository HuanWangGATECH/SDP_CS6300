package edu.gatech.seclass;

import org.junit.Test;

public class WhiteboxClassTestPC2 {
    @Test
    public void testwhiteboxMethod2() throws Exception {
        WhiteboxClass whiteboxClass = new WhiteboxClass();
        whiteboxClass.whiteboxMethod2(10, 5, true);
        whiteboxClass.whiteboxMethod2(5,1,false);
        whiteboxClass.whiteboxMethod2(6, 5, true);
        whiteboxClass.whiteboxMethod2(8,2,false);
    }
}
