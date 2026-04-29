package org.qadev.task3;

import java.util.*;

/**
 * Класс по заданию 3 считаем количество слова в массиве надеюсь задание понял верно,
 * там дальше отдельно печатаем только уникальные слова
 */
public class CountWords {
    private final String[] array;

    public CountWords(String[] array) {
        this.array = array;
    }

    public void findAndPrintUniqueWords() {
        Set<String> words = new HashSet<>();

        for (String word : array) {
            int counter = 0;

            if (!words.contains(word)) {
                words.add(word);

                for (String it : array) {
                    if (it.equals(word)) {
                        counter++;
                    }
                }
                System.out.println(word + " = " + counter);
            }
        }
    }
}
