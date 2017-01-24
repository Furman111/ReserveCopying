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
        File temp = new File(copyingFileSource.getPath() + "\\" + file.getName());
        for (int i = 0; i < file.list().length; i++) {
            boolean contains = false;
            for (CopyObject o : copyObjects){
                if (o.getObjectPath().equals(file.listFiles()[i].getPath()))
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
        copies.add(new CopyOfDirectory(t, copyObjects));
        if (!temp.exists()) FilesManager.copyFromTo(file, temp);
        return (copies.get(copies.size() - 1).copy(t));
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
        if (!file.exists()) FilesManager.copyFromTo(temp, file);
        int i = 0;
        while ((i < copies.size()) && (time != copies.get(i).getTime())) i++;
        if (i < copies.size()) return copies.get(i).upgrade();
        else {
            System.out.println(copies.size());
            System.out.println("i = "+i);
            return false;
        }
    }

    public String getObjectPath() {
        return file.getPath();
    }

    public List<Long> getListOfCopiesTimes(){
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0;i<copies.size();i++)
            res.add(copies.get(i).getTime());
        return res;
    }



}
