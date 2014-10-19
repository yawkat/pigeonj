package at.yawk.pigeon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class Address {
    private final ByteBuf address;

    public Address(ByteBuf address) {
        this.address = Unpooled.unmodifiableBuffer(address);
    }
}
