import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import UI.ErrorFlash;
import UI.UI;
import java.util.Date;

public class USBFlash {

    private static Logger logger = LoggerFactory.getLogger(USBFlash.class);

    public static void main(String[] args) {
        logger.info("Started " + (new Date()).toString());

        try {
            startUI();
        } catch (Exception e) {
            (new ErrorFlash("Program has encountered an unexpected error. Please restart.")).showFlash();
            logger.error("At initialize", e);
        }
    }

    private static void startUI() {
        DrivesList flashes = new DrivesList();

        Runnable uiRunner = () -> {
            UI ui = new UI(flashes);
            ui.setVisible(true);
        };

        uiRunner.run();
    }
}
