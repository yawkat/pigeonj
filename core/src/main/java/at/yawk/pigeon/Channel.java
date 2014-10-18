package at.yawk.pigeon;

import java.util.function.Consumer;

/**
 * @author yawkat
 */
public interface Channel {
    void publish(Message message);

    void subscribe(Consumer<Message> listener);
}
