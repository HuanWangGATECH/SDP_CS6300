package edu.gatech.seclass;

public class WhiteboxClass {

    public void whiteboxMethod1(){
        //THIS IMPLEMENTATION IS NOT POSSIBLE

        //Having a test suite which has 100 % branch coverage and not revealing the fault AND
        // every test suite which has 100 % statement coverage revealing the fault is not possible because
        // branch coverage supersedes statement coverage meaning that it will identify more faults (if any) than
        // statement coverage.
    }

    public void whiteboxMethod2(int a, int b, boolean p) {
        float result = 1;
        if (p == true){
            result = a / b;
        }
        else{
            b = b + 2;
            result = a / b;
        }
        System.out.println(result);
    }

    public void whiteboxMethod3(int a, int b) {
        float result = 1;
        if (b == 0) {
            b = b + 5;
            result = a / b;
        }
        else {
            b = b - b;
            result = a / b;

        }

    }

    public void whiteboxMethod4() {
        //THIS IMPLEMENTATION IS NOT POSSIBLE

        //Might be possible to find a test suit that has less than 100% statement coverage and not
        // finding the fault but when the second condition is tried to be met,
        // which is for every test suite that has 100 % statement coverage and does not reveal the fault
        // The fault would have already been found by the first set of conditions which is partial statement coverage and revealing the fault
        // and this means that there is no way for even a single full 100 % statement coverage to NOT reveal the fault
    }

    public boolean whiteboxMethod5(boolean a, boolean b) {
        int x = 2;
        int y = 4;

        if(a)
            x = x*2;
        else
            b = !b;
        if(b)
            y -= x;
        else
            x -= y;
        return ((x/y) >= 1);
    }

    // ================
    //
    // Fill in column “output” with T, F, or E:
    //
    // | a | b |output|
    // ================
    // | T | T |   E   |
    // | T | F |   F   |
    // | F | T |   F   |
    // | F | F |   T   |
    // ================
    //
    // Fill in the blanks in the following sentences with
    // “NEVER”, “SOMETIMES” or “ALWAYS”:
    //
    // Test suites with 100% statement coverage ALWAYS reveal the fault in this method.
    // Test suites with 100% branch coverage ALWAYS reveal the fault in this method.
    // Test suites with 100% path coverage ALWAYS reveal the fault in this method.
    // ================



}
