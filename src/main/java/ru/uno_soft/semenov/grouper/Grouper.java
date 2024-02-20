package ru.uno_soft.semenov.grouper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Grouper {

    private static final String SEPARATOR = ";";

    public static List<List<String>> findGroups(List<String> lines) {
        List<Map<String, Integer>> indexOfWordToGroup = new ArrayList<>(); // Индекс - позиция слова, Map<Слово, Группа>
        List<List<String>> groups = new ArrayList<>(); // Индекс - номер группы, список - строки группы
        Map<Integer, Integer> mergedGroups = new HashMap<>(); // Map<K, V>, где группа K принадлежит группе V
        for (String line : lines) {
            String[] words = line.split(SEPARATOR);
            SortedSet<Integer> foundInGroups = new TreeSet<>();
            Map<String, Integer> newWords = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (indexOfWordToGroup.size() == i) {
                    indexOfWordToGroup.add(new HashMap<>());
                }

                if (isWordEmpty(word)) {
                    continue;
                } else if (isWordIncorrect(word)) {
                    break;
                }
                Integer groupOfWord = indexOfWordToGroup.get(i).get(word);
                if (groupOfWord != null) {
                    groupOfWord = findGroupOfWord(groupOfWord, mergedGroups);
                    foundInGroups.add(groupOfWord);
                } else {
                    newWords.put(word, i);
                }
            }
            int groupNumber = getGroupNumber(foundInGroups, groups);
            for (Map.Entry<String, Integer> newWord : newWords.entrySet()) {
                indexOfWordToGroup.get(newWord.getValue()).put(newWord.getKey(), groupNumber);
            }
            mergeGroups(groupNumber, foundInGroups, mergedGroups, groups);
            groups.get(groupNumber).add(line);
        }
        removeNullLists(groups);
        sortGroupsByDescSize(groups);
        return groups;
    }

    private static void sortGroupsByDescSize(List<List<String>> groups) {
        groups.sort((group1, group2) -> group2.size() - group1.size());
    }

    private static void removeNullLists(List<List<String>> groups) {
        List<List<String>> listOfNullList = new ArrayList<>(){{
            add(null);
        }};
        groups.removeAll(listOfNullList);
    }

    private static int getGroupNumber(SortedSet<Integer> foundInGroups, List<List<String>> groups) {
        if (foundInGroups.isEmpty()) {
            groups.add(new ArrayList<>());
            return groups.size() - 1;
        }
        return foundInGroups.first();
    }

    private static void mergeGroups(int groupNumber,
                                    SortedSet<Integer> foundInGroups,
                                    Map<Integer, Integer> mergedGroups,
                                    List<List<String>> groups) {
        for (int mergeGroupNumber : foundInGroups) {
            if (mergeGroupNumber != groupNumber) {
                mergedGroups.put(mergeGroupNumber, groupNumber);
                groups.get(groupNumber).addAll(groups.get(mergeGroupNumber));
                groups.set(mergeGroupNumber, null);
            }
        }
    }

    private static int findGroupOfWord(int groupOfWord, Map<Integer, Integer> mergedGroups) {
        while (mergedGroups.containsKey(groupOfWord)) {
            groupOfWord = mergedGroups.get(groupOfWord);
        }
        return groupOfWord;
    }


    /**
     * Пустым считается слово состоящее из "" (2 кавычек) или вообще без символов!
     */
    private static boolean isWordEmpty(String word) {
        return word.equals("\"\"") || word.isEmpty();
    }

    /**
     * Слово считается некорректным, если имеет кавычки в слове
     * Например: "8383"200000741652251", "79855053897"83100000580443402" - некорректные слова.
     */
    private static boolean isWordIncorrect(String word) {
        return word.matches("\"[0-9]*\"[0-9]*\"");
    }
}
