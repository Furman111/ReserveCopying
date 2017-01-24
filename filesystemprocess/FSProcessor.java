package filesystemprocess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Furman on 24.01.2017.
 */
public class FSProcessor {

    public static boolean copyFromTo(File from, File to) {
        try {
            Files.copy(from.toPath(), to.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                FSProcessor.deleteFile(f);
            }
        }
        return file.delete();
    }

    public static boolean createDirectory(File dirFile) {
        return dirFile.mkdir();
    }

}
