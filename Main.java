import copyingObjectsOperations.Journal;
import dataManager.DataManager;
import gui.MainWindow;
import tracking.CopierThread;
import copyingObjectsOperations.SynchronizedOperations;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        Journal journal = null;
        try {
            journal = DataManager.getJournal();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        journal.repairCopies();

        SynchronizedOperations operator = new SynchronizedOperations(journal);

        MainWindow mainWindow = new MainWindow(journal, operator);

        Thread tracking = new Thread(new CopierThread(journal, mainWindow, operator));
        tracking.start();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainWindow.setVisible(true);
            }
        });

    }
}
