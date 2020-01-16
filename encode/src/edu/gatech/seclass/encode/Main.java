package edu.gatech.seclass.encode;

import java.io.File;
import java.io.FileWriter;

public class Main {

/*
Empty Main class for Individual Project 1 in GT CS6300
 */

    public static void main(String[] args) {
        String filename = "";
        int rotateLength = -1;
        int charToNumLength = -1;
        int onlyString = -1;
        int DuplicatesAllowance = -1;
        String stringArg = "";
        String DuplicatesArg = "";
        String LeftRotationArg = "";
        String RightRotationArg = "";
        String textInput= "";
        String textOutput = "";
        String indexUpper = "";
        String DuplicatesTextOutput = "";
        String RotateTextOutput = "";
        String CapitalizationTextOutput = "";
        String CharToNumTextOutput = "";
        String FinalOutputText = "";
        boolean showError = false;

        if (args.length == 0) {
            showError = true;
        } else if (args.length == 1) {
            filename = args[args.length - 1];
            DuplicatesAllowance = -1;
            rotateLength = -1;
            charToNumLength = -1;
            stringArg = "";
        } else if (args.length != 1 && (!args[0].startsWith("-"))) {
            showError = true;
        } else {
            int i = 0;
            while (i < args.length) {
                String optionArg = args[i];
                if (optionArg.startsWith("-")) {
                    String optionPar = i + 1 < args.length ? args[i + 1] : null;
                    if (optionArg.equals("-l")) {
                        if ((optionPar == null) || (optionPar.startsWith("-")) || (optionPar.toLowerCase().contains(".tmp")) || (optionPar.toLowerCase().contains(".txt"))) {
                            showError = true;
                        } else if (Integer.parseInt(optionPar) < 1) {
                            showError = true;
                        } else {
                            try {
                                LeftRotationArg = optionArg;
                                rotateLength = Integer.parseInt(optionPar);
                                i++;
                            } catch (NumberFormatException e) {
                                showError = true;
                            }
                        }
                    } else if (optionArg.equals("-d")) {
                        if ((optionPar == null) || (optionPar.startsWith("-")) || (optionPar.toLowerCase().contains(".tmp")) || (optionPar.toLowerCase().contains(".txt"))) {
                            showError = true;
                        } else {
                            try {
                                DuplicatesArg = optionArg;
                                DuplicatesAllowance = Integer.parseInt(optionPar);
                                if (Integer.parseInt(optionPar) < 0) {
                                    showError = true;
                                }
                                i++;
                            } catch (NumberFormatException e) {
                                showError = true;
                            }
                        }
                    } else if (optionArg.equals("-r")) {
                        if ((optionPar == null) || (optionPar.startsWith("-")) || (optionPar.toLowerCase().contains(".tmp")) || (optionPar.toLowerCase().contains(".txt"))) {
                            showError = true;
                        } else {
                             try {
                                 RightRotationArg = optionArg;
                                 rotateLength = Integer.parseInt(optionPar);
                                 if (Integer.parseInt(optionPar) < 1) {
                                     showError = true;
                                 }
                                 i++;
                             } catch (NumberFormatException e) {
                                 showError = true;
                             }
                        }
                    } else if (optionArg.equals("-c")) {
                        if ((optionPar == null) || (optionPar.startsWith("-")) || (optionPar.toLowerCase().contains(".tmp")) || (optionPar.toLowerCase().contains(".txt"))) {
                            showError = true;
                        } else if ("".equals(optionPar) && args.length == 3) {
                            onlyString = 1;
                            stringArg = optionPar;
                        } else {
                            stringArg = optionPar;
                            i++;
                        }
                    } else if (optionArg.equals("-n")) {
                        if (optionPar == null || optionPar.isEmpty() || (optionPar.contains("-c")) || (optionPar.contains("-l")) || (optionPar.contains("-r")) || (optionPar.toLowerCase().contains(".tmp")) || (optionPar.toLowerCase().contains(".txt"))) {
                            charToNumLength = 0;
                        } else if ((optionPar.equals("c")) || (optionPar.equals("l")) || (optionPar.equals("r")) ) {
                            showError = true;
                        } else if (Integer.parseInt(optionPar) > 25 || Integer.parseInt(optionPar) < 0){
                            showError = true;
                        } else {
                            charToNumLength = Integer.parseInt(optionPar);
                            i++;
                        }
                    } else if (!optionArg.equals("-l") && !optionArg.equals("-r") && !optionArg.equals("-n") && !optionArg.equals("-c") && !optionArg.equals("-d")) {
                        showError = true;
                    }
                } else {
                    filename = optionArg;
                } i++;
            }
        }

        File file = new File(filename);

        if (!LeftRotationArg.isEmpty() && !RightRotationArg.isEmpty())
            showError = true;

        if (showError)
            usage();

        if (showError)
            return;

        encode encode = new encode();


        try {

            if (!(file.exists())) {
                System.err.print("File Not Found");
                return;
            }
            else {
                encode.setFile(file);
                textInput = encode.text();
            }

            if (DuplicatesAllowance == 0) {
                FinalOutputText = "";
            } else {
                if (DuplicatesAllowance != -1 && !DuplicatesArg.isEmpty()) {
                    DuplicatesTextOutput = encode.RemoveDuplicateChars(textInput, DuplicatesAllowance);
                }
                if (rotateLength != -1 && !LeftRotationArg.isEmpty()) {
                    if (!DuplicatesTextOutput.isEmpty()) {
                        RotateTextOutput = encode.rotateString(LeftRotationArg, DuplicatesTextOutput, rotateLength);
                    } else {
                        RotateTextOutput = encode.rotateString(LeftRotationArg, textInput, rotateLength);
                    }
                }
                if (rotateLength != -1 && !RightRotationArg.isEmpty()) {
                    if (!DuplicatesTextOutput.isEmpty()) {
                        RotateTextOutput = encode.rotateString(RightRotationArg, DuplicatesTextOutput, rotateLength);
                    } else {
                        RotateTextOutput = encode.rotateString(RightRotationArg, textInput, rotateLength);
                    }
                }
                if (!stringArg.isEmpty()) {
                    if (!DuplicatesTextOutput.isEmpty() && RotateTextOutput.isEmpty()) {
                        indexUpper = encode.reverseCapUpper(DuplicatesTextOutput, stringArg);
                        textOutput = encode.reverseCapLower(DuplicatesTextOutput, stringArg);
                        CapitalizationTextOutput = encode.reverseCapTotal(textOutput, indexUpper);

                    } else if (!DuplicatesTextOutput.isEmpty() && !RotateTextOutput.isEmpty()) {
                        indexUpper = encode.reverseCapUpper(RotateTextOutput, stringArg);
                        textOutput = encode.reverseCapLower(RotateTextOutput, stringArg);
                        CapitalizationTextOutput = encode.reverseCapTotal(textOutput, indexUpper);
                    } else if (DuplicatesTextOutput.isEmpty() && !RotateTextOutput.isEmpty()) {
                        indexUpper = encode.reverseCapUpper(RotateTextOutput, stringArg);
                        textOutput = encode.reverseCapLower(RotateTextOutput, stringArg);
                        CapitalizationTextOutput = encode.reverseCapTotal(textOutput, indexUpper);
                    } else {
                        indexUpper = encode.reverseCapUpper(textInput, stringArg);
                        textOutput = encode.reverseCapLower(textInput, stringArg);
                        CapitalizationTextOutput = encode.reverseCapTotal(textOutput, indexUpper);
                    }
                }
                if (charToNumLength != -1) {
                    if (!CapitalizationTextOutput.isEmpty()){
                        CharToNumTextOutput = encode.charToNumber(CapitalizationTextOutput , charToNumLength);
                    } else if (CapitalizationTextOutput.isEmpty() && !RotateTextOutput.isEmpty()) {
                        CharToNumTextOutput = encode.charToNumber(RotateTextOutput, charToNumLength);
                    } else if (CapitalizationTextOutput.isEmpty() && RotateTextOutput.isEmpty() && !DuplicatesTextOutput.isEmpty()) {
                        CharToNumTextOutput = encode.charToNumber(DuplicatesTextOutput, charToNumLength);
                    } else {
                        CharToNumTextOutput = encode.charToNumber(textInput, charToNumLength);
                    }
                }
                if (!CharToNumTextOutput.isEmpty()) {
                    FinalOutputText = CharToNumTextOutput;
                }
                if (CharToNumTextOutput.isEmpty() && !CapitalizationTextOutput.isEmpty()) {
                    FinalOutputText = CapitalizationTextOutput;
                }
                if (CharToNumTextOutput.isEmpty() && CapitalizationTextOutput.isEmpty() && !RotateTextOutput.isEmpty()) {
                    FinalOutputText = RotateTextOutput;
                }
                if (CharToNumTextOutput.isEmpty() && CapitalizationTextOutput.isEmpty() && RotateTextOutput.isEmpty() && !DuplicatesTextOutput.isEmpty()) {
                    FinalOutputText = DuplicatesTextOutput;
                }
                if (onlyString == 1) {
                    indexUpper = encode.reverseCapUpper(textInput, stringArg);
                    textOutput = encode.reverseCapLower(textInput, stringArg);
                    CapitalizationTextOutput = encode.reverseCapTotal(textOutput, indexUpper);
                    FinalOutputText = CapitalizationTextOutput;
                }
                if (rotateLength == -1 && stringArg.length() == 0 && charToNumLength == -1 && onlyString == -1 && DuplicatesAllowance == -1) {
                    charToNumLength = 13;
                    FinalOutputText = encode.charToNumber(textInput, charToNumLength);
                }
            }


            File newCotents = new File(filename);
            FileWriter fooWriter = new FileWriter(newCotents, false);
            fooWriter.write(FinalOutputText);
            fooWriter.close();

        } catch (Exception e) {
            return;
        }
    }

    private static void usage() {
        System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>");
    }

}
