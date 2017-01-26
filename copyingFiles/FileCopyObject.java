package copyingFiles;

import copyingFiles.copies.CopyOfFile;
import modesOfCopying.Mode;
import fileSystemProcess.FilesManager;

import java.io.File;
import java.io.Serializable;
import java.util.*;


/**
 * Created by Furman on 21.01.2017.
 */
public class FileCopyObject implements CopyObject, Serializable {

    private File file;
    private File copyingFileSource;
    private Mode mode;
    private ArrayList<CopyOfFile> copies;
    private long timeToCopy;
    private long timeOfLastAttemption;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileCopyObject that = (FileCopyObject) o;

        if (timeToCopy != that.timeToCopy) return false;
        if (timeOfLastAttemption != that.timeOfLastAttemption) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (copyingFileSource != null ? !copyingFileSource.equals(that.copyingFileSource) : that.copyingFileSource != null)
            return false;
        if (mode != that.mode) return false;
        return copies != null ? copies.equals(that.copies) : that.copies == null;
    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (copyingFileSource != null ? copyingFileSource.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (copies != null ? copies.hashCode() : 0);
        result = 31 * result + (int) (timeToCopy ^ (timeToCopy >>> 32));
        result = 31 * result + (int) (timeOfLastAttemption ^ (timeOfLastAttemption >>> 32));
        return result;
    }

    public FileCopyObject(File file, File copyingFileSource, Mode mode, long time) {
        if (file.exists()) {
            this.file = file;
            this.copyingFileSource = new File(copyingFileSource.getPath());
            this.timeToCopy = time;
            this.mode = mode;
            copies = new ArrayList<>();
            copies.clear();
            timeOfLastAttemption = 0;
        } else
            throw new Error("Файл не существует");
    }

    public boolean copy(long timeOfCopy) {
        timeOfLastAttemption = timeOfCopy;
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
        copies.clear();
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

    public boolean isDeleted() {
        return copies.get(copies.size() - 1).isDeleted();
    }

    public boolean isFile() {
        return true;
    }

    public boolean isDirectory() {
        return false;
    }

    public String getName() {
        return file.getName();
    }

    public long getLastCopyTime() {
        if (!copies.isEmpty())
            return copies.get(copies.size() - 1).getTimeOfCopy();
        else
            return 0;
    }

    public long getTimeToCopy() {
        return timeToCopy;
    }

    public long getTimeOfLastAttemption() {
        return timeOfLastAttemption;
    }
}
