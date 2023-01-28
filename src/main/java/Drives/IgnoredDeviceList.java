package Drives;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

public class IgnoredDeviceList {
    private static final Logger logger = LoggerFactory.getLogger(IgnoredDeviceList.class);

    private static final String FILENAME = "ignored.txt";
    private Map<String, Boolean> devices = new HashMap<>();

    private File file;

    public IgnoredDeviceList() throws IOException {
        File ignoredFile = new File(getTempDir(), FILENAME);

        if (!ignoredFile.exists() && !ignoredFile.createNewFile()) {
            throw new AccessDeniedException("Failed to create options file in " + ignoredFile);
        }

        file = ignoredFile;
        readFile();
    }

    public boolean containsIdentifier(String identifier) {
        return devices.containsKey(identifier);
    }

    public void addIdentifier(String identifier) {
        if (containsIdentifier(identifier)) {
            return;
        }

        devices.putIfAbsent(identifier, Boolean.TRUE);
        saveFile();
    }

    public void removeIdentifier(String identifier) {
        if (!containsIdentifier(identifier)) {
            return;
        }

        devices.remove(identifier);
        saveFile();
    }

    private void readFile() throws IOException {
        // Read file
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String st;
        while ((st = reader.readLine()) != null)
            devices.putIfAbsent(st, Boolean.TRUE);
    }

    private void saveFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
            for (String id : devices.keySet()) {
                writer.write(id);
            }

        } catch (IOException e) {
            logger.error("File not found", e);
        }
    }

    private File getTempDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }
}
