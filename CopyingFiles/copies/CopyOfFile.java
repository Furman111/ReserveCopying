package CopyingFiles.copies;

/**
 * Created by Furman on 24.01.2017.
 */
public class CopyOfFile {
    private long timeOfCopy;
    private long timeOfModification;
    private boolean deleted;

    public CopyOfFile(long timeOfCopy,long timeOfModification,boolean deleted){
     this.timeOfCopy = timeOfCopy;
     this.timeOfModification = timeOfModification;
     this.deleted = deleted;
    }

    public long getTimeOfCopy(){
        return timeOfCopy;
    }

    public long getTimeOfModification(){
        return timeOfModification;
    }

    public void setDeleted(){deleted=true;}
    public boolean isDeleted(){return deleted;}
}
