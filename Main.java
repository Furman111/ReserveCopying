/**
 * Created by Furman on 21.01.2017.
 */

import copyingFiles.CopyObject;
import copyingFiles.DirectoryCopyObject;
import gui.InfoWindow;
import gui.MainWindow;
import modesOfCopying.Mode;

import java.io.File;

public class Main {
    public static void main(String[] args) {
/*        CopyObject copyObject = new DirectoryCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\dsasew"), new File("dsdsdsddxasx"), Mode.DIF, 14400);
        InfoWindow infoWindow = new InfoWindow(copyObject);
        infoWindow.setVisible(true);*/
        MainWindow w = new MainWindow();
        w.setVisible(true);
        /*try {
*//*          Journal journal = new Journal();
            journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\ewew.txt"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,1000));
            journal.add(new DirectoryCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\dsasew"),new File("C:\\Users\\Furman\\Desktop\\test\\to"),Mode.INC,1000));
            DataManager.saveJournal(journal);*//*
            Journal serializedJournal = DataManager.getJournal();
            for (CopyObject j : serializedJournal)
                System.out.println(j.getName());
            Thread copier = new Thread(new CopierThread(DataManager.getJournal()));
            DataManager.getJournal().get(0).repairCopies();
            copier.start();
            Thread.sleep(10000);
            copier.interrupt();
            System.out.println(DataManager.getJournal().get(0).upgrade(DataManager.getJournal().get(0).getListOfCopiesTimes().get(0)));
            DataManager.saveJournal();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
