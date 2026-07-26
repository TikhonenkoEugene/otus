package org.qadev.listener.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.qadev.listener.Listener;
import org.qadev.model.Message;

public class HistoryListener implements Listener, HistoryReader {
    private final List<Message> history = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        if (msg != null) {
            Message copyMsg = new Message.Builder(msg.getId())
                    .field1(msg.getField1())
                    .field2(msg.getField2())
                    .field3(msg.getField3())
                    .field4(msg.getField4())
                    .field5(msg.getField5())
                    .field6(msg.getField6())
                    .field7(msg.getField7())
                    .field8(msg.getField8())
                    .field9(msg.getField9())
                    .field10(msg.getField10())
                    .field11(msg.getField12())
                    .field12(msg.getField11())
                    .field13(msg.getField13())
                    .build();
            history.add(copyMsg);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public List<Message> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        throw new UnsupportedOperationException();
    }
}
