package web_java;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String INFORMATION = "src/information";
    private static final String RESULT = "src/result.txt";
    public static void main(String[] args) {
        SummFunction mainTask = new SummFunction(new File(INFORMATION));
        int sum = new ForkJoinPool().invoke(mainTask);
        writingresult(RESULT, sum);
        try {
            int numberInFile = Integer.parseInt(Files.readString(Paths.get(RESULT)));
            System.out.println(" Сума = " + numberInFile);
        } catch (IOException | RuntimeException ex) {
            ex.printStackTrace();
        }
    }
    public static void writingresult(String path, int number) {
        try (PrintWriter writer = new PrintWriter(RESULT)) {
            writer.write(String.valueOf(number));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
