package org.qadev;

import org.qadev.dataprocessor.*;
import org.qadev.model.Measurement;

import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // достаем из фала
        Loader loader = new ResourcesFileLoader("data.json");
        List<Measurement> measurements = loader.load();

        // группируем
        Processor processor = new ProcessorAggregator();
        Map<String, Double> grouped = processor.process(measurements);

        // сохраняем в файл
        Serializer serializer = new FileSerializer("output_file.json");
        serializer.serialize(grouped);
    }
}