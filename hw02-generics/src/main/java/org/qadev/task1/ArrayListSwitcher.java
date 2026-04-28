package org.qadev.task1;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с List<>
 * @param <T>
 */
public class ArrayListSwitcher<T> {
    private final List<T> array;

    public ArrayListSwitcher(List<T> array) {
        this.array = array;
    }

    public List<T> swap(int indexOfFirstElement, int indexOfSecondElement) throws Exception {
        int size = array.size();
        if (indexOfFirstElement < size & indexOfSecondElement < size) {
            if (indexOfFirstElement == indexOfSecondElement) {
                return array;
            }
            else {
                T firstElement = array.get(indexOfFirstElement);
                T secondElement = array.get(indexOfSecondElement);
                List<T> result = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    if (i == indexOfFirstElement) {
                        result.add(secondElement);
                    }
                    else if (i == indexOfSecondElement) {
                        result.add(firstElement);
                    }
                    else {
                        result.add(array.get(i));
                    }
                }
                return result;
            }
        }
        else throw new Exception("Индекс в параметре метода превышает максимальный индекс массива");
    }
}
