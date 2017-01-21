package Files;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Furman on 21.01.2017.
 */
public class FileCopyObject extends copyObject{

    private Calendar time;
    private File file;
    private String copyingPath;

    public FileCopyObject(File file){
        this.file = file;
    }

    public FileCopyObject(String path){
        file = new File(path);
    }

    @Override
    public Calendar getTime() {
        return time;
    }

    @Override
    public void setTime(Calendar time) {
        this.time = time;
    }

    public boolean isFile(){
        return true;
    }

    public boolean isDirectory(){
        return false;
    }

    public String getObjectName(){
        return file.getName();
    }

    public String getObjectPath(){
        return file.getPath();
    }

    public void setCopyingPath(String path){
        copyingPath = path;
    }

    public String getCopyingPath(){
        return copyingPath;
    }

}
