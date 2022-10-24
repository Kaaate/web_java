package web_java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SummFunction extends RecursiveTask<Integer> {
    private final File file;
    public SummFunction(File file) {
        this.file = file;
    }
    @Override
    protected Integer compute() {
        String path = file.getPath();
        if (file.exists()) {
            int sum = 0;
            if (file.isDirectory()) {
                List<SummFunction> taskList = new ArrayList<>();
                for (File child : file.listFiles()) {
                    SummFunction task = new SummFunction(child);
                    task.fork();
                    taskList.add(task);
                }
                for (SummFunction task : taskList) {
                    sum += task.join();
                }
            } else if (path.endsWith(".txt")) {
                try {
                    sum += calculating(path);
                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                }
            }
            return sum;
        }
        System.err.print("Файлів не знадено");
        return null;}
    private int calculating(String path) throws IOException, RuntimeException {
        int sum = 0;
        List<String> strings = Files.readAllLines(Paths.get(path));
        for (String string : strings) {
            int number = Integer.parseInt(string);
            sum += number;
        } return sum;
    }
}
