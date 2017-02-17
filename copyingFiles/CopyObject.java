package copyingFiles;

import modesOfCopying.Mode;
import java.util.List;

/**
 * Created by Furman on 21.01.2017.
 */
public interface CopyObject {

    boolean copy(long timeOfCopy);

    boolean delete();

    boolean upgrade(long time);

    String getPath();

    List<Long> getListOfCopiesTimes();

    boolean isDeleted();

    boolean isFile();

    boolean isDirectory();

    String getName();

    long getLastCopyTime();

    long getTimeToCopy();

    long getTimeOfLastAttemption();

    boolean checkCopies();

    void repairCopies();

    boolean checkCopyInTime(long time);

    void deleteCopyInTime(long time);

    Mode getMode();

    String getPathToCopies();
}
