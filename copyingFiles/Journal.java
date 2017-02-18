package copyingFiles;

import fileSystemProcess.FilesManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Furman on 25.01.2017.
 */
public class Journal implements Iterable<CopyObject>, Serializable {
    private List<CopyObject> copyFiles;

    public Journal() {
        copyFiles = new ArrayList<>();
    }

    public synchronized void add(CopyObject copyObject) {
        copyFiles.add(copyObject);
    }

    public CopyObject get(int i) {
        return copyFiles.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journal journal = (Journal) o;

        return copyFiles != null ? copyFiles.equals(journal.copyFiles) : journal.copyFiles == null;
    }

    @Override
    public synchronized int hashCode() {
        return copyFiles != null ? copyFiles.hashCode() : 0;
    }

    public List<CopyObject> getAllCopyFiles() {
        return copyFiles;
    }

    public Iterator iterator() {
        return copyFiles.iterator();
    }

    public int size() {
        return copyFiles.size();
    }

    public synchronized void repairCopies() {
       for(int i=0;i<copyFiles.size();i++)
            if (!FilesManager.fileWithPathExists(copyFiles.get(i).getPathToCopies()))
                delete(copyFiles.get(i));
        for (CopyObject copyObject : copyFiles) {
            copyObject.repairCopies();
        }
    }

    public CopyObject get(CopyObject object) {
        return copyFiles.get(copyFiles.indexOf(object));
    }

    public synchronized void delete(CopyObject object) {
        this.get(object).delete();
        copyFiles.remove(object);
    }

}
