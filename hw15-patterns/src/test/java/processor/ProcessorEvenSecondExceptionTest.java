package processor;

import org.junit.jupiter.api.Test;
import org.qadev.model.Message;
import org.qadev.processor.Processor;
import org.qadev.processor.ProcessorEvenSecondException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorEvenSecondExceptionTest {

    @Test
    void processShouldThrowExceptionWhenSecondIsEven() {
        LocalDateTime evenSecondTime = LocalDateTime.of(2026, 7, 27, 10, 0, 2);
        Processor processor = new ProcessorEvenSecondException(() -> evenSecondTime);
        Message message = new Message.Builder(1L).field1("Hello").build();
        assertThrows(IllegalStateException.class, () -> processor.process(message));
    }

    @Test
    void processShouldReturnMessageWhenSecondIsOdd() {
        LocalDateTime oddSecondTime = LocalDateTime.of(2026, 7, 27, 10, 0, 3);
        Processor processor = new ProcessorEvenSecondException(() -> oddSecondTime);
        Message message = new Message.Builder(1L).field1("Hello").build();
        Message result = processor.process(message);
        assertNotNull(result);
    }
}
