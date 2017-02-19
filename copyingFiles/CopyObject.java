package copyingFiles;

import modesOfCopying.Mode;

import java.util.List;


public interface CopyObject {

    boolean copy(long timeOfCopy);

    boolean delete();

    boolean upgrade(long time);

    String getPath();

    List<Long> getListOfCopiesTimes();

    boolean isDeleted();

    boolean isFile();

    String getName();

    long getLastCopyTime();

    long getTimeToCopy();

    long getTimeOfLastAttemption();

    void repairCopies();

    boolean checkCopyInTime(long time);

    void deleteCopyInTime(long time);

    Mode getMode();

    String getPathToCopies();
}
