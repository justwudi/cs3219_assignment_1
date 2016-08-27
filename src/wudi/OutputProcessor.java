package wudi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Module that is in charge of processing output files.
 */
class OutputProcessor {

    private File file;
    private Scanner reader = new Scanner(System.in);

    boolean setOutputFile(String outputFilePath) throws IOProcessingException {
        file = new File(outputFilePath);

        if (file.isDirectory()) {
            throw new IOProcessingException("Output file cannot be a directory!");
        } else if (file.exists()) {
            System.out.print("Output file exists. Enter [y] to overwrite, or any other key to exit: ");
            String input = reader.next();

            if (!input.trim().equalsIgnoreCase("y")) {
                return false;
            }
        }

        return true;
    }

    void processFile(Alphabetizer alphabetizer) throws IOProcessingException {
        try {
            FileWriter fileWriter = new FileWriter(file, false);

            for (String line : alphabetizer.getAlphabeticallyShiftedLines()) {
                fileWriter.write(line);
                fileWriter.write("\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            throw new IOProcessingException("Error writing to output file!");
        }
    }
}
