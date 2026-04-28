package org.qadev.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * Задание 2 где конвертируем массив в List
 * @param <T>
 */
public class Converter<T> {
    private final T[] array;

    public Converter(T[] array) {
        this.array = array;
    }

    public List<T> convert() {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            result.add(array[i]);
        }
        return result;
    }
}
