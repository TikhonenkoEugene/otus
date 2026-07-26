package org.qadev.handler;

import org.qadev.listener.Listener;
import org.qadev.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}
