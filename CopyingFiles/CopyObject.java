package CopyingFiles;

import Modes.Mode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Furman on 21.01.2017.
 */
public interface CopyObject {


    boolean copy();

    boolean delete();

    boolean upgrade(long time);

    String getObjectPath();

    List<Long> getListOfCopiesTimes();

}
