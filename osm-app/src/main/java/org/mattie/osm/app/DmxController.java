package org.mattie.osm.app;

import com.fazecast.jSerialComm.SerialPort;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.dmx.DmxBuffer;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt Tyler
 */
@Slf4j
@Service
public class DmxController {

    private SerialPort commPort;

    private BooleanProperty dmxEnabled = new SimpleBooleanProperty(false);

    private ScheduledExecutorService executorService;

    @Getter
    private DmxBuffer dmxBuffer;

    @PreDestroy
    public void destroy() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public DmxController() {
        dmxBuffer = new DmxBuffer();
        dmxEnabled.addListener((ov, oldVal, newVal) -> {
            if (newVal) {
                connect();
            } else {
                disconnect();
            }
        });
    }

    public BooleanProperty dmxEnabledProperty() {
        return dmxEnabled;
    }

    public void setDmxEnabled(boolean dmxEnabled) {
        this.dmxEnabled.set(dmxEnabled);
    }

    public boolean isDmxEnabled() {
        return dmxEnabled.get();
    }

    private void connect() {
        if (commPort == null || !commPort.isOpen()) {
            log.debug("Connecting");
            commPort = SerialPort.getCommPort("COM5");
            boolean opened = commPort.openPort(0, 518, 0);
            if (!opened) {
                log.warn("Unable to open comm port 5");
                setDmxEnabled(false);
            } else {
                executorService = Executors.newSingleThreadScheduledExecutor();
                ScheduledFuture<?> future = executorService.scheduleWithFixedDelay(() -> render(), 20, 20, TimeUnit.MILLISECONDS);
            }
        }
    }

    private void disconnect() {
        if (commPort != null && commPort.isOpen()) {
            log.debug("Disconnecting");
            commPort.closePort();

            if (executorService != null) {
                executorService.shutdownNow();
                executorService = null;
            }
        }
    }

    private int[] outputData = new int[512 + 6];

    private byte[] outputBuffer = new byte[512 + 6];

    private void render() {
        if (commPort == null || !commPort.isOpen()) {
            return;
        }

        //      Arrays.fill(outputData, 0);
        int len = 513;
        outputData[0] = 0x7E;
        outputData[1] = 6;
        outputData[2] = len & 0xFF;
        outputData[3] = (len >> 8) & 0xFF;
        outputData[4] = 0;
        outputData[outputData.length - 1] = 0xE7;

        System.arraycopy(dmxBuffer.getDataBuffer(), 0, outputData, 5, 512);

        for (int ndx = 0; ndx < outputData.length; ndx++) {
            outputBuffer[ndx] = (byte) outputData[ndx];
        }

        commPort.writeBytes(outputBuffer, outputBuffer.length);
        commPort.flushIOBuffers();
    }
}
