/**
 * Created by Furman on 21.01.2017.
 */

import copyingFiles.CopyObject;
import copyingFiles.DirectoryCopyObject;
import copyingFiles.Journal;
import dataManager.DataManager;
import gui.InfoWindow;
import gui.MainWindow;
import modesOfCopying.Mode;
import tracking.CopierThread;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){}

        Journal journal = null;
        try {
            journal = DataManager.getJournal();
        }
        catch (Exception e){
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        MainWindow w = new MainWindow(journal);
        Thread tracking = new Thread(new CopierThread(journal,w));
        tracking.start();
        w.setVisible(true);
    }
}
