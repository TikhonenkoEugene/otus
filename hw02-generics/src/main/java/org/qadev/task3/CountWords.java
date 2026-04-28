package org.qadev.task3;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс по заданию 3 считаем количество слова в массиве надеюсь задание понял верно,
 * там дальше отдельно печатаем только уникальные слова
 */
public class CountWords {

    public static Map<String, Integer> count(String[] arr) {
        Map<String, Integer> result = new HashMap<>();
        for (String item : arr) {
            result.merge(item, 1, Integer::sum);
        }
        return result;
    }
}
