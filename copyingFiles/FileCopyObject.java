package copyingFiles;

import copyingFiles.copies.CopyOfFile;
import modesOfCopying.Mode;
import fileSystemProcess.FilesManager;

import java.io.File;
import java.io.Serializable;
import java.util.*;


public class FileCopyObject implements CopyObject, Serializable {

    private File file;
    private File copyingFileSource;
    private Mode mode;
    private ArrayList<CopyOfFile> copies;
    private long timeToCopy;

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
        return (int) (timeOfLastAttemption ^ (timeOfLastAttemption >>> 32));
    }

    private long timeOfLastAttemption;

    public void repairCopies() {
        for (int i = 0; i < copies.size(); i++)
            if (!FilesManager.copyExists(new File(copyingFileSource + "\\" + copies.get(i).getNameOfCopyFile()))) {
                if (i == copies.size() - 1)
                    if (copies.size() > 1)
                        timeOfLastAttemption = copies.get(i - 1).getTimeOfCopy();
                    else
                        timeOfLastAttemption = 0;
                copies.remove(i);
            }
    }

    public boolean checkCopyInTime(long time) {
        boolean res = false;
        for (int i = 0; i < copies.size(); i++)
            if (copies.get(i).getTimeOfCopy() == time)
                if (FilesManager.copyExists(new File(copyingFileSource + "\\" + copies.get(i).getNameOfCopyFile())))
                    res = true;
        return res;
    }

    public void deleteCopyInTime(long time) {
        for (int i = 0; i < copies.size(); i++)
            if (copies.get(i).getTimeOfCopy() == time) {
                if (FilesManager.copyExists(new File(copyingFileSource + "\\" + copies.get(i).getNameOfCopyFile())))
                    FilesManager.deleteZipFile(new File(copyingFileSource + "\\" + copies.get(i).getNameOfCopyFile()));
                copies.remove(i);
            }
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
            case DIF:
                return (decCopy(timeOfCopy));
        }
        return false;
    }

    public boolean decCopy(long timeOfCopy) {
        boolean res = true;
        if (file.exists()) {
            if (copies.isEmpty()) {
                CopyOfFile tempCopy = new CopyOfFile(timeOfCopy, file.lastModified(), false);
                copies.add(tempCopy);
                File temp = new File(copyingFileSource + "\\" + tempCopy.getNameOfCopyFile());
                res = FilesManager.fileToZipCopy(file, temp);
            } else {
                if (file.lastModified() > copies.get(copies.size() - 1).getTimeOfCopy()) {
                    CopyOfFile tempCopy = new CopyOfFile(timeOfCopy, file.lastModified(), false);
                    File temp;
                    if (copies.size() == 2) {
                        tempCopy.setNameOfCopyFile(copies.get(copies.size() - 1).getNameOfCopyFile());
                        temp = new File(copyingFileSource + "\\" + copies.get(copies.size() - 1).getNameOfCopyFile());
                    } else
                        temp = new File(copyingFileSource + "\\" + tempCopy.getNameOfCopyFile());
                    res = FilesManager.fileToZipCopy(file, temp);
                    if (copies.size() == 2)
                        copies.set(1, tempCopy);
                    else
                        copies.add(tempCopy);
                }
            }
        } else if (copies.size() == 3) {
            copies.set(copies.size() - 1, new CopyOfFile(timeOfCopy, timeOfCopy, true));
        } else {
            copies.add(new CopyOfFile(timeOfCopy, timeOfCopy, true));
        }
        return res;
    }

    public boolean incCopy(long timeOfCopy) {
        boolean res = true;
        if (file.exists()) {
            if (copies.isEmpty()) {
                CopyOfFile tempCopy = new CopyOfFile(timeOfCopy, file.lastModified(), false);
                File temp = new File(copyingFileSource + "\\" + tempCopy.getNameOfCopyFile());
                res = FilesManager.fileToZipCopy(file, temp);
                copies.add(tempCopy);
            } else {
                if (file.lastModified() > copies.get(copies.size() - 1).getTimeOfCopy()) {
                    CopyOfFile tempCopy = new CopyOfFile(timeOfCopy, file.lastModified(), false);
                    File temp = new File(copyingFileSource + "\\" + tempCopy.getNameOfCopyFile());
                    res = FilesManager.fileToZipCopy(file, temp);
                    copies.add(tempCopy);
                }
            }
        } else if (copies.get(copies.size() - 1).isDeleted())
            copies.set(copies.size() - 1, new CopyOfFile(timeOfCopy, timeOfCopy, true));
        else
            copies.add(new CopyOfFile(timeOfCopy, timeOfCopy, true));
        return res;
    }

    public boolean delete() {
        boolean res = true;
        for (int i = 0; i < copies.size(); i++) {
            if (!copies.get(i).isDeleted()) {
                File temp = new File(copyingFileSource.getPath() + "\\" + copies.get(i).getNameOfCopyFile());
                res = FilesManager.deleteZipFile(temp);
            }
        }
        copies.clear();
        return res;
    }

    public boolean upgrade(long time) throws NoSuchElementException {
        if (!this.checkCopyInTime(time))
            throw new NoSuchElementException();
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getTimeOfCopy() == time) {
                FilesManager.deleteFile(file);
                if (!copies.get(i).isDeleted()) {
                    File temp = new File(copyingFileSource.getPath() + "\\" + copies.get(i).getNameOfCopyFile());
                    return FilesManager.zipToFileCopy(temp, file);
                }
                return true;
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

    public Mode getMode() {
        return mode;
    }

    public String getPathToCopies() {
        return copyingFileSource.getAbsolutePath();
    }
}
