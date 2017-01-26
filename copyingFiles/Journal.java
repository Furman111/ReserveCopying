package copyingFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Furman on 25.01.2017.
 */
public class Journal implements Iterable<CopyObject>,Serializable{
    private List<CopyObject> copyFiles;

    public Journal(){
        copyFiles = new ArrayList<>();
    }

    public void add(CopyObject copyObject){
        copyFiles.add(copyObject);
    }

    public CopyObject get(int i){
        return copyFiles.get(i);
    }

    public CopyObject getWithPath(String path){
        for(CopyObject f:copyFiles)
            if(f.getPath()==path)
                return f;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journal journal = (Journal) o;

        return copyFiles != null ? copyFiles.equals(journal.copyFiles) : journal.copyFiles == null;
    }

    @Override
    public int hashCode() {
        return copyFiles != null ? copyFiles.hashCode() : 0;
    }

    public List<CopyObject> getAllCopyFiles(){
        return copyFiles;
    }

    public Iterator iterator(){return copyFiles.iterator();}

    public void delete(int i){
        copyFiles.get(i).delete();
        copyFiles.remove(i);
    }

    public int size(){return copyFiles.size();}

}
