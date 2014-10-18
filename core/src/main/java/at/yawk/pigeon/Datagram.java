package at.yawk.pigeon;

import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class Datagram {
    private final MessageId id;
    private final Address address;
    private final Message body;
}
