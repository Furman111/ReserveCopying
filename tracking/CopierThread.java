package tracking;

import copyingFiles.CopyObject;
import copyingObjectsOperations.Journal;

import java.util.ArrayList;
import java.util.List;

import observing.Observable;
import observing.Observer;
import copyingObjectsOperations.SynchronizedOperations;


public class CopierThread implements Runnable, Observable {
    private Journal journal;
    private List<CopyObject> copyList;
    private int hashCode;
    private List<Observer> observers;
    private SynchronizedOperations operator;

    public CopierThread(Journal journal, Observer o, SynchronizedOperations operator) {
        this.journal = journal;
        this.observers = new ArrayList<>();
        this.operator = operator;
        registerObserver(o);
        this.hashCode = 0;
        copyList = new ArrayList<>(journal.getAllCopyFiles());
        this.sort(journal.hashCode());
    }

    private void sort(int currentHashCode) {
        if (currentHashCode != hashCode) {
            copyList = new ArrayList<>(journal.getAllCopyFiles());
            copyList.sort(new CopyObjectComparator());
            hashCode = currentHashCode;
        }
    }

    public void run() {
        while (!Thread.interrupted()) {
            sort(journal.hashCode());
            long currentTime = System.currentTimeMillis();
            if ((!copyList.isEmpty()) && ((copyList.get(0).getTimeOfLastAttemption() + copyList.get(0).getTimeToCopy()) <= currentTime)) {
                operator.copy(copyList.get(0), currentTime);
                notifyObservers();
            }
        }
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (Observer o : observers)
            o.dataChanged();
    }

}
