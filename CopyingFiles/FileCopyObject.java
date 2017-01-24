package CopyingFiles;

import CopyingFiles.copies.CopyOfFile;
import Modes.Mode;
import filesystemprocess.FSProcessor;

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
            this.copyingFileSource = new File(copyingFileSource.getPath() + "\\" + mode.toString() + "\\");
            if (!this.copyingFileSource.exists())
                FSProcessor.createDirectory(this.copyingFileSource);
            this.timeToCopy = time;
            this.mode = mode;
            copies = new ArrayList<>();
            copies.clear();
        } else
            throw new Error("Файл не существует");
    }

    public boolean copy() {
        switch (mode) {
            case INC:
                return (incCopy());
            case DEC:
                return (decCopy());
        }
        return false;
    }

    public boolean decCopy() {
        boolean res = true;
        if (file.exists()) {
            File temp = new File(copyingFileSource + "\\" + "v" + 1 + "_" + file.getName());
            if (copies.isEmpty()) {
                res = FSProcessor.copyFromTo(file, temp);
                copies.add(new CopyOfFile(System.currentTimeMillis(), file.lastModified(), false));
            } else {
                if (file.lastModified() > copies.get(copies.size() - 1).getTimeOfCopy()) {
                    temp = new File(copyingFileSource + "\\" + 2 + "_" + file.getName());
                    res = FSProcessor.copyFromTo(file, temp);
                    if (copies.size() == 1)
                        copies.add(new CopyOfFile(System.currentTimeMillis(), file.lastModified(), false));
                    else
                        copies.set(1, new CopyOfFile(System.currentTimeMillis(), file.lastModified(), false));
                }
            }
        } else if (copies.get(copies.size() - 1).isDeleted())
            copies.set(copies.size() - 1, new CopyOfFile(System.currentTimeMillis(), System.currentTimeMillis(), true));
        else
            copies.add(new CopyOfFile(System.currentTimeMillis(), System.currentTimeMillis(), true));
        return res;
    }

    public boolean incCopy() {
        boolean res = true;
        if (file.exists()) {
            File temp = new File(copyingFileSource + "\\" + "v" + (copies.size()+1) + "_" + file.getName());
            if (copies.isEmpty()) {
                res = FSProcessor.copyFromTo(file, temp);
                copies.add(new CopyOfFile(System.currentTimeMillis(), file.lastModified(), false));
            } else {
                if (file.lastModified() > copies.get(copies.size() - 1).getTimeOfCopy()) {
                    temp = new File(copyingFileSource + "\\" + "v" + (copies.size() + 1) + "_" + file.getName());
                    res = FSProcessor.copyFromTo(file, temp);
                    copies.add(new CopyOfFile(System.currentTimeMillis(), file.lastModified(), false));
                }
            }
        } else if (copies.get(copies.size() - 1).isDeleted())
            copies.set(copies.size() - 1, new CopyOfFile(System.currentTimeMillis(), System.currentTimeMillis(), true));
        else
            copies.add(new CopyOfFile(System.currentTimeMillis(), System.currentTimeMillis(), true));
        return res;
    }

    public boolean delete() {
        boolean res = true;
        for (int i = 0; i < copies.size(); i++) {
            if (!copies.get(i).isDeleted()) {
                File temp = new File(copyingFileSource.getPath() + "\\" + "v" + (i+1) + "_" + file.getName());
                res = FSProcessor.deleteFile(temp);
            }
        }
        return res;
    }

    public boolean upgrade(long time) {
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getTimeOfCopy() == time)
                if (!copies.get(i).isDeleted()) {
                    File temp = new File(copyingFileSource.getPath() + "\\" + "v" + (i+1) + "_" + file.getName());
                    return FSProcessor.copyFromTo(temp,file);
                }
        }
        return false;
    }

    public String getObjectPath() {
        return file.getPath();
    }

    public List<Long> getListOfCopiesTimes(){
        ArrayList<Long> res = new ArrayList<>();
        for(int i=0;i<copies.size();i++)
            res.add(copies.get(i).getTimeOfCopy());
        return res;
    }

}
