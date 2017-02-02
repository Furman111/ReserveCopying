package copyingFiles.copies;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by Furman on 24.01.2017.
 */
public class CopyOfFile implements Serializable {
    private long timeOfCopy;
    private long timeOfModification;
    private boolean deleted;
    private String nameOfCopyFile;

    public CopyOfFile(long timeOfCopy, long timeOfModification, boolean deleted) {
        this.timeOfCopy = timeOfCopy;
        this.timeOfModification = timeOfModification;
        this.deleted = deleted;
        Random random = new Random();
        nameOfCopyFile = new String(random.nextLong()+"_"+random.nextLong());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyOfFile that = (CopyOfFile) o;

        if (timeOfCopy != that.timeOfCopy) return false;
        if (timeOfModification != that.timeOfModification) return false;
        return deleted == that.deleted;
    }

    @Override
    public int hashCode() {
        int result = (int) (timeOfCopy ^ (timeOfCopy >>> 32));
        result = 31 * result + (int) (timeOfModification ^ (timeOfModification >>> 32));
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    public String getNameOfCopyFile(){
        return nameOfCopyFile;
    }

    public long getTimeOfCopy() {
        return timeOfCopy;
    }

    public long getTimeOfModification() {
        return timeOfModification;
    }

    public void setDeleted() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
