/**
 * Created by Furman on 21.01.2017.
 */


import copyingFiles.Journal;
import dataManager.DataManager;
import gui.MainWindow;
import tracking.CopierThread;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        Journal journal = null;
        try {
            journal = DataManager.getJournal();
        }
        catch (Exception e){
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        journal.repairCopies();
        MainWindow mainWindow = new MainWindow(journal);
        Thread tracking = new Thread(new CopierThread(journal,mainWindow));
        tracking.start();
        mainWindow.setVisible(true);
    }
}
