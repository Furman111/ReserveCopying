package CopyingFiles;

import Modes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Furman on 21.01.2017.
 */
public class DirectoryCopyObject extends copyObject {

    private Mode mode;
    private long timeToCopy;
    private File file;
    private File copyingFileSource;


    public void fullCopy(File from, File to) throws IOException {
        try {
            File fromNext, toNext;
            Files.copy(from.toPath(), to.toPath(), REPLACE_EXISTING);
            if (from.isDirectory())
                for (int i = 0; i < from.listFiles().length; i++) {
                    fromNext = from.listFiles()[i];
                    toNext = new File(to.getPath() + "\\" + from.listFiles()[i].getName());
                    fullCopy(fromNext, toNext);
                }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void incCopy(File from, File to) throws IOException {
        try {
            File fromNext, toNext;
            if (from.isDirectory())
                for (int i = 0; i < from.listFiles().length; i++) {
                    fromNext = from.listFiles()[i];
                    toNext = new File(to.getPath() + "\\" + from.listFiles()[i].getName());
                    fullCopy(fromNext, toNext);
                }
            if (from.isDirectory()) {
                if (!to.exists()) Files.copy(from.toPath(), to.toPath(), REPLACE_EXISTING);
            }
            if (from.isFile()) {
                if (!to.exists())
                    Files.copy(from.toPath(), to.toPath(), REPLACE_EXISTING);
                else if (from.lastModified() > to.lastModified())
                    Files.copy(from.toPath(), to.toPath(), REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public boolean copy() {
        try {
            switch (mode) {
                case FULL:
                    fullCopy(file, copyingFileSource);
                    break;
                case INC:
                case DEC:
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public DirectoryCopyObject(File file) {
        this.file = file;
    }

    public DirectoryCopyObject(String path) {
        this.file = new File(path);
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public long getTimeToCopy() {
        return timeToCopy;
    }

    public void setTimeToCopy(long timeToCopy) {
        this.timeToCopy = timeToCopy;
    }

    public boolean isFile() {
        return false;
    }

    public boolean isDirectory() {
        return true;
    }

    public String getObjectName() {
        return file.getName();
    }

    public String getObjectPath() {
        return file.getPath();
    }

    public void setCopyingFileSource(File file) {
        copyingFileSource = new File(file.getPath() + "\\" + this.file.getName());
    }

    public File getCopyingFileSource() {
        return copyingFileSource;
    }
}
