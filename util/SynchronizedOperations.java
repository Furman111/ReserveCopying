package util;

import copyingFiles.CopyObject;
import copyingFiles.Journal;
/**
 * Created by Furman on 18.02.2017.
 */
public class SynchronizedOperations {

    private Journal journal;

    public SynchronizedOperations(Journal journal){
        this.journal=journal;
    }

    public synchronized void copy(CopyObject object, long timeOfCopy){
        object.copy(timeOfCopy);
    }

    public synchronized void upgrade(CopyObject object, long timeOfCopy){
        object.upgrade(timeOfCopy);
    }

    public synchronized void delete(CopyObject object){
        journal.delete(object);
    }

    public synchronized void addCopyObject(CopyObject object){
            journal.add(object);
    }

    public synchronized void exit(){
        System.exit(0);
    }

}
