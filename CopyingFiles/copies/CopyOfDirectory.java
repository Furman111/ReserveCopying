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

    public CopyOfDirectory(long time,ArrayList<CopyObject> files){
        this.files =(ArrayList<CopyObject>) files.clone();
        this.time = time;
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

    public boolean copy(){
        for(CopyObject j:files){
            if(!j.copy())
                return false;
        }
        return true;
    }
    
}





