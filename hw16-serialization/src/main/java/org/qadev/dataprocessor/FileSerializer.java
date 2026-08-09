package org.qadev.dataprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        String json = convertToJson(data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(json);
        }
        catch (IOException exception) {
            throw new FileProcessException("Ошибка записи в файл: " + fileName + "\n" + exception.getMessage());
        }
    }

    private String convertToJson(Map<String, Double> data) {
        StringJoiner sj = new StringJoiner(",\n  ", "{\n  ", "\n}");

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String escapedKey = escapeString(entry.getKey());
            sj.add(String.format("\"%s\": %s", escapedKey, entry.getValue()));
        }

        return sj.toString();
    }

    private String escapeString(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
