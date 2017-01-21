package CopyingFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.*;
import java.util.Calendar;

/**
 * Created by Furman on 21.01.2017.
 */
public class FileCopyObject extends copyObject{

    private long time;
    private File file;
    private File copyingFileSource;
    int numberOfCopies;

    public FileCopyObject(File file,File copyingFileSource, long time){
        this.file = file;
        this.copyingFileSource = copyingFileSource;
        this.time = time;
        this.copy();
    }

    public boolean copy(){
        try {
            numberOfCopies++;
            File temp = new File(copyingFileSource+"\\"+"v"+numberOfCopies+"_"+file.getName());
            Files.copy(file.toPath(), temp.toPath(),REPLACE_EXISTING,COPY_ATTRIBUTES);
        }
        catch(IOException e){ return false;}
        return true;
    }

    public boolean delete(){
        try {
            for(int i=1;i<=numberOfCopies;i++) {
                File temp = new File(copyingFileSource.getPath()+"\\"+"v"+i+"_"+file.getName());
                temp.delete();
            }
        }
        catch(Exception e){return false;}
        return true;
    }

    public boolean upgrade(int i){
        try {
            if ((i>=1) && (i<=numberOfCopies)) {
                File temp = new File(copyingFileSource.getPath()+"\\"+"v"+i+"_"+file.getName());
                Files.copy(temp.toPath(), file.toPath(), REPLACE_EXISTING,COPY_ATTRIBUTES);
            }
            else
                throw new IOException();
        }
        catch(IOException e){ return false;}
        return true;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setTime(long time) {
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

    public File getCopyingFileSource(){
        return copyingFileSource;
    }

}
