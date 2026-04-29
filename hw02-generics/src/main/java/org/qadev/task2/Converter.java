package org.qadev.task2;

import java.util.ArrayList;

/*** Задание 2 где конвертируем массив в List */
public class Converter<T> {
    private final T[] array;

    public Converter(T[] array) {
        this.array = array;
    }

    public ArrayList<T> convert() {
        ArrayList<T> result = new ArrayList<>();
        for (T item : array) {
            result.add(item);
        }
        return result;
    }
}
