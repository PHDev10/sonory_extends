package br.edu.ifpb.sonoryextends.service;

import javafx.concurrent.Task;

import java.io.*;
import java.util.List;

public class AudioConversionService {
    public Task<Boolean> createConversionTask(File inputFile, File outputFile) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                List<String> command = List.of("ffmepg", "-y", "-i", inputFile.getAbsolutePath(), outputFile.getAbsolutePath());

                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        updateProgress(-1, 1);
                        System.out.println(line);
                    }
                }

                int exitCode = process.waitFor();
                return exitCode == 0;
            }
        };
    }
}
