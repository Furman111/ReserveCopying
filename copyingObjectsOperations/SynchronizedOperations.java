package copyingObjectsOperations;

import copyingFiles.CopyObject;
import copyingObjectsOperations.Journal;
import dataManager.DataManager;

import javax.swing.*;

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
        try {
            DataManager.saveJournal();
        } catch (Exception except) {
            JOptionPane.showConfirmDialog(null, except.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        System.exit(0);
    }

}
