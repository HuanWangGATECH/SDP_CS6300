package edu.gatech.seclass.encode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class MyMainTest {


    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


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
    private static final String FILE8 = "abcxyz";
    private static final String FILE9 = "Howdy Billy,\n" +
            "I am going to take cs6300 and cs6400 next semester.\n" +
            "Did you take cs 6300 last semester? I want to\n" +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE10 = "abc123ABC";
    private static final String FILE11 = "Let's try some **special**  %!(characters)!% ###\n" +
            "and line breaks^$@ \r" +
            "of \\different// types; \n" +
            "in 1 file\r"+
            ":-)";
    private static final String FILE12 = "Howdy Billy," + System.lineSeparator() +
            "I am going to take cs6300 and cs6400 next semester."  + System.lineSeparator() +
            "Did you take cs 6300 last semester? I want to"  + System.lineSeparator() +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE13 = "\n\r678";
    private static final String FILE14 = "this is a test\n\r message";
    private static final String FILE15 = "";


    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                     new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    //Purpose: testing only the encoding -n part for given file
    //Frame #: 56
    @Test
    public void encodeTest1() throws Exception {
        File inputfile = createInputFile(FILE3);
        String args[] = {"-n", "4", inputfile.getPath()};
        Main.main(args);

        String expected = "050607123";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing the encoding -n part, and the capitalization -c part for given file
    //Frame #: 57
    //Failure Type: Test Failure
    @Test
    public void encodeTest2() throws Exception {
        File inputfile = createInputFile(FILE1);
        String args[] = {"-n", "1", "-c", "cx", inputfile.getPath()};
        Main.main(args);

        String expected = "0203305126282904255229305152";
        //String expected = "02032526020304252603042526";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing just the right rotation for given file
    //Frame #: 58
    @Test
    public void encodeTest3() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-r", "3", inputfile.getPath()};
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
        String args[] = {"-c", "cx", inputfile.getPath()};
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
        String args[] = {"-r", "5", inputfile.getPath()};
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
        String args[] = {"-r", "1", "-c", "ab", inputfile.getPath()};
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
        String args[] = {inputfile.getPath()};
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
        String args[] = {"-l", "1", inputfile.getPath()};
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
        String args[] = {inputfile.getPath()};
        Main.main(args);

        String expected = "@#$%^&*";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }
    //Purpose: testing right rotation and encoding for given file
    //Frame #: 46
    //Failure Type: Test Failure
    @Test
    public void encodeTest10() throws Exception {
        File inputfile = createInputFile(FILE3);
        String args[] = {"-r", "1","-n","4", inputfile.getPath()};
        Main.main(args);
        String expected = "305060712";
        //String expected = "301020312";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }
    //Purpose: testing right rotation, capitalization and encoding for given file
    //Frame #: 49
    // Failure Type: Test Failure
    @Test
    public void encodeTest11() throws Exception {
        File inputfile = createInputFile(FILE7);
        String args[] = {"-r", "1","-c","ab","-n","3", inputfile.getPath()};
        Main.main(args);

        String expected = "9123031506040532300506404318";
        //String expected = "9120102503010203010203401028";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }
    //Purpose: testing rotation for file with spaces
    //Frame #: 10
    @Test
    public void encodeTest12() throws Exception {
        File inputfile = createInputFile(FILE5);
        String args[] = {"-r", "6", inputfile.getPath()};
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
        String args[] = {inputfile.getPath()};
        Main.main(args);

        String expected = "8500118238";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    
    //Purpose: testing default value for given file
    //Frame #: 68
    // Failure Type: Test Failure
    @Test
    public void encodeTest14() throws Exception {
        File inputfile = createInputFile(FILE7);
        String args[] = {inputfile.getPath()};
        Main.main(args);

        String expected = "1214155164041421441164401589";
        //String expected = "1214155161415161415164141589";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Purpose: testing left rotation, capitalization and encoding for given file
    //Frame #: 51
    // Failure Type: Test Failure
    @Test
    public void encodeTest15() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-l", "1","-c", "is", "-n", "2", inputfile.getPath()};
        Main.main(args);
        String expected = "103747 3747 22074722371609 1507474703090748";
        //String expected = "101121 1121 22072122111609 1507212103090722";
        String actual = getFileContent(inputfile.getPath());

        assertEquals("The files differ!", expected, actual);

    }

    //Failure Type:BUG: encode will fail when there is a negative encoding option applied. It can not encode characters as negative integers.
    @Test
    public void encodeTest16() throws Exception {
        File inputfile = createInputFile(FILE4);
        String args[] = {"-n","-2",inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }


    //Failure Type:BUG: occurs because encode does not accept negative integers for string rotation but should be able to rotate in the opposite direction if a negative integer is given
    @Test
    public void encodeTest17() throws Exception {
        File inputfile = createInputFile(FILE4);
        String args[] = {"-r","-2",inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }
    
    //Failure Type:BUG: encode will fail because there is no integer argument provided to encode the string with
    @Test
    public void encodeTest18() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-n", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }

    // @Test
    // public void encodeTest19() throws Exception {
    //     File inputfile = createInputFile(FILE2);
    //     String args[] = {"-d","-2", inputfile.getPath()};
    //     Main.main(args);
    //     String expected = "Error";
    //     String actual = getFileContent(inputfile.getPath());
    //     assertEquals("The files differ!", expected, actual);

    // }

    
    //Failure Type:BUG: this happens because -d should be passed an integer value and not a string value which is not being enforced in the current version provided
    @Test
    public void encodeTest20() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-d","is", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }

    //Failure Type:BUG: this happens because we are asking encode to reverse capitalization for every empty string found in the input file which is not possible
    @Test
    public void encodeTest21() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-c","", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }

    //Failure Type:BUG: when same file content is passed as an argument value to -c option, it should be reversing the capitalization for all characters which does not happen
    @Test
    public void encodeTest22() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"-c","This is testing message", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }

    //Failure Type:BUG: this bug happens because both same rotations can not be applied to the input string value.
    @Test
    public void encodeTest23() throws Exception {
        File inputfile = createInputFile(FILE9);
        String args[] = {"-r","1","-r","2", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }

    // @Test
    // public void encodeTest24() throws Exception {
    //     File inputfile = createInputFile(FILE9);
    //     String args[] = {"-r","1","-l","1", inputfile.getPath()};
    //     Main.main(args);
    //     String expected = "Error";
    //     String actual = getFileContent(inputfile.getPath());
    //     assertEquals("The files differ!", expected, actual);

    // }


    //Failure Type:BUG: occurs due to the fact that when no option and just an integer input is given, encode just does -n 0 on the input string which is not correct
    @Test
    public void encodeTest25() throws Exception {
        File inputfile = createInputFile(FILE2);
        String args[] = {"2", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }


    //Failure Type:BUG: when combined with a series of special characters such as the new line operator and with other strings in between, encode will fail
    @Test
    public void encodeTest26() throws Exception {
        File inputfile = createInputFile(FILE14);
        String args[] = {"-r","1", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }


    //Failure Type:BUG: there can be no repetitions of characters in an empty file which is why this bug occurs when -d and 1 is passed.
    @Test
    public void encodeTest27() throws Exception {
        File inputfile = createInputFile(FILE15);
        String args[] = {"-d","1", inputfile.getPath()};
        Main.main(args);
        String expected = "Error";
        String actual = getFileContent(inputfile.getPath());
        assertEquals("The files differ!", expected, actual);

    }

    // @Test
    // public void encodeTest20() throws Exception {
    //     File inputfile = createInputFile(FILE2);
    //     String args[] = {"-n","-l","4", inputfile.getPath()};
    //     Main.main(args);
    //     String expected = "Error";
    //     String actual = getFileContent(inputfile.getPath());
    //     assertEquals("The files differ!", expected, actual);

    // }
}
