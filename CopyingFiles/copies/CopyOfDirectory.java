package CopyingFiles.copies;

import CopyingFiles.CopyObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.io.File;


/**
 * Created by Furman on 24.01.2017.
 */
public class CopyOfDirectory {
    private long time;
    private ArrayList<CopyObject> files;
    private boolean deleted;

    public CopyOfDirectory(long time,ArrayList<CopyObject> files,boolean isDeleted){
        this.files = new ArrayList<>();
        for(int i=0;i<files.size();i++)
            this.files.add(i,files.get(i));
        this.time = time;
        this.deleted=isDeleted;
    }

    public boolean delete(){
          for(CopyObject j:files)
              if(!j.delete())
                  return false;
          return true;
    }

    public long getTime(){
        return time;
    }

    public boolean upgrade(){
          for(CopyObject j:files)
              if (!j.upgrade(time))
                  return false;
          return true;
    }

    public boolean copy(long timeOfCopy){
        for(CopyObject j:files){
            if(!j.copy(timeOfCopy))
                return false;
        }
        return true;
    }

    public boolean isDeleted(){
        return deleted;
    }
    
}





