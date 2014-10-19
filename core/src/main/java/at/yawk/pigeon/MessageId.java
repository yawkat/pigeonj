package at.yawk.pigeon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.Random;
import lombok.Value;

/**
 * @author yawkat
 */
@Value
public class MessageId {
    private static final int DEFAULT_LENGTH = 16;
    private static final Random RNG = new Random();

    private final ByteBuf id;

    public MessageId(ByteBuf id) {
        this.id = Unpooled.unmodifiableBuffer(id);
    }

    public static MessageId random() {
        return random(DEFAULT_LENGTH);
    }

    public static MessageId random(int length) {
        byte[] bytes = new byte[length];
        RNG.nextBytes(bytes);
        return new MessageId(Unpooled.wrappedBuffer(bytes));
    }
}
