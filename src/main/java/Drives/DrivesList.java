package Drives;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrivesList implements Iterable<USBStorageDevice> {
    private static Logger logger = LoggerFactory.getLogger(DrivesList.class);
    private static IgnoredDeviceList ignored;
    private List<USBStorageDevice> drives;
    private USBDeviceDetectorManager manager;

    public DrivesList() throws IllegalStateException {
        manager = new USBDeviceDetectorManager(5000);
        refresh();
    }

    public static void main(String[] args) {
        DrivesList dl = new DrivesList();

        for (USBStorageDevice dev : dl) {
            File file = dev.getRootDirectory();
            System.out.println(DriveOptions.getDriveUUID(file.getPath()));
        }
    }

    public Iterator<USBStorageDevice> iterator() {
        return drives.iterator();
    }

    public void refresh() {
        try {
            ignored = new IgnoredDeviceList();
        } catch (IOException e) {
            logger.error("Cannot read ignored file. Will not ignore any", e);
        }
        drives = getDrivesList();
    }

    public List<USBStorageDevice> getDrivesList() {
        // Build list
        List<USBStorageDevice> result = new ArrayList<>();
        // Ignore greylist devices
        for (USBStorageDevice dev : manager.getRemovableDevices()) {
            String uuid = DriveOptions.getDriveUUID(dev.getRootDirectory().getPath());
            if (!ignored.containsIdentifier(uuid)) {
                result.add(dev);
            }
        }
        return result;
    }

    public int size() {
        return drives.size();
    }

    public USBDeviceDetectorManager getManager() {
        return manager;
    }
}
