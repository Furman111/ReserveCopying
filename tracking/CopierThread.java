package tracking;

import copyingFiles.CopyObject;
import copyingFiles.Journal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Furman on 26.01.2017.
 */
public class CopierThread implements Runnable{
    private Journal journal;
    private List<CopyObject> copyList;
    private int hashCode;

    public CopierThread(Journal journal){
        this.journal = journal;
        hashCode = 0;
        copyList = new ArrayList<>(journal.getAllCopyFiles());
        this.sort(journal.hashCode());
    }

    private void sort(int currentHashCode) {
        if (currentHashCode!=hashCode) {
            copyList = new ArrayList<>(journal.getAllCopyFiles());
            copyList.sort(new CopyObjectComparator());
            hashCode = currentHashCode;
            System.out.println();
            for(CopyObject j:copyList)
                System.out.println(j.getName());
            System.out.println();
        }
    }

    public void run(){
        while(!Thread.interrupted()){
            sort(journal.hashCode());
            long currentTime = System.currentTimeMillis();
            if (!copyList.isEmpty() && (copyList.get(0).getTimeOfLastAttemption()+copyList.get(0).getTimeToCopy())<=currentTime)
                copyList.get(0).copy(currentTime);
        }
    }

}
