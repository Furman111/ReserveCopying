package CopyingFiles;

import Modes.Mode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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



}
