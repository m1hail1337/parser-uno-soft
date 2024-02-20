package ru.uno_soft.semenov.grouper;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrouperTest {

    @Test
    void testFindMergingGroups() {
        List<String> lines = List.of(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";\"\";\"100\""
        );
        List<List<String>> actual = Grouper.findGroups(lines);
        assertEquals(1, actual.size());
        assertEquals(lines, actual.get(0));
    }

    @Test
    void testFindNotMergingGroups() {
        List<String> lines = List.of(
                "\"100\";\"200\";\"300\"",
                "\"200\";\"300\";\"100\""
        );
        List<List<String>> actual = Grouper.findGroups(lines);
        assertEquals(2, actual.size());
        assertEquals(List.of("\"100\";\"200\";\"300\""), actual.get(0));
        assertEquals(List.of("\"200\";\"300\";\"100\""), actual.get(1));
    }
}
