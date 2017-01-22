package CopyingFiles;

import Modes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Furman on 21.01.2017.
 */

public class DirectoryCopyObject extends CopyObject {

    private Mode mode;
    private long timeToCopy;
    private File file;
    private File copyingFileSource;
    private ArrayList<CopyObject> copyObjects;
    private ArrayList<Long> timesOfCopies;


    public DirectoryCopyObject(File file, File copyingFileSource, Mode mode, long timeToCopy) {
        this.mode = mode;
        this.timeToCopy = timeToCopy;
        this.copyingFileSource = copyingFileSource;
        this.file = file;
        copyObjects = new ArrayList<>();
        timesOfCopies = new ArrayList<>();
        refreshCopyObjects();
    }


    void refreshCopyObjects() {
        File temp = new File(copyingFileSource.getPath() + "\\" + file.getName());
        boolean contains = false;
        for (int i = 0; i < file.list().length; i++) {
            for (int j = 0; j < copyObjects.size(); j++) {
                if (copyObjects.get(j).getObjectPath().equals(file.listFiles()[i].getPath()))
                    contains = true;
            }
            if (contains == false) {
                if (file.listFiles()[i].isFile()) {
                    copyObjects.add(new FileCopyObject(file.listFiles()[i], temp, mode, timeToCopy));
                } else {
                    copyObjects.add(new DirectoryCopyObject(file.listFiles()[i], temp, mode, timeToCopy));
                }
            }
        }
        for (int i = 0; i < copyObjects.size(); i++) {
            contains = false;
            for (int j = 0; j < file.listFiles().length; j++) {
                if (copyObjects.get(i).getObjectPath().equals(file.listFiles()[j].getPath()))
                    contains = true;
            }
            if (contains == false)
                copyObjects.remove(i);
        }
    }

    public boolean copy() {
        try {
            refreshCopyObjects();
            timesOfCopies.add(new Long(file.lastModified()));
            File temp = new File(copyingFileSource + "\\" + file.getName());
            Files.copy(file.toPath(), temp.toPath());
            for (int i = 0; i < copyObjects.size(); i++)
                copyObjects.get(i).copy();
        }
        catch (IOException e){return  false;}
        return true;
    }

   /* public void fullCopy(File from, File to) throws IOException {
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
    }*/

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
