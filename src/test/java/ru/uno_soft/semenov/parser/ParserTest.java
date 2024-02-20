package ru.uno_soft.semenov.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void testGetLinesFromTest1Txt() {
        List<String> actual = Parser.getLinesFromFile("src/test/resources/test1.txt");
        List<String> expected = List.of(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";\"\";\"100\""
        );
        assertEquals(expected, actual);
    }

    @Test
    void testGetLinesFromTest2Txt() {
        List<String> actual = Parser.getLinesFromFile("src/test/resources/test2.txt");
        List<String> expected = List.of(
                "\"100\";\"200\";\"300\"",
                "\"200\";\"300\";\"100\""
        );
        assertEquals(expected, actual);
    }

    @Test
    void testGetLinesFromLngTxt() {
        List<String> actual = Parser.getLinesFromFile("src/main/resources/lng.txt");
        assertEquals(1_000_000, actual.size());
    }
}
