package org.qadev.processor;

import org.qadev.model.Message;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ProcessorEvenSecondException implements Processor {
    private final Supplier<LocalDateTime> timeProvider;

    public ProcessorEvenSecondException(Supplier<LocalDateTime> timeProvider) {
        this.timeProvider = timeProvider;
    }

    public ProcessorEvenSecondException() {
        this(LocalDateTime::now);
    }


    @Override
    public Message process(Message message) {
        int second = timeProvider
                .get()
                .getSecond();

        if (second % 2 == 0) {
            throw new IllegalStateException(
                    "Это исключение на четную секунду: " + second
            );
        }

        return message;
    }
}
