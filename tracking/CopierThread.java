package tracking;

import copyingFiles.CopyObject;
import copyingFiles.Journal;

import java.util.ArrayList;
import java.util.List;

import gui.MainWindow;
import observing.Observable;
import observing.Observer;

import static java.lang.Thread.yield;

/**
 * Created by Furman on 26.01.2017.
 */
public class CopierThread implements Runnable, Observable {
    private Journal journal;
    private List<CopyObject> copyList;
    private int hashCode;
    private List<Observer> observers;
    private MainWindow mainWindow;

    public CopierThread(Journal journal, MainWindow mainWindow) {
        this.journal = journal;
        this.observers = new ArrayList<>();
        this.mainWindow = mainWindow;
        registerObserver(mainWindow);
        hashCode = 0;
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
                mainWindow.setCopyingObject(copyList.get(0));
                copyList.get(0).copy(currentTime);
                notifyObservers();
                mainWindow.cancelCopyingNow();
            }
        }
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers)
            o.dataChanged();
    }

}
