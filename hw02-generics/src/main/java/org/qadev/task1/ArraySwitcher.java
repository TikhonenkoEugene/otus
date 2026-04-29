package org.qadev.task1;

import java.util.Arrays;

/**
 * Класс для работы с массивами
 * @param <T>
 */
public class ArraySwitcher<T> {

    private final T[] array;

    public ArraySwitcher(T[] array) {
        this.array = array;
    }

    public T[] swap(int indexOfFirstElement, int indexOfSecondElement) throws Exception {
        int size = array.length;
        if (indexOfFirstElement < size & indexOfSecondElement < size) {
            if (indexOfFirstElement == indexOfSecondElement) {
                return array;
            }
            else {
                T[] result = Arrays.copyOf(array, size);
                T temp = result[indexOfFirstElement];
                result[indexOfFirstElement] = result[indexOfSecondElement];
                result[indexOfSecondElement] = temp;
                return result;
            }
        }
        else throw new Exception("Индекс в параметре метода превышает максимальный индекс массива");
    }
}
