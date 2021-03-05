/*
 * NAME: Diane Li
 * PID: A15773774
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * A class that counts similarity in lines from an undetermined number of command line arguments
 * 
 * @author Diane Li
 * @since 03/04/2021
 */
public class LineCounter {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int DECIMAL_PERCENT = 100;

    /**
     * Method to print the filename to the console
     * @param filename filename to print
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to the console
     * @param compareFileName name of the file being compared
     * @param percentage similarity percentage
     */
    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    /**
     * Main method.
     * @param args names of the files to compare
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];
        for (int i = 0; i < numArgs; i++) {
            tableList[i] = new HashTable(MIN_INIT_CAPACITY);
        }

        // Preprocessing: Read every file and create a HashTable
        int[] linesPerFile = new int[numArgs];

        for (int i = 0; i < numArgs; i++) {
            File file = new File(args[i]);
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    // inserts each line into its hash table
                    tableList[i].insert(scanner.nextLine());
                    linesPerFile[i]++;
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }

        // Find similarities across files
        for (int i = 0; i < numArgs; i++) {
            int[] countOverlapped = new int[numArgs];
            File file = new File(args[i]);

            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String thisLine = scanner.nextLine();
                    // checks this line in every other hash table
                    for (int j = 0; j < numArgs; j++) {
                        // skip if same argument number (file)
                        if (j == i) { continue; }
                        // increments count of overlapped if this line is in other file
                        else if (tableList[j].lookup(thisLine)) { countOverlapped[j]++; }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }

            printFileName(args[i]);
            for (int j = 0; j < numArgs; j++) {
                // skip if same file
                if (j == i) { continue; }
                // prints overlap with file otherwise
                else {
                    if (linesPerFile[i] == 0) {
                        printStatistics(args[j], 0);
                    } else {
                        double percentOverlapped = (double) (countOverlapped[j] + 1) / linesPerFile[i] * DECIMAL_PERCENT;
                        printStatistics(args[j], (int)percentOverlapped);
                    }
                }
            }
        }
    }

}