package CopyingFiles;

import Modes.Mode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;


import static java.nio.file.StandardCopyOption.*;


/**
 * Created by Furman on 21.01.2017.
 */
public class FileCopyObject extends copyObject {

    private long timeToCopy;
    private File file;
    private File copyingFileSource;
    private Mode mode;
    private int numberOfCopies;
    private ArrayList<Long> timesOfCopies;

    public FileCopyObject(File file, File copyingFileSource, Mode mode, long time) {
        this.file = file;
        this.copyingFileSource = copyingFileSource;
        this.timeToCopy = time;
        this.mode = mode;
        numberOfCopies = 0;
        timesOfCopies = new ArrayList<>();
        timesOfCopies.clear();
        this.copy();
    }

    public boolean copy() {
        try {
            switch (mode) {
                case INC:
                    incCopy();
                case DEC:
                    decCopy();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void decCopy() throws IOException {
        try {
            if (numberOfCopies == 0) {
                numberOfCopies++;
                File temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
                Files.copy(file.toPath(), temp.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
                timesOfCopies.add(new Long(file.lastModified()));
            } else {
                File temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
                if (file.lastModified() > temp.lastModified()) {
                    if (numberOfCopies == 1) {
                        numberOfCopies++;
                        timesOfCopies.add(new Long(file.lastModified()));
                    }
                    temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
                    Files.copy(file.toPath(), temp.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
                    timesOfCopies.set(1, new Long(file.lastModified()));
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void incCopy() throws IOException {
        try {
            if (numberOfCopies == 0) {
                numberOfCopies++;
                File temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
                Files.copy(file.toPath(), temp.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
                timesOfCopies.add(new Long(file.lastModified()));
            } else {
                File temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
                if (file.lastModified() > temp.lastModified()) {
                    numberOfCopies++;
                    temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
                    Files.copy(file.toPath(), temp.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
                    timesOfCopies.add(new Long(file.lastModified()));
                } else
                    throw new IOException();
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public boolean delete() {
        try {
            for (int i = 1; i <= numberOfCopies; i++) {
                File temp = new File(copyingFileSource.getPath() + "\\" + "v" + i + "_" + file.getName());
                temp.delete();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean upgrade(int i) {
        try {
            if ((i >= 1) && (i <= numberOfCopies)) {
                File temp = new File(copyingFileSource.getPath() + "\\" + "v" + i + "_" + file.getName());
                Files.copy(temp.toPath(), file.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
            } else
                throw new IOException();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean upgrade(long time) {
        try {
            if (time < timesOfCopies.get(0).longValue()) throw new IOException();
            int i = 0;
            while ((i < numberOfCopies - 1) && (time > timesOfCopies.get(i).longValue())) i++;
            if (time < timesOfCopies.get(i).longValue())
                i--;
            i++;
            File temp = new File(copyingFileSource.getPath() + "\\" + "v" + i + "_" + file.getName());
            Files.copy(temp.toPath(), file.toPath(), REPLACE_EXISTING, COPY_ATTRIBUTES);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public long getTimeToCopy() {
        return timeToCopy;
    }

    public void setTimeToCopy(long timeToCopy) {
        this.timeToCopy = timeToCopy;
    }

    public boolean isFile() {
        return true;
    }

    public boolean isDirectory() {
        return false;
    }

    public String getObjectName() {
        return file.getName();
    }

    public String getObjectPath() {
        return file.getPath();
    }

    public File getCopyingFileSource() {
        return copyingFileSource;
    }

    public long getLastModification() {
        File temp = new File(copyingFileSource + "\\" + "v" + numberOfCopies + "_" + file.getName());
        return file.lastModified();
    }

}
