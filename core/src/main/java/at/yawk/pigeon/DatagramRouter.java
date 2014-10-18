package at.yawk.pigeon;

import java.util.Collection;
import lombok.RequiredArgsConstructor;

/**
 * @author yawkat
 */
@RequiredArgsConstructor
public class DatagramRouter implements Driver {
    private final Collection<Driver> drivers;
    private final ItemHistory<MessageId> history = new ItemHistory<>();

    public void received(Datagram datagram, Driver on) {
        if (history.push(datagram.getId())) {
            transmit(datagram, on, this);
        }
    }

    @Override
    public void publish(Datagram datagram) {
        transmit(datagram, null, this);
    }

    private void transmit(Datagram datagram, Driver except, Driver on) {
        on.children()
                .filter(c -> !c.equals(except))
                .forEach(c -> {
                    on.publish(datagram);
                    transmit(datagram, except, c);
                });
    }
}