package at.yawk.pigeon;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author yawkat
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Loft {
    private final DatagramRouter router;
    private final Map<Address, Set<Consumer<Message>>> listeners = new ConcurrentHashMap<>();

    public Channel getChannel(Address address) {
        return new Channel() {
            @Override
            public void publish(Message message) {
                router.publish(new Datagram(MessageId.random(), address, message));
            }

            @Override
            public void subscribe(Consumer<Message> listener) {
                listeners.computeIfAbsent(address, addr -> Collections.newSetFromMap(new ConcurrentHashMap<>()))
                        .add(listener);
            }
        };
    }

    public static class Builder {
        private final List<DriverFactory> driverFactories = new ArrayList<>();

        public Builder append(DriverFactory driver) {
            driverFactories.add(driver);
            return this;
        }

        public Loft build() {
            List<Driver> drivers = new ArrayList<>();
            DatagramRouter router = new DatagramRouter(drivers);
            Loft loft = new Loft(router);

            drivers.add(datagram -> {
                Set<Consumer<Message>> listeners = loft.listeners.get(datagram.getAddress());
                if (listeners != null) {
                    Message body = datagram.getBody();
                    listeners.forEach(l -> l.accept(body));
                }
            });
            drivers.addAll(driverFactories.stream().map(f -> f.createDriver(router)).collect(Collectors.toList()));

            return loft;
        }
    }
}
