package org.qadev.dataprocessor;

import org.qadev.model.Measurement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        if (data.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Double> result = new HashMap<>();

        for (Measurement measurement : data) { // не знаю правильно ли тут понял (можно было и стримы сделать, но хочу так)
            result.merge(measurement.name(), measurement.value(), Double::sum);
        }

        return result;
    }
}
