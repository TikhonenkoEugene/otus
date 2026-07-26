package org.qadev.listener.homework;

import java.util.Optional;
import org.qadev.model.Message;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
