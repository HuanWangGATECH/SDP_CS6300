package edu.gatech.seclass.encode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import encode.test.edu.gatech.seclass.encode.MainTest;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class MyMainTest {


/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/
    private static final String FILE1 = "abcxyABCXYBcxY";
    private static final String FILE2 = "This is testing message";
    private static final String FILE3 = "abc123";
    private static final String FILE4 = "8500118238";
    private static final String FILE5 = "          ";
    private static final String FILE6 = "@#$%^&*";
    private static final String FILE7 = "12ab5cABCaBc4Ab89";

    //Purpose: testing only the encoding -n part for given file
    //Frame #: 56
    @Test
    public void encodeTest1() throws Exception {
        File inputfile = createInputFile(FILE3);
        String args[] = {"-n", "4", inputfile.getPath()}
        Main.main(args);

        String expected = "050607123";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing the encoding -n part, and the capitalization -c part for given file
    //Frame #: 57
    @Test
    public void encodeTest2() throws Exception {
        File inputfile = createInputFile(FILE1);
        String args[] = {"-n", "1", "-c", "cx", inputfile.getPath()}
        Main.main(args);

        String expected = "02032526020304252603042526";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing just the right rotation for given file
    //Frame #: 58
    @Test
    public void encodeTest3() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-r", "3", inputfile.getPath()}
        Main.main(args);

        String expected = "ageThis is testing mess";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing just the capitalization for given file
    //Frame #: 69
    @Test
    public void encodeTest4() throws Exception {
        File inputfile = createInputFile(FILE1);
        String args[] = {"-c", "cx", inputfile.getPath()}
        Main.main(args);

        String expected = "abCXyABcxYBCXY";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing left rotation for a numeric input
    //Frame #: 22
    @Test
    public void encodeTest5() throws Exception {
        File inputfile = createInputFile(FILE4);
        String args[] = {"-r", "5", inputfile.getPath()}
        Main.main(args);

        String expected = "1823885001";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing right rotation and reverse capitalization for a given file
    //Frame #: 61
    @Test
    public void encodeTest6() throws Exception {
        File inputfile = createInputFile(FILE3);
        String args[] = {"-r", "1", "-c", "ab", inputfile.getPath()}
        Main.main(args);

        String expected = "3ABc12";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing default value for a file with only spaces
    //Frame #: 18
    @Test
    public void encodeTest7() throws Exception {
        File inputfile = createInputFile(FILE5);
        String args[] = {inputfile.getPath()}
        Main.main(args);

        String expected = "          ";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing left rotation for special character files
    //Frame #: 40
    @Test
    public void encodeTest8() throws Exception {
        File inputfile = createInputFile(FILE6);
        String args[] = {"-l", "1", inputfile.getPath()}
        Main.main(args);

        String expected = "#$%^&*@";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing default value for special character files
    //Frame #: 43
    @Test
    public void encodeTest9() throws Exception {
        File inputfile = createInputFile(FILE6);
        String args[] = {inputfile.getPath()}
        Main.main(args);

        String expected = "@#$%^&*";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }
    //Purpose: testing right rotation and encoding for given file
    //Frame #: 46
    @Test
    public void encodeTest10() throws Exception {
        File inputfile = createInputFile(FILE3);
        String args[] = {"-r", "1","-n","4" inputfile.getPath()}
        Main.main(args);

        String expected = "301020312";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }
    //Purpose: testing right rotation, capitalization and encoding for given file
    //Frame #: 49
    @Test
    public void encodeTest11() throws Exception {
        File inputfile = createInputFile(FILE7);
        String args[] = {"-r", "1","-c","ab","-n","3" inputfile.getPath()}
        Main.main(args);

        String expected = "9120102503010203010203401028";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }
    //Purpose: testing rotation for file with spaces
    //Frame #: 10
    @Test
    public void encodeTest12() throws Exception {
        File inputfile = createInputFile(FILE5);
        String args[] = {"-r", "6", inputfile.getPath()}
        Main.main(args);

        String expected = "          ";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing default value for numeric input
    //Frame #: 31
    @Test
    public void encodeTest13() throws Exception {
        File inputfile = createInputFile(FILE4);
        String args[] = {inputfile.getPath()}
        Main.main(args);

        String expected = "8500118238";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing default value for given file
    //Frame #: 68
    @Test
    public void encodeTest14() throws Exception {
        File inputfile = createInputFile(FILE7);
        String args[] = {inputfile.getPath()}
        Main.main(args);

        String expected = "1214155161415161415164141589";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing left rotation, capitalization and encoding for given file
    //Frame #: 51
    @Test
    public void encodeTest15() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-l", "1","-c", "is", "-n", "2", inputfile.getPath()}
        Main.main(args);

        String expected = "101121 1121 22072122111609 1507212103090722";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

}
