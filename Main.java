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
        catch (Exception e){};
        MainWindow w = new MainWindow(journal);
        w.setVisible(true);
    }
}
