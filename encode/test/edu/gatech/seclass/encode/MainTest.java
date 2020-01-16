package edu.gatech.seclass.encode;

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

/*
DO NOT ALTER THIS CLASS.  Use it as an example for MyMainTest.java
 */

public class MainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
    *  TEST UTILITIES
    */

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
	
	/*
	* TEST FILE CONTENT
	*/
	private static final String FILE1 = "abcXYZ";
    private static final String FILE2 = "Howdy Billy,\n" +
            "I am going to take cs6300 and cs6400 next semester.\n" +
            "Did you take cs 6300 last semester? I want to\n" +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE3 = "abc123";
    private static final String FILE4 = "";
    private static final String FILE5 = " ";
    private static final String FILE6 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String FILE7 = "0123456789";
    private static final String FILE8 = "Let's try some **special**  %!(characters)!% ###\n" +
            "and line breaks^$@ \r" +
            "of \\different// types; \n" +
            "in 1 file\r"+
            ":-)";
    private static final String FILE9 = "Up with the white and gold\r" +
            "Down with the red and black\r" +
            "Georgia Tech is out for a victory\r" +
            "Well drop a battle axe on georgia's head\r" +
            "When we meet her our team is sure to beat her\r" +
            "Down on the old farm there will be no sound\r" +
            "'Till our bow wows rips through the air\r" +
            "When the battle is over georgia's team will be found\r" +
            "With the Yellow Jacket's swarming 'round! Hey!";
    private static final String FILE10 = "Robert'); DROP TABLE students;--";
    private static final String FILE11 = ".*";
    private static final String FILE12 = " Just a one line file with numbers 123456 ";
    private static final String FILE13 = "Howdy Billy," + System.lineSeparator() +
            "I am going to take cs6300 and cs6400 next semester."  + System.lineSeparator() +
            "Did you take cs 6300 last semester? I want to"  + System.lineSeparator() +
            "take 2 courses so that I will graduate Asap!";
    private static final String FILE14 = "\n";
	

    // test cases

    /*
    *   TEST CASES
    */

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 1 from assignment directions
    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {inputFile1.getPath()};
        Main.main(args);

        String expected1 = "141516373839";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 2 from assignment directions
    @Test
    public void mainTest2() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {"-r", "5", inputFile1.getPath()};
        Main.main(args);

        String expected2 = "bcXYZa";

        String actual2 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected2, actual2);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 3 from assignment directions
    @Test
    public void mainTest3() throws Exception {
        File inputFile2 = createInputFile(FILE2);

        String args[] = {"-c", "aeiou", "-l", "3", inputFile2.getPath()};
        Main.main(args);

        String expected3 = "dy BIlly,HOw\n" +
            "m gOIng tO tAkE cs6300 And cs6400 nExt sEmEstEr.i A\n" +
            " yOU tAkE cs 6300 lAst sEmEstEr? i wAnt tODId\n" +
            "E 2 cOUrsEs sO thAt i wIll grAdUAtE asAp!tAk";

        String actual3 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected3, actual3);
    }

    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 4 from assignment directions
    @Test
    public void mainTest4() throws Exception {
        File inputFile3 = createInputFile(FILE3);

        String args[] = {"-n", "2", "-r", "1", inputFile3.getPath()};
        Main.main(args);

        String expected4 = "303040512";

        String actual4 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected4, actual4);
    }


    // Purpose: To provide an example of a test case format
    // Frame #: Instructor example 5 from assignment directions
    @Test
    public void mainTest5() throws Exception {
        File inputFile2 = createInputFile(FILE2);

        String args[] = {"-d", "1", inputFile2.getPath()};
        Main.main(args);

        String expected3 = "Howdy Bil,\n" +
                "Iamgntkecs6304xr." +
                "Du?" +
                "2hAp!";

        String actual3 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected3, actual3);
    }

    // Purpose: To provide an example of a test case format (no arguments passed)
    // Frame #: Instructor error example
    @Test
    public void mainTest6() {
        String args[]  = new String[0]; //no arguments
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest7() throws Exception {
        File inputFile = createInputFile(FILE4);

        String args[] = {"-r", "2", inputFile.getPath()};
        Main.main(args);

        String expected = "";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest8() throws Exception {
        File inputFile = createInputFile(FILE4);

        String args[] = {"-n", "2", inputFile.getPath()};
        Main.main(args);

        String expected = "";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest9() throws Exception {
        File inputFile = createInputFile(FILE8);

        String args[] = {"-r", "4", inputFile.getPath()};
        Main.main(args);

        String expected = " ###Let's try some **special**  %!(characters)!%\n" +
                "^$@ and line breaks\r" +
                "es; of \\different// typ\n" +
                "filein 1 \r"+
                "):-";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest10() throws Exception {
        File inputFile = createInputFile(FILE13);

        String args[] = {"-n", "0", inputFile.getPath()};
        Main.main(args);

        String expected = "3415230425 2809121225," + System.lineSeparator() +
                "35 0113 0715091407 2015 20011105 03196300 011404 03196400 14052420 1905130519200518." + System.lineSeparator() +
                "300904 251521 20011105 0319 6300 12011920 1905130519200518? 35 23011420 2015" + System.lineSeparator() +
                "20011105 2 03152118190519 1915 20080120 35 23091212 0718010421012005 27190116!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest11() throws Exception {
        File inputFile = createInputFile(FILE5);

        String args[] = {inputFile.getPath()};
        Main.main(args);

        String expected = " ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest12() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-n", "-r", "1", inputFile.getPath()};
        Main.main(args);

        String expected = "52010203040506070809101112131415161718192021222324252627282930313233343536373839404142434445464748495051";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest13() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-d", "1", inputFile.getPath()};
        Main.main(args);

        String expected = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest14() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-l", "1", inputFile.getPath()};
        Main.main(args);

        String expected = "1234567890";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest15() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-n", "10", "-c", "abc983", inputFile.getPath()};
        Main.main(args);

        String expected = "0123456789";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest16() throws Exception {
        File inputFile = createInputFile(FILE13);

        String args[] = {"-n", "1", "-c", "abcdefghijkl", inputFile.getPath()};
        Main.main(args);

        String expected = "0916243126 0336393926,"  + System.lineSeparator() +
                "10 2814 3416361534 2116 21283832 30206300 281531 30206400 15322521 2032143220213219."  + System.lineSeparator() +
                "053631 261622 21283832 3020 6300 39282021 2032143220213219? 10 24281521 2116"  + System.lineSeparator() +
                "21283832 2 30162219203220 2016 21352821 10 24363939 3419283122282132 02202817!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest17() throws Exception {
        File inputFile = createInputFile(FILE8);

        String args[] = {"-d", "2", inputFile.getPath()};
        Main.main(args);

        String expected = "Let's try some**pcial%!(char)!%##\n" +
                "ndlinbk^$@\r" +
                "of\\df//yp;\n" +
                "1\r" +
                ":-)";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest18() throws Exception {
        File inputFile = createInputFile(FILE8);

        String args[] = {"-d", "0", inputFile.getPath()};
        Main.main(args);

        String expected = "";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest19() throws Exception {
        File inputFile = createInputFile(FILE12);

        String args[] = {"-n", "5", "-d", "2", inputFile.getPath()};
        Main.main(args);

        String expected = " 41262425 06201910171419101114170225132618072324123456";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest20() throws Exception {
        File inputFile = createInputFile(FILE10);

        String args[] = {"-n", "-r", "3", inputFile.getPath()};
        Main.main(args);

        String expected = ";--441502051820'); 30444142 4627283831 1920210405142019";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest21() throws Exception {
        File inputFile = createInputFile(FILE11);

        String args[] = {"-c", "a", "-n", "5", inputFile.getPath()};
        Main.main(args);

        String expected = ".*";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest22() throws Exception {
        File inputFile = createInputFile(FILE11);

        String args[] = {"-r", "2", inputFile.getPath()};
        Main.main(args);

        String expected = ".*";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest23() throws Exception {
        File inputFile = createInputFile(FILE12);

        String args[] = {"-n-d5", inputFile.getPath()};
        Main.main(args);

        String expected = FILE12;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest24() throws Exception {
        File inputFile = createInputFile(FILE9);

        String args[] = {"-l", "-r", "3", inputFile.getPath()};
        Main.main(args);

        String expected = FILE9;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest25() throws Exception {

        String args[] = {"-n", "filedoesnotexist.txt"};
        Main.main(args);

        assertEquals("File Not Found", errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest26() throws Exception {
        File inputFile = createInputFile(FILE9);

        String args[] = {"-r", "40", "-d", "5", "-n", "2", "-c", "abc etc a", inputFile.getPath()};
        Main.main(args);

        String expected = "2510114833 291606 091714064918 25114810 481033 \r" +
                "25114810481033203306291606301429311332172516\r" +
                "172009112922311121172308172029243120013533\r" +
                "14062018301426160909'215114\r" +
                "51162515231521212330\r" +
                "27383113'15'!3601!320815302123'22233018095124'150851";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest27() throws Exception {
        File inputFile = createInputFile(FILE9);

        String args[] = {"-c", "abc", "-c", "def", inputFile.getPath()};
        Main.main(args);

        String expected = "Up with thE whitE anD golD\r" +
                "down with thE rED anD black\r" +
                "GEorgia TEch is out For a victory\r" +
                "WEll Drop a battlE axE on gEorgia's hEaD\r" +
                "WhEn wE mEEt hEr our tEam is surE to bEat hEr\r" +
                "down on thE olD Farm thErE will bE no sounD\r" +
                "'Till our bow wows rips through thE air\r" +
                "WhEn thE battlE is ovEr gEorgia's tEam will bE FounD\r" +
                "With thE YEllow JackEt's swarming 'rounD! HEy!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest28() throws Exception {
        File inputFile = createInputFile(FILE14);

        String args[] = {"-x", inputFile.getPath()};
        Main.main(args);

        String expected = FILE14;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest29() throws Exception {
        File inputFile = createInputFile(FILE9);

        String args[] = {"-l", "3", inputFile.getPath()};
        Main.main(args);

        String expected = "with the white and goldUp \r" +
                "n with the red and blackDow\r" +
                "rgia Tech is out for a victoryGeo\r" +
                "l drop a battle axe on georgia's headWel\r" +
                "n we meet her our team is sure to beat herWhe\r" +
                "n on the old farm there will be no soundDow\r" +
                "ll our bow wows rips through the air'Ti\r" +
                "n the battle is over georgia's team will be foundWhe\r" +
                "h the Yellow Jacket's swarming 'round! Hey!Wit";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: New Test Case for Refactoring
    // Frame #: Instructor Provided New Test Case
    @Test
    public void mainTest30() throws Exception {
        File inputFile = createInputFile(FILE10);

        String args[] = {"-c", "x", "-d", "5", inputFile.getPath()};
        Main.main(args);

        String expected = FILE10;

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

}