package copyingFiles;

import copyingFiles.copies.CopyOfDirectory;
import modesOfCopying.*;
import fileSystemProcess.FilesManager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void repairCopies() {
        if (!(new File(copyingFileSource.getPath() + "\\" + file.getName()).exists())) {
            copies.clear();
        } else
            for (int i = 0; i < copies.size(); i++)
                if (!copies.get(i).check()) {
                    copies.get(i).repair();
                    copies.remove(i);
                }
    }

    public boolean checkCopies() {
        boolean res = true;
        if (!(new File(copyingFileSource.getPath() + "\\" + file.getName()).exists()))
            return false;
        else
            for (int i = 0; i < copies.size(); i++)
                res = copies.get(i).check();
        return res;
    }

    public boolean checkCopyInTime(long time) {
        boolean res = false;
        if (!(new File(copyingFileSource.getPath() + "\\" + file.getName()).exists()))
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
        } else
            throw new Error("Файл не существует");
    }


    public boolean copy(long t) {
        timeOfLastAttemption = t;
        if (file.exists()) {
            File temp = new File(copyingFileSource.getPath() + "\\" + file.getName());
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
        File temp = new File(copyingFileSource.getPath() + "\\" + file.getName());
        return (FilesManager.deleteFile(temp));
    }

    public boolean upgrade(long time) {
        FilesManager.deleteFile(file);
        File temp = new File(copyingFileSource.getPath() + "\\" + file.getName());
        if (!file.exists()) FilesManager.copyFileFromTo(temp, file);
        int i = 0;
        while ((i < copies.size()) && (time != copies.get(i).getTime())) i++;
        if (i < copies.size()) return copies.get(i).upgrade();
        else {
            System.out.println(copies.size());
            System.out.println("i = " + i);
            return false;
        }
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
        int result = mode != null ? mode.hashCode() : 0;
        result = 31 * result + (int) (timeToCopy ^ (timeToCopy >>> 32));
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (copyingFileSource != null ? copyingFileSource.hashCode() : 0);
        result = 31 * result + (copies != null ? copies.hashCode() : 0);
        result = 31 * result + (copyObjects != null ? copyObjects.hashCode() : 0);
        result = 31 * result + (int) (timeOfLastAttemption ^ (timeOfLastAttemption >>> 32));
        return result;
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

    public Mode getMode(){return mode;}

    public String getPathToCopies(){
        return copyingFileSource.getAbsolutePath();
    }
}
