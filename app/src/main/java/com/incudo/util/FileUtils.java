package com.incudo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileUtils {
    private static final String RESOURCES_PATH = "./resources/";
    private static final String OUTPUT_PATH = "./output/";

    private Path getFileFromResource(String fileName) {
        Path filePath = Paths.get(RESOURCES_PATH, fileName).normalize();
        if (!checkResourceExistence(filePath)) {
            throw new IllegalArgumentException("File non trovato! " + fileName);
        }
        return filePath;
    }

    private boolean checkResourceExistence(Path filePath) {
        return Files.exists(filePath);
    }

    public <T> List<T> loadData(String fileName, Function<String, T> parser)
            throws IOException, IllegalArgumentException {
        List<T> items = new ArrayList<>();
        Path filePath = getFileFromResource(fileName);
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                items.add(parser.apply(line));
            }
        }
        return items;
    }

    public <T> void updateFile(String fileName, String header, List<T> items, Function<T, String> mapper)
            throws IOException, IllegalArgumentException {
        Path filePath = getFileFromResource(fileName);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath, StandardCharsets.UTF_8))) {
            writer.println(header);

            for (T item : items) {
                writer.println(mapper.apply(item));
            }
        }
    }

    public <T> void exportFile(String fileName, String header, List<T> items, Function<T, String> mapper)
            throws IOException {
        Path outputDir = Paths.get(OUTPUT_PATH).normalize();
        Files.createDirectories(outputDir);

        Path filePath = outputDir.resolve(fileName);

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath, StandardCharsets.UTF_8))) {
            writer.println(header);

            for (T item : items) {
                writer.println(mapper.apply(item));
            }
        }
    }
}
