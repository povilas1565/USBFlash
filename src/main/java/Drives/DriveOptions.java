package Drives;

import net.samuelcampos.usbdrivedetector.process.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class DriveOptions {
    static Logger logger = LoggerFactory.getLogger(DriveOptions.class);

    public static String getDriveUUID(String driveName) {

        String pathSeparator = "\\";

        if (driveName.contains(pathSeparator)) {
            driveName = driveName.substring(0, driveName.indexOf(pathSeparator));
        }

        String wmicKeyName = "VolumeSerialNumber";
        String command = "wmic logicaldisk where DeviceID=\"" + driveName + "\" get " + wmicKeyName;

        ArrayList<String> result = getRawCommandOutput(command);

        if (result.size() != 2) {
            logger.error("Received wrong number of result strings. ");
            result.forEach((str) -> logger.error(str));
            return "";
        }

        return result.get(1);
    }

    private static ArrayList<String> getRawCommandOutput(String command) {
        final ArrayList<String> resultLines = new ArrayList<>();

        try (CommandExecutor commandExecutor = new CommandExecutor(command)) {
            commandExecutor.processOutput(outputLine -> {
                if (!outputLine.isEmpty()) {
                    resultLines.add(outputLine);
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return resultLines;
    }
}
