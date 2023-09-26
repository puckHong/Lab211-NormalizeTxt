/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import model.StringInput;

/**
 *
 * @author ASUS
 */
public class HandleNormalization {

    StringInput si = new StringInput();

    public void runFile() {
        System.out.println(System.getProperty("user.dir"));
        try {
            Scanner input = new Scanner(new File("input.txt"));

            StringBuilder result = new StringBuilder();
            boolean firstLine = true;
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (!firstLine) {
                    result.append(" ");
                }
                line = formatOneSpace(line);
                line = formatSpecialCharacters(line);
                line = afterDotUpperCase(line);
                line = noSpaceQuotes(line);
                line = firstUpercase(line);
                line = lastAddDot(line);
                result.append(line);
                firstLine = false;
            }
            input.close();
            result.deleteCharAt(result.length() - 1);
            result.append(".");
            BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
            si.setInput(result.toString());
            output.write(result.toString());
            output.close();
            System.out.println("===================Successful==================");
            System.out.println("");
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
        } catch (IOException e) {
            System.out.println("Error reading or writing to file");

        }
    }

    public String formatOneSpaceSpecial(String line, String character) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] strings = line.split("\\s*\\" + character + "\\s*");

        // appen every word and character special distance is one space
        for (String oneWord : strings) {
            stringBuffer.append(oneWord + " " + character);

            stringBuffer.append(" ");
        }
        return stringBuffer.toString().trim().substring(0, stringBuffer.length() - 3);
    }

    public String formatOneSpace(String line) {
        line = line.toLowerCase();
        line = line.replaceAll("\\s+", " ");
        line = formatOneSpaceSpecial(line, ".");
        line = formatOneSpaceSpecial(line, ",");
        line = formatOneSpaceSpecial(line, ":");
        line = formatOneSpaceSpecial(line, "\"");
        return line.trim();
        //abcd. Avc
    }

    public String formatSpecialCharacters(String line) {
        StringBuffer stringBuffer = new StringBuffer(line);
        // check from first to last before .,:; then delete
        for (int i = 0; i < stringBuffer.length() - 1; i++) {
            if (stringBuffer.charAt(i) == ' '
                    && stringBuffer.charAt(i + 1) == '.'
                    || stringBuffer.charAt(i + 1) == ','
                    || stringBuffer.charAt(i + 1) == ':') {
                stringBuffer.deleteCharAt(i);
            }
            //ádasdasd
        }
        return stringBuffer.toString().trim();
    }

    public String afterDotUpperCase(String line) {
        StringBuffer stringBuffer = new StringBuffer(line);
        int lengthLine = stringBuffer.length();
        // check from first to last after . then UpperCase
        for (int i = 0; i < lengthLine - 2; i++) {
            if (stringBuffer.charAt(i) == '.') {
                char afterDot = stringBuffer.charAt(i + 2);
                stringBuffer.setCharAt(i + 2, Character.toUpperCase(afterDot));
            }
        }
        return stringBuffer.toString().trim();
    }

    public String noSpaceQuotes(String line) {
        StringBuffer stringBuffer = new StringBuffer(line);
        int countQuetes = 0;
        for (int i = 0; i < stringBuffer.length(); i++) {
            if (stringBuffer.charAt(i) == '"' && countQuetes % 2 == 0) {
                stringBuffer.deleteCharAt(i + 1);
                countQuetes++;
            } else if (stringBuffer.charAt(i) == '"' && countQuetes % 2 == 1
                    && i != 0) {
                stringBuffer.deleteCharAt(i - 1);
                countQuetes++;
            }
        }
        return stringBuffer.toString().trim();
    }

    public String firstUpercase(String line) {
        line = line.substring(0);
        StringBuffer stringBuffer = new StringBuffer(line);
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetter(line.charAt(i))) {
                stringBuffer.setCharAt(i, Character.toUpperCase(line.charAt(i)));
                break;
            }
        }
        return stringBuffer.toString().trim();
    }

    public String lastAddDot(String line) {
        if (line.endsWith(".")) {
            return line;
        } else {
            return line + ".";
        }
    }

    public boolean isLineEmpty(String line) {
        if (line.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int countLine() {
        int countLine = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
            String line;
            // write until end file
            while ((line = br.readLine()) != null) {
                // check line empty
                if (isLineEmpty(line)) {
                    continue;
                }
                countLine++;
            }
            br.close();
            pw.close();

        } catch (FileNotFoundException ex) {
            System.err.println("Can't found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return countLine;
    }

}
