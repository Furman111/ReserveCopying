package CopyingFiles;

import CopyingFiles.copies.CopyOfFile;
import Modes.Mode;
import filesystemprocess.FilesManager;

import java.io.File;
import java.util.*;


/**
 * Created by Furman on 21.01.2017.
 */
public class FileCopyObject implements CopyObject {

    private long timeToCopy;
    private File file;
    private File copyingFileSource;
    private Mode mode;
    private ArrayList<CopyOfFile> copies;

    public FileCopyObject(File file, File copyingFileSource, Mode mode, long time) {
        if (file.exists()) {
            this.file = file;
            this.copyingFileSource = new File(copyingFileSource.getPath());
            this.timeToCopy = time;
            this.mode = mode;
            copies = new ArrayList<>();
            copies.clear();
        } else
            throw new Error("Файл не существует");
    }

    public boolean copy(long timeOfCopy) {
        switch (mode) {
            case INC:
                return (incCopy(timeOfCopy));
            case DEF:
                return (decCopy(timeOfCopy));
        }
        return false;
    }

    public boolean decCopy(long timeOfCopy) {
        boolean res = true;
        if (file.exists()) {
            if (copies.isEmpty()) {
                File temp = new File(copyingFileSource + "\\" + "v" + 1 + "_" + file.getName());
                res = FilesManager.fileToZipCopy(file, temp);
                copies.add(new CopyOfFile(timeOfCopy, file.lastModified(), false));
            } else {
                if (file.lastModified() > copies.get(copies.size() - 1).getTimeOfCopy()) {
                    File temp = new File(copyingFileSource + "\\" + "v" + 2 + "_" + file.getName());
                    res = FilesManager.fileToZipCopy(file, temp);
                    if (copies.size() == 1)
                        copies.add(new CopyOfFile(timeOfCopy, file.lastModified(), false));
                    else
                        copies.set(1, new CopyOfFile(timeOfCopy, file.lastModified(), false));
                }
            }
        } else if (copies.get(copies.size() - 1).isDeleted())
            copies.set(copies.size() - 1, new CopyOfFile(timeOfCopy, timeOfCopy, true));
        else
            copies.add(new CopyOfFile(timeOfCopy, timeOfCopy, true));
        return res;
    }

    public boolean incCopy(long timeOfCopy) {
        boolean res = true;
        if (file.exists()) {
            if (copies.isEmpty()) {
                File temp = new File(copyingFileSource + "\\" + "v" + (copies.size() + 1) + "_" + file.getName());
                res = FilesManager.fileToZipCopy(file, temp);
                copies.add(new CopyOfFile(timeOfCopy, file.lastModified(), false));
            } else {
                if (file.lastModified() > copies.get(copies.size() - 1).getTimeOfCopy()) {
                    File temp = new File(copyingFileSource + "\\" + "v" + (copies.size() + 1) + "_" + file.getName());
                    res = FilesManager.fileToZipCopy(file, temp);
                    copies.add(new CopyOfFile(timeOfCopy, file.lastModified(), false));
                }
            }
        } else if (copies.get(copies.size() - 1).isDeleted())
            copies.set(copies.size() - 1, new CopyOfFile(timeOfCopy, System.currentTimeMillis(), true));
        else
            copies.add(new CopyOfFile(timeOfCopy, System.currentTimeMillis(), true));
        return res;
    }

    public boolean delete() {
        boolean res = true;
        for (int i = 0; i < copies.size(); i++) {
            if (!copies.get(i).isDeleted()) {
                File temp = new File(copyingFileSource.getPath() + "\\" + "v" + (i + 1) + "_" + file.getName());
                res = FilesManager.deleteZipFile(temp);
            }
        }
        return res;
    }

    public boolean upgrade(long time) {
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getTimeOfCopy() == time)
                if (!copies.get(i).isDeleted()) {
                    File temp = new File(copyingFileSource.getPath() + "\\" + "v" + (i + 1) + "_" + file.getName());
                    return FilesManager.zipToFileCopy(temp, file);
                }
        }
        return false;
    }

    public String getPath() {
        return file.getPath();
    }

    public List<Long> getListOfCopiesTimes() {
        ArrayList<Long> res = new ArrayList<>();
        for (int i = 0; i < copies.size(); i++)
            res.add(copies.get(i).getTimeOfCopy());
        return res;
    }

    public FileCopyObject clone() {
        FileCopyObject res = new FileCopyObject(this.file, this.copyingFileSource, this.mode, this.timeToCopy);
        res.copies = new ArrayList<>();
        for (int i = 0; i < this.copies.size(); i++)
            res.copies.add(i, new CopyOfFile(this.copies.get(i).getTimeOfCopy(), this.copies.get(i).getTimeOfModification(), this.copies.get(i).isDeleted()));
        return res;
    }

    public boolean isDeleted(){
        return copies.get(copies.size()-1).isDeleted();
    }

    public boolean isFile(){
        return true;
    }

    public boolean isDirectory(){
        return false;
    }

    public String getName(){ return file.getName(); }
}
