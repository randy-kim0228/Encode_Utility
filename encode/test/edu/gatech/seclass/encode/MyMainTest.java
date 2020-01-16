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

public class MyMainTest {

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

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    /*
     * TEST FILE CONTENT
     */
    private static final String FILE1 = "nkim84";
    private static final String FILE2 = "Hey Randy,\n" +
            "have you taken CS 7641 yet? I heard that the\n" +
            "Course is very challenging! If you have not\n" +
            "taken it yet, Let's take it together next Sem!";
    private static final String FILE3 = "Taradiddle";
    private static final String FILE4 = "";
    private static final String FILE5 = "cs6300 is SDP";
    private static final String FILE6 = "AaAa BbBb 1234 vvvv";
    private static final String FILE7 = "!@#$%0909HurryingUp";
    private static final String FILE8 = "!@#$%0909HurryingUp\n" +
            "!@#$%0909 HurryingUp\n" +
            "!@#$%0909Hurrying Up";
    private static final String FILE9 = "zZaA";
    /*
     *   TEST CASES
     */

    // Purpose: To provide an example of a test case where the string for an integer argument for '- n' is empty
    // Frame 5: Test Case 5 in the catpart.txt.tsl
    @Test
    public void encodeTest1() throws Exception {
        File inputFile1 = createInputFile(FILE1);
        String args[] = {"-n", "", inputFile1.getPath()};
        Main.main(args);
        String expected1 = "1411091384";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
    }

    // Purpose: To provide an example of a test case where the integer argument for '- n' > 25
    // Frame 2: Test Case 6 in the catpart.txt.tsl
    @Test
    public void encodeTest2() throws Exception {
        File inputFile2 = createInputFile(FILE1);
        String args[] = {"-n", "30", "-r", "1", inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where the integer argument for '-l' < 1
    // Frame 9: Test Case 9 in the catpart.txt.tsl
    @Test
    public void encodeTest3() throws Exception {
        File inputFile3 = createInputFile(FILE1);
        String args[] = {"-c", "ni", "-l", "-3", inputFile3.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where the string argument for '- c' are special characters
    // Frame 11: Test Case 11 in the catpart.txt.tsl
    @Test
    public void encodeTest4() throws Exception {
        File inputFile4 = createInputFile(FILE2);
        String args[] = {"-c", "!@#", "-r", "3", inputFile4.getPath()};
        Main.main(args);
        String expected4 = "Hey Randy,\n" +
                "have you taken CS 7641 yet? I heard that the\n" +
                "Course is very challenging! If you have not\n" +
                "taken it yet, Let's take it together next Sem!";

        String actual4 = getFileContent(inputFile4.getPath());
        assertEquals("The files differ!", expected4, actual4);
    }


    // Purpose: To provide an example of a test case where the string argument for '- c' is empty
    // Frame 35: Test Case 35 in the catpart.txt.tsl
    @Test
    public void encodeTest5() throws Exception {
        File inputFile5 = createInputFile(FILE2);
        String args[] = {"-c", "", "-r", "1", inputFile5.getPath()};
        Main.main(args);
        String expected5 = ",Hey Randy\n" +
                "ehave you taken CS 7641 yet? I heard that th\n" +
                "tCourse is very challenging! If you have no\n" +
                "!taken it yet, Let's take it together next Sem";

        String actual5 = getFileContent(inputFile5.getPath());
        assertEquals("The files differ!", expected5, actual5);
    }

    // Purpose: To provide an example of a test case where the string argument for '- c' is alphabets with many blanks
    // Frame 18: Test Case 18 in the catpart.txt.tsl
    @Test
    public void encodeTest6() throws Exception {
        File inputFile6 = createInputFile(FILE2);
        String args[] = {"-c", "r i o e", inputFile6.getPath()};
        Main.main(args);
        String expected6 = "HEy randy,\n" +
                "havE yOu takEn CS 7641 yEt? i hEaRd that thE\n" +
                "COuRsE Is vERy challEngIng! if yOu havE nOt\n" +
                "takEn It yEt, LEt's takE It tOgEthER nExt SEm!";

        String actual6 = getFileContent(inputFile6.getPath());
        assertEquals("The files differ!", expected6, actual6);
    }

    // Purpose: To provide an example of a test case where '- n', '- l', '- c' all simultaneously exist
    // Frame 27: Test Case 27 in the catpart.txt.tsl
    @Test
    public void encodeTest7() throws Exception {
        File inputFile7 = createInputFile(FILE3);
        String args[] = {"-l", "2", "-c", "tl", "-n", "3", inputFile7.getPath()};
        Main.main(args);
        String expected7 = "21040712070741082304";
        String actual7 = getFileContent(inputFile7.getPath());
        assertEquals("The files differ!", expected7, actual7);
    }

    // Purpose: To provide an example of a test case where there are no options of the arguments
    // Frame 2: Test Case 2 in the catpart.txt.tsl
    @Test
    public void encodeTest8() throws Exception {
        File inputFile8 = createInputFile(FILE3);
        String args[] = {inputFile8.getPath()};
        Main.main(args);
        String expected8 = "33140514172217172518";
        String actual8 = getFileContent(inputFile8.getPath());
        assertEquals("The files differ!", expected8, actual8);
    }

    // Purpose: To provide an example of a test case where exist '- n' and '- c' together with lowercase string input
    // Frame 33: Test Case 33 in the catpart.txt.tsl
    @Test
    public void encodeTest9() throws Exception {
        File inputFile9 = createInputFile(FILE3);
        String args[] = {"-n", "-c", "ad", inputFile9.getPath()};
        Main.main(args);
        String expected9 = "46271827300930301205";
        String actual9 = getFileContent(inputFile9.getPath());
        assertEquals("The files differ!", expected9, actual9);
    }


    // Purpose: To provide an example of a test case where the file is empty
    // Frame 1: Test Case 1 in the catpart.txt.tsl
    @Test
    public void encodeTest10() throws Exception {
        File inputFile10 = createInputFile(FILE4);
        String args[] = {"-n", "5", "-r", "11", inputFile10.getPath()};
        Main.main(args);
        String expected10 = "";
        String actual10 = getFileContent(inputFile10.getPath());
        assertEquals("The files differ!", expected10, actual10);
    }

    // Purpose: To provide an example of a test case where there only is '- c'
    // Frame 16: Test Case 16 in the catpart.txt.tsl
    @Test
    public void encodeTest11() throws Exception {
        File inputFile11 = createInputFile(FILE3);
        String args[] = {"-c", "ad",inputFile11.getPath()};
        Main.main(args);
        String expected11 = "TArADiDDle";
        String actual11 = getFileContent(inputFile11.getPath());
        assertEquals("The files differ!", expected11, actual11);
    }

    // Purpose: To provide an example of a test case where there is no input after the option argument '- n'
    // Frame 4: Test Case 4 in the catpart.txt.tsl
    @Test
    public void encodeTest12() throws Exception {
        File inputFile12 = createInputFile(FILE5);
        String args[] = {"-n", inputFile12.getPath()};
        Main.main(args);
        String expected12 = "03196300 0919 453042";
        String actual12 = getFileContent(inputFile12.getPath());
        assertEquals("The files differ!", expected12, actual12);
    }

    // Purpose: To provide an example of a test case where there is no input after the option argument '- r | -l'
    // Frame 8: Test Case 8 in the catpart.txt.tsl
    @Test
    public void encodeTest13() throws Exception {
        File inputFile13 = createInputFile(FILE5);
        String args[] = {"-l", inputFile13.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where there is no input after the option argument '- c'
    // Frame 10: Test Case 10 in the catpart.txt.tsl
    @Test
    public void encodeTest14() throws Exception {
        File inputFile14 = createInputFile(FILE5);
        String args[] = {"-c", inputFile14.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where there is one argument but it is none of the ('- n', '- r | l', '- c')
    // Frame 3: Test Case 3 in the catpart.txt.tsl (fixed for Deliverable 2, instead of '-d', using '-x')
    @Test
    public void encodeTest15() throws Exception {
        File inputFile15 = createInputFile(FILE5);
        String args[] = {"-x", "15", inputFile15.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where there are many blanks within the text file
    // not from frame: New Test Case
    @Test
    public void encodeTest16() throws Exception {
        File inputFile16 = createInputFile(FILE6);
        String args[] = {"-l", "3", "-c", "abv",inputFile16.getPath()};
        Main.main(args);
        String expected16 = "A bBbB 1234 VVVVaAa";
        String actual16 = getFileContent(inputFile16.getPath());
        assertEquals(expected16, actual16);
    }

    //Purpose: To provide an example of a test case where there is one blank within the string value for '-c'
    // Frame 17: Test Case 17 in the catpart.txt.ts
    @Test
    public void encodeTest17() throws Exception {
        File inputFile17 = createInputFile(FILE3);
        String args[] = {"-c", "d e", inputFile17.getPath()};
        Main.main(args);
        String expected17 = "TaraDiDDlE";
        String actual17 = getFileContent(inputFile17.getPath());
        assertEquals("The files differ!", expected17, actual17);
    }

    // Where there are two rotation options
    // not from frame: New Test Case
    @Test
    public void encodeTest18() throws Exception {
        File inputFile18 = createInputFile(FILE3);
        String args[] = {"-l", "2", "-r", "2",inputFile18.getPath()};
        Main.main(args);
        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where there is only one argument '-l' and the integer value of '-l' = 0
    // Frame 13: Test Case 13 in the catpart.txt.tsl
    @Test
    public void encodeTest19() throws Exception {
        File inputFile19 = createInputFile(FILE5);
        String args[] = {"-l", "0",inputFile19.getPath()};
        Main.main(args);
        assertEquals( "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test where the input string value of '-c' is non alphabet letter
    // not from frame: New Test Case
    @Test
    public void encodeTest20() throws Exception {
        File inputFile20 = createInputFile(FILE5);
        String args[] = {"-c", "1234",inputFile20.getPath()};
        Main.main(args);
        String expected20 = "cs6300 is SDP";
        String actual20 = getFileContent(inputFile20.getPath());
        assertEquals(expected20, actual20);
    }

    // Purpose: To provide an example of a test where the input string value of '-c' is empty
    // Frame 45: Test Case 45 in the catpart.txt.tsl
    @Test
    public void encodeTest21() throws Exception {
        File inputFile21 = createInputFile(FILE5);
        String args[] = {"-c", "", inputFile21.getPath()};
        Main.main(args);
        String expected21 = "cs6300 is SDP";
        String actual21 = getFileContent(inputFile21.getPath());
        assertEquals(expected21, actual21);
    }

    // Purpose: To provide an example of a test case where there is no option for '-n' and where there are many blanks within the text file
    // not from frame: New Test Case
    @Test
    public void encodeTest22() throws Exception {
        File inputFile22 = createInputFile(FILE6);
        String args[] = {"-n", inputFile22.getPath()};
        Main.main(args);
        String expected22 = "27012701 28022802 1234 22222222";
        String actual22 = getFileContent(inputFile22.getPath());
        assertEquals("The files differ!", expected22, actual22);
    }

    // Purpose: To provide an example of a test case where there is no '-' the the input option such that 'n' not '-n'
    // not from frame: New Test Case
    @Test
    public void encodeTest23() throws Exception {
        File inputFile23 = createInputFile(FILE6);
        String args[] = {"n", inputFile23.getPath()};
        Main.main(args);
        assertEquals( "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: To provide an example of a test case where exist '- n' and '- c' together with uppercase string input
    // Frame 33: Test Case 33 in the catpart.txt.tsl
    @Test
    public void encodeTest24() throws Exception {
        File inputFile24 = createInputFile(FILE1);
        String args[] = {"-n", "-c", "I", inputFile24.getPath()};
        Main.main(args);
        String expected24 = "1411351384";
        String actual24 = getFileContent(inputFile24.getPath());
        assertEquals("The files differ!", expected24, actual24);
    }

    // Purpose: To provide an example of a test case where there are '-c' and '-n' exist as input arguments
    // Frame 28: Test Case 28 in the catpart.txt.tsl
    @Test
    public void encodeTest25() throws Exception {
        File inputFile25 = createInputFile(FILE1);
        String args[] = {"-n", "-r", "1", inputFile25.getPath()};
        Main.main(args);
        String expected25 = "4141109138";
        String actual25 = getFileContent(inputFile25.getPath());
        assertEquals("The files differ!", expected25, actual25);
    }

    // Purpose: To provide an example of a test case where many input arguments not starting with '-'
    // not from frame: New Test Case
    @Test
    public void encodeTest26() throws Exception {
        File inputFile26 = createInputFile(FILE1);
        String args[] = {"r", "3", "c", "KiM", "-n", inputFile26.getPath()};
        Main.main(args);
        assertEquals( "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    //Purpose: Purpose: To provide an example of a test case where there is only one argument '-l' and the integer value of '-l' < 0
    // Frame 13: Another Test Case of Test Case 13 in the catpart.txt.tsl
    @Test
    public void encodeTest27() throws Exception {
        File inputFile27 = createInputFile(FILE1);
        String args[] = {"-n", "-3", inputFile27.getPath()};
        Main.main(args);
        assertEquals( "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    //Purpose: To provide an example of a test case where there are many special characters within text file and one argument '-c'
    // not from frame: New Test Case
    @Test
    public void encodeTest28() throws Exception {
        File inputFile28 = createInputFile(FILE7);
        String args[] = {"-c", "Hu", inputFile28.getPath()};
        Main.main(args);
        String expected28 = "!@#$%0909hUrryingup";
        String actual28 = getFileContent(inputFile28.getPath());
        assertEquals("The files differ!", expected28, actual28);
    }

    //Purpose: To provide an example of a test case where there is many duplicates within the string value for '-c'
    // not from frame: New Test Case
    @Test
    public void encodeTest29() throws Exception {
        File inputFile29 = createInputFile(FILE7);
        String args[] = {"-c", "uuuuuuuuuu", inputFile29.getPath()};
        Main.main(args);
        String expected29 = "!@#$%0909HUrryingup";
        String actual29 = getFileContent(inputFile29.getPath());
        assertEquals("The files differ!", expected29, actual29);
    }

    //Purpose: To provide an example of a test if the space separator is well regarded as character when rotating the string
    // not from frame: New Test Case
    @Test
    public void encodeTest30() throws Exception {
        File inputFile30 = createInputFile(FILE8);
        String args[] = {"-c", "HhHrrruuuuuuuuuu", "-r", "20", inputFile30.getPath()};
        Main.main(args);
        String expected30 = "p!@#$%0909hURRyingu\n"+
                "!@#$%0909 hURRyingup\n" +
                "!@#$%0909hURRying up";
        String actual30 = getFileContent(inputFile30.getPath());
        assertEquals("The files differ!", expected30, actual30);
    }

    //Purpose: To provide an example of a test if there are two rotational option arguments should call out error
    // not from frame: New Test Case
    @Test
    public void encodeTest31() throws Exception {
        File inputFile31 = createInputFile(FILE9);
        String args[] = {"-r", "1", "-l", "3", inputFile31.getPath()};
        Main.main(args);
        assertEquals( "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    //Purpose: To provide an example according to D2
    // not from frame: New Test Case
    @Test
    public void encodeTest32() throws Exception {
        File inputFile32 = createInputFile(FILE2);
        String args[] = {"-d", "3", "-l", "3", "-c", "ARr bC", inputFile32.getPath()};
        Main.main(args);
        String expected32 = " rAndy,Hey\n"+
                "e you tAkencS7641t?IhRdthhAv\n" +
                "RsisvRCllngig!Ifouvcou\n" +
                "L'skgxSm!ki,";
        String actual32 = getFileContent(inputFile32.getPath());
        assertEquals("The files differ!", expected32, actual32);
    }

    //Purpose: To provide an example according to D2
    // not from frame: New Test Case
    @Test
    public void encodeTest33() throws Exception {
        File inputFile33 = createInputFile(FILE2);
        String args[] = {"-d", "0", "-l", "3", "-c", "ARr bC", inputFile33.getPath()};
        Main.main(args);
        String expected33 = "";
        String actual33 = getFileContent(inputFile33.getPath());
        assertEquals("The files differ!", expected33, actual33);
    }

    //Purpose: To provide an example according to D2
    // not from frame: New Test Case
    @Test
    public void encodeTest34() throws Exception {
        File inputFile34 = createInputFile(FILE2);
        String args[] = {"-d", "-1", inputFile34.getPath()};

        Main.main(args);
        assertEquals( "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }
}
