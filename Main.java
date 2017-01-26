/**
 * Created by Furman on 21.01.2017.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


import copyingFiles.*;

import dataManager.DataManager;
import modesOfCopying.Mode;
import tracking.CopierThread;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {
        try {
/*            Journal journal = new Journal();
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\1.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,1000));
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\2.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,1000));
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\3.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,1000));
            DataManager.saveJournal(journal);*/
            Journal serializedJournal = DataManager.getJournal();
            for (CopyObject j : serializedJournal)
                System.out.println(j.getName());
            Thread copier = new Thread(new CopierThread(DataManager.getJournal()));
            copier.start();
            Thread.sleep(10000);
            copier.interrupt();
            DataManager.saveJournal();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
