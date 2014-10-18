package at.yawk.pigeon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

/**
 * @author yawkat
 */
@Getter
public class Message {
    private final ByteBuf message;

    public Message(ByteBuf message) {
        this.message = Unpooled.unmodifiableBuffer(message);
    }
}
