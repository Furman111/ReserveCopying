package tracking;

import copyingFiles.CopyObject;
import copyingFiles.Journal;
import java.util.ArrayList;
import java.util.List;
import observing.Observable;
import observing.Observer;

/**
 * Created by Furman on 26.01.2017.
 */
public class CopierThread implements Runnable,Observable{
    private Journal journal;
    private List<CopyObject> copyList;
    private int hashCode;
    private List<Observer> observers;

    public CopierThread(Journal journal,Observer o){
        this.journal = journal;
        this.observers = new ArrayList<>();
        this.observers.add(o);
        hashCode = 0;
        copyList = new ArrayList<>(journal.getAllCopyFiles());
        this.sort(journal.hashCode());
    }

    private void sort(int currentHashCode) {
        if (currentHashCode!=hashCode) {
            copyList = new ArrayList<>(journal.getAllCopyFiles());
            copyList.sort(new CopyObjectComparator());
            hashCode = currentHashCode;
        }
    }

    public void run(){
        while(!Thread.interrupted()){
            sort(journal.hashCode());
            long currentTime = System.currentTimeMillis();
            if (!copyList.isEmpty() && (copyList.get(0).getTimeOfLastAttemption()+copyList.get(0).getTimeToCopy())<=currentTime) {
                copyList.get(0).copy(currentTime);
                notifyObservers();
            }
        }
    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for(Observer o: observers)
            o.dataChanged();
    }

}
