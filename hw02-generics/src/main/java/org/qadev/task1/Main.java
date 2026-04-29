package org.qadev.task1;

public class Main {

    /*** Запуск задания 1 */
    public static void main(String[] args) throws Exception {

        // создаем массив значений и выводим его на экран
        Integer[] arrItems = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 10};
        printArray(arrItems);

        // создаем объект класса ArraySwitcher, вызываем метод swap() для замены мест и выводим на экран
        ArraySwitcher<Integer> arraySwitcher = new ArraySwitcher<>(arrItems);
        Integer[] arraySwitched = arraySwitcher.swap(3, 8);
        printArray(arraySwitched);
    }

    private static void printArray(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length - 1) {
                System.out.print(arr[i] + " ");
            }
            else {
                System.out.println(arr[i]);
            }
        }
    }
}
