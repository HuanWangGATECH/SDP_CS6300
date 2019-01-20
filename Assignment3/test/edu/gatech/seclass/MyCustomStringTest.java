package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * You should implement your tests in this class.  Do not change the test names.
 */

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    //Test Purpose: This is the first instructor example test.
    @Test
    public void testCountDuplicates1() {
        mycustomstring.setString("People are sleeping... Zzz.");
        assertEquals(4, mycustomstring.countDuplicates());
    }

    @Test
    public void testCountDuplicates2() {
        mycustomstring.setString("Hello");
        assertEquals(1, mycustomstring.countDuplicates());
        //fail("Not yet implemented");
    }

    @Test
    public void testCountDuplicates3() {
        mycustomstring.setString("AAAAAABbb");
        assertEquals(6, mycustomstring.countDuplicates());
        //including multiple repeated letters mixing up capitals and lower case
    }

    @Test
    public void testCountDuplicates4() {
        mycustomstring.setString("123445");
        assertEquals(1,mycustomstring.countDuplicates());
        //testing the functionality using numbers as characters
    }

    @Test
    public void testCountDuplicates5() {
        mycustomstring.setString(",..,,testing.,,.");
        assertEquals(3,mycustomstring.countDuplicates());
        //using special characters as the input string
    }

    @Test
    public void testCountDuplicates6() {
        mycustomstring.setString("Hello  AaAA");
        assertEquals(3,mycustomstring.countDuplicates());
        //Mixed with normal string with lower and upper case along with 2 spaces also being counted as a character
    }

    //Test Purpose: This is the second instructor example test.
    @Test
    public void testAddDigits1() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("5678!!! H7y, l7t'9 put 94me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, true));
    }

    //Test Purpose: This the third instructor example test.
    @Test
    public void testAddDigits2() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("7890!!! H9y, l9t'1 put 16me d727ts in this 1tr7n2!77!7", mycustomstring.addDigits(4, false));
    }

    @Test
    public void testAddDigits3() {
        mycustomstring.setString("hello 90, bye 2");
        assertEquals("hello 12, bye 4", mycustomstring.addDigits(2,true));
        //testing given case
    }

    @Test
    public void testAddDigits4() {
        mycustomstring.setString("hello 90, bye 2");
        assertEquals("hello 78, bye 0", mycustomstring.addDigits(2,false));
        //testing given case
    }

    @Test
    public void testAddDigits5() {
        mycustomstring.setString("123456789");
        assertEquals("456789012",mycustomstring.addDigits(3,true));
        //testing with numbers
    }

    @Test
    public void testAddDigits6() {
        mycustomstring.setString("123456789");
        assertEquals("890123456",mycustomstring.addDigits(3,false));
        //testing with numbers
    }

    @Test(expected = NullPointerException.class)
    public void testAddDigits7() {
        mycustomstring.setString("");
        mycustomstring.addDigits(5,true);
        //testing the null pointer exception with possible values for arguments
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddDigits8() {
        mycustomstring.setString("TEST");
        mycustomstring.addDigits(25,true);
        //giving n higher than 9
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddDigits9() {
        mycustomstring.setString("TEST");
        mycustomstring.addDigits(-7,true);
        //giving n less than 0
    }

    @Test (expected = NullPointerException.class)
    public void testAddDigits10() {
        mycustomstring.setString("");
        mycustomstring.addDigits(10,true);
        //null string is given AND higher than 9 n value is given but takes the first null pointer exception because there is a null string
    }

    @Test
    public void testAddDigits11() {
        mycustomstring.setString("909090");
        assertEquals("676767",mycustomstring.addDigits(3,false));
        //testing with repeated digits
    }

    @Test
    public void testAddDigits12() {
        mycustomstring.setString("HOOLA9876LALALEYO");
        assertEquals("HOOLA6543LALALEYO", mycustomstring.addDigits(3,false));
        //testing for numbers in between the string
    }


    //Test Purpose: This is the fourth instructor example test.
    @Test
    public void testFlipLetttersInSubstring1() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(18, 30);
        assertEquals("H3y, l3t'5 put 50ni s161tD em this 5tr1n6!11!!", mycustomstring.getString());
    }

    //Test Purpose: This is an instructor example test to demonstrate testing for an exception.
    @Test(expected = NullPointerException.class)
    public void testFlipLetttersInSubstring2() {
        mycustomstring.flipLetttersInSubstring(200, 100);
    }

    @Test(expected = NullPointerException.class)
    public void testFlipLetttersInSubstring3() { 
        mycustomstring.setString("");
        mycustomstring.flipLetttersInSubstring(2,10);
        //giving null string as input
    }

    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring4() {
        mycustomstring.setString("BUMBUMBOO");
        mycustomstring.flipLetttersInSubstring(3,12);
        //endposition is higher than the length of the string so it will fail and throw an exception
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring5() {
        mycustomstring.setString("HUMDIDDLEDEE");
        mycustomstring.flipLetttersInSubstring(-4,7);
        //startpoisiton is a negative number and also less than zero which doesnt exist in the index of a string or array
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFlipLetttersInSubstring6() {
        mycustomstring.setString("twinklestars");
        mycustomstring.flipLetttersInSubstring(4,2);
        //start position is higher than end position which will terminate the program
    }

    @Test
    public void testFlipLetttersInSubstring7() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testFlipLetttersInSubstring8() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testFlipLetttersInSubstring9() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testFlipLetttersInSubstring10() {
        
        fail("Not yet implemented");
    }

}
