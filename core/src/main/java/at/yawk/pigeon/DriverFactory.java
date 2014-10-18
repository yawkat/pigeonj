package at.yawk.pigeon;

/**
 * @author yawkat
 */
public interface DriverFactory {
    Driver createDriver(DatagramRouter router);
}
