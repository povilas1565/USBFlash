package UI;
import Drives.DrivesList;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import net.samuelcampos.usbdrivedetector.events.USBStorageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UI extends JFrame {

    private static Logger logger = LoggerFactory.getLogger(UI.class);

    private static JPanel devicesPanel;

    // USB elements
    private DrivesList flashes;

    public UI(DrivesList flashes) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.flashes = flashes;

        addUIElements();

        this.setVisible(true);

        flashes.getManager().addDriveListener(this::listRefreshed);
    }

    public static void main(String[] args) {
        UI ui = new UI(new DrivesList());
    }

    private void addUIElements() {
        this.setSize(400, 400);
        this.setLayout(new BorderLayout());
        this.setName("USBFlash");

        // Add menu bar
        JMenuBar menu = new JMenuBar();
        menu.add(new JMenu("Файл"));
        this.add(menu, BorderLayout.NORTH);

        // Add device panel
        devicesPanel = new JPanel();
        this.add(devicesPanel, BorderLayout.WEST);
        refreshDevicePanels(devicesPanel);

        // Add exit btn
    }

    private void refreshDevicePanels(JPanel jpanel) {
        // Build a panel for each drive
        ArrayList<JPanel> panels = buildDevicePanels(flashes);

        for (JPanel panel: panels) {
            jpanel.add(panel);
        }
    }

    private ArrayList<JPanel> buildDevicePanels(DrivesList drivesList) {
        ArrayList<JPanel> panels = new ArrayList<>();

        for (USBStorageDevice dev : drivesList) {
            JPanel panel = new FlashDrivePanel(dev);
            panels.add(panel);
        }
        return panels;
    }

    private void listRefreshed(USBStorageEvent usbStorageEvent) {
        logger.info(usbStorageEvent.toString());
        refreshDevicePanels(devicesPanel);
    }
}
