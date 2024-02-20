package ru.uno_soft.semenov;

import ru.uno_soft.semenov.grouper.Grouper;
import ru.uno_soft.semenov.parser.Parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static final String OUTPUT_FILE_NAME = "output.txt";
    private static final Charset utf8 = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        String inputFile = args[0];
        List<String> lines = Parser.getLinesFromFile(inputFile);
        List<List<String>> groups = Grouper.findGroups(lines);
        buildOutputFile(groups);
    }

    private static void buildOutputFile(List<List<String>> groups) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, utf8))) {
            writeFirstString(groups, bufferedWriter);
            writeGroupsStrings(groups, bufferedWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFirstString(List<List<String>> groups, BufferedWriter bufferedWriter) throws IOException {
        long groupsWithMoreThanOneElement = groups.stream().filter(group -> group.size() > 1).count();
        bufferedWriter
                .append("Число групп с более чем одним элементом: ")
                .append(String.valueOf(groupsWithMoreThanOneElement))
                .append("\n");
    }

    private static void writeGroupsStrings(List<List<String>> groups, BufferedWriter bufferedWriter) throws IOException {
        int groupCounter = 1;
        for (List<String> group : groups) {
            bufferedWriter.append("Группа ").append(String.valueOf(groupCounter++)).append("\n");
            for (String line : group) {
                bufferedWriter.append(line).append("\n");
            }
        }
    }
}
