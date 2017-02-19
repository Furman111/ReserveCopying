package tracking;

import copyingFiles.CopyObject;

import java.util.Comparator;


public class CopyObjectComparator implements Comparator<CopyObject> {
    public int compare(CopyObject obj1, CopyObject obj2) {
        if ((obj1.getTimeOfLastAttemption() + obj1.getTimeToCopy()) > (obj2.getTimeOfLastAttemption() + obj2.getTimeToCopy()))
            return 1;
        else if ((obj1.getTimeOfLastAttemption() + obj1.getTimeToCopy()) < (obj2.getTimeOfLastAttemption() + obj2.getTimeToCopy()))
            return -1;
        else
            return 0;
    }
}
