package copyingFiles.copies;

import copyingFiles.CopyObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CopyOfDirectory implements Serializable {
    private long time;
    private ArrayList<CopyObject> files;
    private boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyOfDirectory that = (CopyOfDirectory) o;

        if (time != that.time) return false;
        if (deleted != that.deleted) return false;
        return files != null ? files.equals(that.files) : that.files == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    public CopyOfDirectory(long time, ArrayList<CopyObject> files, boolean isDeleted) {
        this.files = new ArrayList<>();
        for (int i = 0; i < files.size(); i++)
            this.files.add(i, files.get(i));
        this.time = time;
        this.deleted = isDeleted;
    }

    public boolean delete() {
        for (CopyObject j : files)
            if (!j.delete())
                return false;
        return true;
    }

    public long getTime() {
        return time;
    }

    public boolean upgrade() {
        for (CopyObject j : files)
            if (!j.upgrade(time))
                return false;
        return true;
    }

    public boolean copy(long timeOfCopy) {
        for (CopyObject j : files) {
            if (!j.copy(timeOfCopy))
                return false;
        }
        return true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean check() {
        boolean res = true;
        for (int i = 0; i < files.size(); i++)
            if (!files.get(i).checkCopyInTime(time))
                res = false;
        return res;
    }

    public void repair() {
        for (int i = 0; i < files.size(); i++)
            files.get(i).deleteCopyInTime(time);
    }

}





