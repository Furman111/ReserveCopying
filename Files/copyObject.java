package Files;

import java.util.Calendar;
import Modes.Mode;

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
    public boolean upgrade(){
        throw new UnsupportedOperationException();
    }

    public boolean isDirectory(){
        throw new UnsupportedOperationException();
    }
    public boolean isFile(){
        throw new UnsupportedOperationException();
    }

    public String getObjectName(){throw new UnsupportedOperationException();}
    public String getObjectPath(){throw new UnsupportedOperationException();}

    public String getCopyingPath(){throw new UnsupportedOperationException();}
    public void setCopyingPath(String Path){throw new UnsupportedOperationException();}
}
