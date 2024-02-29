package magus.task.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIo {
    private static final String TASK_LIST_FILE_NAME = "Magus.txt";
    private static final String TASK_LIST_FILE_DIRECTORY = "./data/";
    private static final String TASK_LIST_FILE_PATH = TASK_LIST_FILE_DIRECTORY + TASK_LIST_FILE_NAME;

    public static List<String> readTaskListFile() {
        List<String> taskCommandStrings = new ArrayList<>();
        File taskListFile = new File(TASK_LIST_FILE_PATH);

        Scanner scanner;
        try {
            scanner = new Scanner(taskListFile);
        } catch (FileNotFoundException e) {
            return taskCommandStrings;
        }

        while (scanner.hasNextLine()) {
            String taskCommandString = scanner.nextLine();
            taskCommandStrings.add(taskCommandString);
        }
        scanner.close();

        return taskCommandStrings;
    }

    public static void writeTaskListFile(List<String> taskCommandStrings) {
        File taskListFile = new File(TASK_LIST_FILE_PATH);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            createDataDirectory();
            fileWriter = new FileWriter(taskListFile);
            bufferedWriter = new BufferedWriter(fileWriter);

            for (String taskCommandString: taskCommandStrings) {
                bufferedWriter.write(taskCommandString);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                assert bufferedWriter != null;
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createDataDirectory() throws IOException {
        Path taskListDirectory = Paths.get(TASK_LIST_FILE_DIRECTORY);
        Files.createDirectories(taskListDirectory);
    }
}
