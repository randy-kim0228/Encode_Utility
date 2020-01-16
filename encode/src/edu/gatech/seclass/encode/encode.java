package edu.gatech.seclass.encode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class encode {

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public String text() {

        String fileContent;

        try {
            fileContent = readFile(file);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return fileContent;
    }

    public String RemoveDuplicateChars (String textLine, int DuplicatesAllowance) {

        StringBuilder sb = new StringBuilder();
        StringBuilder sbOutput = new StringBuilder(textLine);
        String DupRemovedTextLine;
        List<String> toRemoveFinal = new ArrayList<>();
        Map<Character, Integer> duplicatesMap = new HashMap<Character, Integer>();
        char[] chars = textLine.toCharArray();

        for (char oneCharacter: chars) {
            if (duplicatesMap.containsKey(oneCharacter)) {
                duplicatesMap.put(oneCharacter, duplicatesMap.get(oneCharacter) + 1);
            } else {
                duplicatesMap.put(oneCharacter, 1);
            }
        }

        String delimiter = "";
        for (Character character : duplicatesMap.keySet()) {
            Integer dupCounts = duplicatesMap.get(character);
            if (dupCounts > DuplicatesAllowance) {
                sb.append(delimiter);
                sb.append(character);
                delimiter = ",";
            }
        }

        if (Integer.parseInt(String.valueOf(sb.length())) == 0) {
            return textLine;
        } else {
            List<String> duplicatesCharLists = Arrays.asList(sb.toString().split(","));

            for (int i = 0; i < duplicatesCharLists.size(); i++) {
                String oneChar;
                oneChar = duplicatesCharLists.get(i);
                StringBuilder sbIndex = new StringBuilder();
                String delimiterIndex = "";
                int index = textLine.indexOf(oneChar);
                while(index >= 0) {
                    sbIndex.append(delimiterIndex);
                    sbIndex.append(index);
                    index = textLine.indexOf(oneChar, index + 1);
                    delimiterIndex = ",";
                }
                List<String> allDupIndex = Arrays.asList(sbIndex.toString().split(","));
                List<String >toRemove = allDupIndex.subList(DuplicatesAllowance, allDupIndex.size());
                toRemoveFinal.addAll(toRemove);
            }

            List<Integer> removeInt = new ArrayList<>();
            for (String indexString : toRemoveFinal) {
                removeInt.add(Integer.valueOf(indexString));
            }

            Collections.sort(removeInt, Collections.reverseOrder());

            for (Integer removing : removeInt) {
                sbOutput.deleteCharAt(removing);
            }
            DupRemovedTextLine = sbOutput.toString();
            return DupRemovedTextLine;
        }
    }

    private static void reverseCharArray(char chars[], int s, int e) {

        while (s < e) {
            char charReverse = chars[e];
            chars[e] = chars[s];
            chars[s] = charReverse;
            s++; e--;
        }
    }

    private char[] rotate2Right(char chars[], int offset, int arrayLength) {

        reverseCharArray(chars, 0, arrayLength - 1);
        reverseCharArray(chars, 0, offset - 1);
        reverseCharArray(chars, offset, arrayLength - 1);
        return chars;
    }

    private String rotateWithRotationOption(String rotationOption, String Line, int offset) {

        if (rotationOption.equals("-l")) {
            Line = Line.substring(offset) + Line.substring(0, offset);
        } else if (rotationOption.equals("-r")) {
            char[] chars = Line.toCharArray();
            chars = rotate2Right(chars, offset, Line.length());
            Line = String.valueOf(chars);
        }
        return Line;
    }

    public String rotateString (String rotationOption, String textLine, int rotationLength) {

        StringBuilder sb = new StringBuilder();
        String lines[] = textLine.split("(?<=[\r\n])");
        String Line;
        String charLine;
        String delimiter = "";
        String rotatedTextLine;

        for (int i = 0; i < lines.length; i++) {
            sb.append(delimiter);
            Line = lines[i];
            if (!Line.endsWith("\n") && !Line.endsWith("\r")) {
                charLine = Line;
                int offset = rotationLength % (charLine.length());
                charLine = rotateWithRotationOption(rotationOption, charLine, offset);
            } else {
                if (Line.endsWith("\r")){
                    delimiter = "\r";
                } else if (Line.endsWith("\n")) {
                    delimiter = "\n";
                }
                charLine = Line.substring(0, Line.length()-1);
                int offset = rotationLength % (charLine.length());
                charLine = rotateWithRotationOption(rotationOption, charLine, offset);
            }
            sb.append(charLine);
        }
        rotatedTextLine = sb.toString();
        return rotatedTextLine;
    }

    private static String removeDuplicatesArg (String stringArg) {

        StringBuilder sb = new StringBuilder();
        String dupArgRemoved;

        for (int i = 0; i < stringArg.length(); i++) {
            String uniqueStrArg = stringArg.substring(i, i + 1);
            if (sb.indexOf(uniqueStrArg) == -1) {
                sb.append(uniqueStrArg);
            }
        }
        dupArgRemoved = sb.toString();
        return dupArgRemoved;
    }

    public String reverseCapUpper (String textLine, String stringArg) {

        String indexContent;
        String delimiter = "";
        stringArg = stringArg.replaceAll( "[^a-zA-Z0-9 ]" , "" );
        stringArg = removeDuplicatesArg(stringArg);
        String stringArgUpper = stringArg.toUpperCase();
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        Pattern patternUpper = Pattern.compile("["+stringArgUpper+"]");
        Matcher mUpper = patternUpper.matcher(textLine);

        while(mUpper.find()) {
            Integer integer = mUpper.start();
            indexList.add(integer);
        }

        StringBuilder sb = new StringBuilder();

        for (Integer Int: indexList) {
            sb.append(delimiter);
            delimiter = ",";
            sb.append(Int);
        }
        indexContent = sb.toString();
        return indexContent;
    }

    public String reverseCapLower (String textLine, String stringArg) {

        stringArg = stringArg.replaceAll( "[^a-zA-Z0-9 ]" , "" );
        stringArg = removeDuplicatesArg(stringArg);
        String stringArgLower = stringArg.toLowerCase();

        for (int i = 0; i < stringArgLower.length(); i++) {
            String LowerStringArg = String.valueOf(stringArgLower.charAt(i));
            Pattern pattern = Pattern.compile(LowerStringArg);
            Matcher m = pattern.matcher(textLine);
            textLine = m.replaceAll(LowerStringArg.toUpperCase());
        }
        return textLine;
    }

    public String reverseCapTotal (String textLine, String indexUpper) {

        if (indexUpper.isEmpty()) {
            return textLine;
        } else {
            List<String> upperIndexString = Arrays.asList(indexUpper.split(","));
            List<Integer> upperIndexInt = new ArrayList<>();

            for (String index : upperIndexString) {
                upperIndexInt.add(Integer.valueOf(index));
            }

            for (Integer Int : upperIndexInt) {
                char[] characters = textLine.toCharArray();
                characters[Int] = Character.toLowerCase(textLine.charAt(Int));
                textLine = new String(characters);
            }
            return textLine;
        }
    }

    public String charToNumber (String textLine, Integer charToNumLength) {

        StringBuilder sb = new StringBuilder();
        String numChar;
        String numCharNew = "";

        for (int i = 0; i < textLine.length(); i++) {
            char c = textLine.charAt(i);
            if (c == 'a') numChar = String.format("%02d", 1 + charToNumLength);
            else if (c == 'b') numChar = String.format("%02d", 2 + charToNumLength);
            else if (c == 'c') numChar = String.format("%02d", 3 + charToNumLength);
            else if (c == 'd') numChar = String.format("%02d", 4 + charToNumLength);
            else if (c == 'e') numChar = String.format("%02d", 5 + charToNumLength);
            else if (c == 'f') numChar = String.format("%02d", 6 + charToNumLength);
            else if (c == 'g') numChar = String.format("%02d", 7 + charToNumLength);
            else if (c == 'h') numChar = String.format("%02d", 8 + charToNumLength);
            else if (c == 'i') numChar = String.format("%02d", 9 + charToNumLength);
            else if (c == 'j') numChar = String.format("%02d", 10 + charToNumLength);
            else if (c == 'k') numChar = String.format("%02d", 11 + charToNumLength);
            else if (c == 'l') numChar = String.format("%02d", 12 + charToNumLength);
            else if (c == 'm') numChar = String.format("%02d", 13 + charToNumLength);
            else if (c == 'n') numChar = String.format("%02d", 14 + charToNumLength);
            else if (c == 'o') numChar = String.format("%02d", 15 + charToNumLength);
            else if (c == 'p') numChar = String.format("%02d", 16 + charToNumLength);
            else if (c == 'q') numChar = String.format("%02d", 17 + charToNumLength);
            else if (c == 'r') numChar = String.format("%02d", 18 + charToNumLength);
            else if (c == 's') numChar = String.format("%02d", 19 + charToNumLength);
            else if (c == 't') numChar = String.format("%02d", 20 + charToNumLength);
            else if (c == 'u') numChar = String.format("%02d", 21 + charToNumLength);
            else if (c == 'v') numChar = String.format("%02d", 22 + charToNumLength);
            else if (c == 'w') numChar = String.format("%02d", 23 + charToNumLength);
            else if (c == 'x') numChar = String.format("%02d", 24 + charToNumLength);
            else if (c == 'y') numChar = String.format("%02d", 25 + charToNumLength);
            else if (c == 'z') numChar = String.format("%02d", 26 + charToNumLength);
            else if (c == 'A') numChar = String.format("%02d", 27 + charToNumLength);
            else if (c == 'B') numChar = String.format("%02d", 28 + charToNumLength);
            else if (c == 'C') numChar = String.format("%02d", 29 + charToNumLength);
            else if (c == 'D') numChar = String.format("%02d", 30 + charToNumLength);
            else if (c == 'E') numChar = String.format("%02d", 31 + charToNumLength);
            else if (c == 'F') numChar = String.format("%02d", 32 + charToNumLength);
            else if (c == 'G') numChar = String.format("%02d", 33 + charToNumLength);
            else if (c == 'H') numChar = String.format("%02d", 34 + charToNumLength);
            else if (c == 'I') numChar = String.format("%02d", 35 + charToNumLength);
            else if (c == 'J') numChar = String.format("%02d", 36 + charToNumLength);
            else if (c == 'K') numChar = String.format("%02d", 37 + charToNumLength);
            else if (c == 'L') numChar = String.format("%02d", 38 + charToNumLength);
            else if (c == 'M') numChar = String.format("%02d", 39 + charToNumLength);
            else if (c == 'N') numChar = String.format("%02d", 40 + charToNumLength);
            else if (c == 'O') numChar = String.format("%02d", 41 + charToNumLength);
            else if (c == 'P') numChar = String.format("%02d", 42 + charToNumLength);
            else if (c == 'Q') numChar = String.format("%02d", 43 + charToNumLength);
            else if (c == 'R') numChar = String.format("%02d", 44 + charToNumLength);
            else if (c == 'S') numChar = String.format("%02d", 45 + charToNumLength);
            else if (c == 'T') numChar = String.format("%02d", 46 + charToNumLength);
            else if (c == 'U') numChar = String.format("%02d", 47 + charToNumLength);
            else if (c == 'V') numChar = String.format("%02d", 48 + charToNumLength);
            else if (c == 'W') numChar = String.format("%02d", 49 + charToNumLength);
            else if (c == 'X') numChar = String.format("%02d", 50 + charToNumLength);
            else if (c == 'Y') numChar = String.format("%02d", 51 + charToNumLength);
            else if (c == 'Z') numChar = String.format("%02d", 52 + charToNumLength);
            else if (Character.isWhitespace(c)) numChar = Character.toString(c);
            else {
                numChar = Character.toString(c);
            }

            Integer numCharLength = numChar.length();

            if (numCharLength == 2) {
                Integer num = Integer.parseInt(numChar);
                if ((num > 26) && (num - charToNumLength < 27)){
                    num = num - 26;
                    numCharNew = String.format("%02d", num);
                } else if ((num > 52) && (num - charToNumLength < 53)){
                    num = num - 26;
                    numCharNew = String.format("%02d", num);
                } else {
                    numCharNew = String.format("%02d", num);
                }
            } else if (numCharLength == 1) {
                numCharNew = numChar;
            }
            sb.append(numCharNew);
        }
        return sb.toString();
    }

    private String readFile(File file) throws IOException {

        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        new DataInputStream(inputFile).readFully(buffer);
        inputFile.close();
        String inputstring = new String(buffer, "UTF-8");

        return inputstring;
    }
}