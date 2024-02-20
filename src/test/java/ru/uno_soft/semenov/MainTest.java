package ru.uno_soft.semenov;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MainTest {

    private static final Path OUTPUT_PATH = Path.of(Main.OUTPUT_FILE_NAME);

    @Test
    void testGroupMergingToOne() throws IOException {
        Main.main(new String[] { "src/test/resources/test1.txt"} );
        assertTrue(Files.exists(OUTPUT_PATH));
        List<String> outputLines = Files.lines(OUTPUT_PATH).collect(Collectors.toList());
        assertEquals("Число групп с более чем одним элементом: 1", outputLines.get(0));
        assertEquals("Группа 1", outputLines.get(1));
        List<String> expectedLines = List.of(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";\"\";\"100\""
        );
        assertEquals(expectedLines, outputLines.subList(2, outputLines.size()));
    }

    @Test
    void testGroupNotMerged() throws IOException {
        Main.main(new String[] { "src/test/resources/test2.txt"} );
        assertTrue(Files.exists(OUTPUT_PATH));
        List<String> outputLines = Files.lines(OUTPUT_PATH).collect(Collectors.toList());
        assertEquals("Число групп с более чем одним элементом: 0", outputLines.get(0));
        assertEquals("Группа 1", outputLines.get(1));
        assertEquals("\"100\";\"200\";\"300\"", outputLines.get(2));
        assertEquals("Группа 2", outputLines.get(3));
        assertEquals("\"200\";\"300\";\"100\"", outputLines.get(4));
    }

    // Добавил после того, как узнал правильный ответ
    @Test
    void testNumberOfGroupsInLngTxt() throws IOException {
        Main.main(new String[] { "src/main/resources/lng.txt"});
        assertTrue(Files.exists(OUTPUT_PATH));
        List<String> outputLines = Files.lines(OUTPUT_PATH).collect(Collectors.toList());
        assertEquals("Число групп с более чем одним элементом: 1910", outputLines.get(0));
    }

    /*
    Добавил после того, как узнал правильный ответ
    @Test
    void testNumberOfGroupsInLngBigCsv() throws IOException {
        Main.main(new String[] { "src/main/resources/lng-big.csv"});
        assertTrue(Files.exists(OUTPUT_PATH));
        List<String> outputLines = Files.lines(OUTPUT_PATH).collect(Collectors.toList());
        assertEquals("Число групп с более чем одним элементом: 105036", outputLines.get(0));
    }
    */
}
