package org.qadev.task2;

public class Main {

    /*** Запуск задания 2 */
    public static void main(String[] args) {
        Integer[] arrItems = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 10};
        Converter<Integer> converter = new Converter<>(arrItems);

        converter
                .convert()
                .forEach(System.out::println);
    }
}
