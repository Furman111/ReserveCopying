package CopyingFiles;

import java.util.Calendar;
import Modes.Mode;
import java.io.File;

/**
 * Created by Furman on 21.01.2017.
 */
public abstract class copyObject {

    public void setTime(Calendar time){
        throw new UnsupportedOperationException();
    }
    public Calendar getTime(){
        throw new UnsupportedOperationException();
    }

    public void setMode(Mode mode){
        throw new UnsupportedOperationException();
    }
    public Mode getMode(){
        throw new UnsupportedOperationException();
    }

    public boolean copy(){
        throw new UnsupportedOperationException();
    }
    public boolean delete(){
        throw new UnsupportedOperationException();
    }
    public boolean upgrade(int i){
        throw new UnsupportedOperationException();
    }

    public boolean isDirectory(){throw new UnsupportedOperationException();}
    public boolean isFile(){
        throw new UnsupportedOperationException();
    }

    public String getObjectName(){throw new UnsupportedOperationException();}
    public String getObjectPath(){throw new UnsupportedOperationException();}

    public File getCopyingFileSource(){throw new UnsupportedOperationException();}
    public void setCopyingFileSource(File copyingFileSource){throw new UnsupportedOperationException();}
}
