package at.yawk.pigeon;

import java.util.stream.Stream;

/**
 * @author yawkat
 */
public interface Driver {
    void publish(Datagram datagram);

    default Stream<Driver> children() {
        return Stream.empty();
    }
}
