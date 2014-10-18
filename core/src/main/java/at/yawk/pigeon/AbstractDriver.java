package at.yawk.pigeon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yawkat
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractDriver implements Driver {
    private final DatagramRouter router;
}
