package UI;
import Drives.DriveOptions;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import javax.swing.*;

public class FlashDrivePanel extends JPanel {

    public static final int ROW_HEIGHT = 40;
    private String identifier;

    public FlashDrivePanel(USBStorageDevice dev) {
         identifier = DriveOptions.getDriveUUID(dev.getRootDirectory().toString());
         JPanel panel = new JPanel();
         panel.setSize(50,50);
         JLabel label = new JLabel(dev.getRootDirectory() + "( " + dev.getDeviceName() + " )");
         label.setSize(250, ROW_HEIGHT);
         label.setVerticalAlignment(SwingConstants.BOTTOM);
         this.add(label);
         JButton button = new JButton(identifier);
         button.setSize(50, ROW_HEIGHT);
         button.setVerticalAlignment(SwingConstants.BOTTOM);
         this.add(button);
    }
}
