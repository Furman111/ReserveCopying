package copyingFiles;

import copyingFiles.copies.CopyOfDirectory;
import modesOfCopying.*;
import fileSystemProcess.FilesManager;
import java.util.Random;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Furman on 21.01.2017.
 */

public class DirectoryCopyObject implements CopyObject, Serializable {

    private Mode mode;
    private long timeToCopy;
    private File file;
    private File copyingFileSource;
    private ArrayList<CopyOfDirectory> copies;
    private ArrayList<CopyObject> copyObjects;
    private long timeOfLastAttemption;
    private String copyName;

    public void repairCopies() {
        if (!(new File(copyingFileSource.getPath() + "\\" + copyName).exists())) {
            copies.clear();
            timeOfLastAttemption = 0;
        } else
            for (int i = 0; i < copies.size(); i++) {
                if (!copies.get(i).check()) {
                    if (i == copies.size() - 1)
                        if (copies.size() > 1)
                            timeOfLastAttemption = copies.get(i - 1).getTime();
                        else
                            timeOfLastAttemption = 0;
                    copies.get(i).repair();
                    copies.remove(i);
                }
            }
            if (copies.isEmpty())
                FilesManager.deleteFile(new File(copyingFileSource+"\\"+copyName));
    }

    public boolean checkCopies() {
        boolean res = true;
        if (!new File(copyingFileSource.getPath() + "\\" + copyName).exists())
            return false;
        else
            for (int i = 0; i < copies.size(); i++) {
                if (!copies.get(i).check())
                    res = false;
            }
        return res;
    }

    public boolean checkCopyInTime(long time) {
        boolean res = false;
        if (!(new File(copyingFileSource.getPath() + "\\" + copyName).exists()))
            return false;
        else
            for (int i = 0; i < copies.size(); i++)
                if (copies.get(i).getTime() == time)
                    res = copies.get(i).check();
        return res;
    }

    public void deleteCopyInTime(long time) {
        for (CopyOfDirectory c : copies)
            if (c.getTime() == time)
                c.repair();
    }


    public DirectoryCopyObject(File file, File copyingFileSource, Mode mode, long timeToCopy) {
        if (file.exists()) {
            this.mode = mode;
            this.timeToCopy = timeToCopy;
            this.copyingFileSource = new File(copyingFileSource.getPath());
            this.file = file;
            copies = new ArrayList<>();
            copies.clear();
            copyObjects = new ArrayList<>();
            copyObjects.clear();
            timeOfLastAttemption = 0;
            Random random = new Random();
            copyName = new String(random.nextInt()+"_"+random.nextInt());
        } else
            throw new Error("Файл не существует");
    }


    public boolean copy(long t) {
        timeOfLastAttemption = t;
        if (file.exists()) {
            File temp = new File(copyingFileSource.getPath() + "\\" + copyName);
            for (int i = 0; i < file.list().length; i++) {
                boolean contains = false;
                for (CopyObject o : copyObjects) {
                    if (o.getPath().equals(file.listFiles()[i].getPath()))
                        contains = true;
                }
                if (!contains) {
                    if (file.listFiles()[i].isFile()) {
                        copyObjects.add(new FileCopyObject(file.listFiles()[i], temp, mode, timeToCopy));
                    } else {
                        copyObjects.add(new DirectoryCopyObject(file.listFiles()[i], temp, mode, timeToCopy));
                    }
                }
            }
            for (int j = 0; j < copyObjects.size(); j++) {
                boolean contains = false;
                for (int i = 0; i < file.list().length; i++) {
                    if (file.listFiles()[i].getPath().equals(copyObjects.get(j).getPath()))
                        contains = true;
                }
                if (!contains) {
                    copyObjects.remove(j);
                }
            }
            copies.add(new CopyOfDirectory(t, copyObjects, false));
            if (!temp.exists()) FilesManager.copyFileFromTo(file, temp);
            return (copies.get(copies.size() - 1).copy(t));
        } else if (!copies.get(copies.size() - 1).isDeleted()) {
            copies.add(new CopyOfDirectory(t, copyObjects, true));
        } else
            copies.set(copies.size() - 1, new CopyOfDirectory(t, copyObjects, true));
        return true;
    }

    public boolean delete() {
        for (CopyOfDirectory cop : copies) {
            cop.delete();
        }
        File temp = new File(copyingFileSource.getPath() + "\\" + copyName);
        return (FilesManager.deleteFile(temp));
    }

    public boolean upgrade(long time) throws NoSuchElementException {
        if (!this.checkCopyInTime(time))
            throw new NoSuchElementException();

        FilesManager.deleteFile(file);
        File temp = new File(copyingFileSource.getPath() + "\\" + copyName);
        if (!file.exists()) FilesManager.copyFileFromTo(temp, file);
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getTime() == time)
                return copies.get(i).upgrade();
        }
        return false;
    }

    public String getPath() {
        return file.getPath();
    }

    public List<Long> getListOfCopiesTimes() {
        ArrayList<Long> res = new ArrayList<>();
        for (int i = 0; i < copies.size(); i++)
            res.add(copies.get(i).getTime());
        return res;
    }

    public boolean isDeleted() {
        return copies.get(copies.size() - 1).isDeleted();
    }

    public boolean isFile() {
        return false;
    }

    public boolean isDirectory() {
        return true;
    }

    public String getName() {
        return file.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectoryCopyObject that = (DirectoryCopyObject) o;

        if (timeToCopy != that.timeToCopy) return false;
        if (timeOfLastAttemption != that.timeOfLastAttemption) return false;
        if (mode != that.mode) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (copyingFileSource != null ? !copyingFileSource.equals(that.copyingFileSource) : that.copyingFileSource != null)
            return false;
        if (copies != null ? !copies.equals(that.copies) : that.copies != null) return false;
        return copyObjects != null ? copyObjects.equals(that.copyObjects) : that.copyObjects == null;
    }

    @Override
    public int hashCode() {
        return (int) (timeOfLastAttemption ^ (timeOfLastAttemption >>> 32));
    }

    public long getLastCopyTime() {
        if (!copies.isEmpty())
            return copies.get(copies.size() - 1).getTime();
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
