package org.qadev.task3;

public class Main {

    /*** Запуск задания 3 */
    public static void main(String[] args) throws Exception {
        String[] inputData = new String[]{"red", "blue", "pink", "white", "red", "black", "green", "red", "green",
                "black", "white", "red", "blue", "yellow", "black", "red", "purple", "blue", "green", "white"};

        CountWords countWords = new CountWords(inputData);
        countWords.findAndPrintUniqueWords();

    }
}