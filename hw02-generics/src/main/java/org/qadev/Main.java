package org.qadev;

import org.qadev.task1.ArrayListSwitcher;
import org.qadev.task1.ArraySwitcher;
import org.qadev.task2.Converter;
import org.qadev.task3.CountWords;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Задание 1:");
        printArraySwitcher();
        System.out.println("==========");
        printArrayListSwitcher();

        System.out.println("Задание 2:");
        printArrayConverted();
        System.out.println("==========");

        System.out.println("Задание 3:");
        String[] inputData = new String[]{"red", "blue", "white", "red", "black", "green", "red", "green", "black",
                "white", "red", "blue", "yellow", "black", "red", "purple", "pink", "blue", "green", "white"};
        Map<String, Integer> arrCounted = CountWords.count(inputData);
        System.out.println(arrCounted);
        printUniqueWords(arrCounted);
        System.out.println("==========");
    }


    public static void printArrayListSwitcher() throws Exception {
        List<String> arrItems = Arrays.asList("0. Banana", "1. Car", "2. Stick", "3. Lamp", "4. Ball", "5. Window",
                "6. Mirror", "7. Chair", "8. Lake", "9. Laptop", "10. TV");
        arrItems.forEach(System.out::println);
        ArrayListSwitcher<String> arrayListSwitcher = new ArrayListSwitcher<>(arrItems);
        List<String> arrSwitched = arrayListSwitcher.swap(1, 6);
        System.out.println("----------");
        arrSwitched.forEach(System.out::println);
    }

    public static void printArraySwitcher() throws Exception {
        Integer[] arrItems = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 10};
        for (int i = 1; i < arrItems.length; i++) {
            System.out.print(arrItems[i] + " ");
        }
        System.out.println();
        ArraySwitcher<Integer> arraySwitcher = new ArraySwitcher<>(arrItems);
        Integer[] arraySwitched = arraySwitcher.swap(3, 8);
        System.out.println("----------");
        for (int i = 1; i < arraySwitched.length; i++) {
            System.out.print(arraySwitched[i] + " ");
        }
        System.out.println();
    }

    public static void printArrayConverted() {
        Integer[] arrItems = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 10};
        Converter<Integer> converter = new Converter<>(arrItems);
        converter.convert().forEach(System.out::println);
    }

    private static void printUniqueWords(Map<String, Integer> arrCounted) {
        arrCounted.forEach((s, integer) -> {
            if (integer == 1) {
                System.out.println(s);
            }
        });
    }
}