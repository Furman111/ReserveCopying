/**
 * Created by Furman on 21.01.2017.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import  Modes.Mode;
import CopyingFiles.*;

import filesystemprocess.*;

import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {
        try {
/*            Journal journal= new Journal();
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\1.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,5000));
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\2.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,5000));
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\3.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,5000));
            DataManager.saveJournal(journal);*/
            Journal serializedJournal = DataManager.getJournal();
            for(CopyObject j:serializedJournal)
                System.out.println(j.getName());
            serializedJournal.delete(0);
            DataManager.saveJournal();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
