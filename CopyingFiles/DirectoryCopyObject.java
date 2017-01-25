package CopyingFiles;

import CopyingFiles.copies.CopyOfDirectory;
import Modes.*;
import filesystemprocess.FilesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Furman on 21.01.2017.
 */

public class DirectoryCopyObject implements CopyObject {

    private Mode mode;
    private long timeToCopy;
    private File file;
    private File copyingFileSource;
    private ArrayList<CopyOfDirectory> copies;
    private ArrayList<CopyObject> copyObjects;


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
        } else
            throw new Error("Файл не существует");
    }


    public boolean copy(long t) {
        if (file.exists()) {
            copyObjects.clear();
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
        return copies.get(copies.size()-1).isDeleted();
    }

    public boolean isFile(){
        return false;
    }

    public boolean isDirectory(){
        return true;
    }

    public String getName(){ return file.getName(); }

}
