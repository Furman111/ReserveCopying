package gui;

import copyingFiles.Journal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import observing.*;

import dataManager.DataManager;
import util.*;

/**
 * Created by Furman on 02.02.2017.
 */
public class MainWindow extends JFrame implements Observer {

    private JMenuItem createNewCopyJMenu;
    private JMenuItem setCopyFolder;
    private JMenuItem closeOperation;
    private JTable table;
    private JButton upgrade;
    private JButton information;
    private JButton delete;
    private JScrollPane scrollPane;
    private Journal journal;
    private JFrame infoWindow;
    private JFrame upgradeWindow;
    private JFrame addWindow;
    private JFrame setDefaultDirectoryForCopiesWindow;


    public MainWindow(Journal journal) {
        super("Резревное копирование");
        setSize(800, 590);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultLookAndFeelDecorated(true);
        setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileJMenu = new JMenu("Файл");

        createNewCopyJMenu = new JMenuItem("Добавить файл для копирования");
        fileJMenu.add(createNewCopyJMenu);
        createNewCopyJMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addWindow == null) {
                    try {
                        addWindow = new AddWindow(getJournal(),MainWindow.this);
                    }
                    catch (Exception e1){
                        JOptionPane.showConfirmDialog(null,e1.getMessage(),"Ошибка!",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                    }
                    addWindow.setVisible(true);
                    addWindow.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                            MainWindow.super.setEnabled(false);
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            addWindow = null;
                            MainWindow.super.setEnabled(true);
                            MainWindow.super.toFront();
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {
                            MainWindow.super.setState(ICONIFIED);
                            addWindow.setState(ICONIFIED);
                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                            addWindow.setState(NORMAL);
                            addWindow.setFocusable(true);
                            addWindow.toFront();
                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }

                    });
                }
            }
        });

        setCopyFolder = new JMenuItem("Изменить директорию для сохранения копий");
        setCopyFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (setDefaultDirectoryForCopiesWindow == null) {
                    setDefaultDirectoryForCopiesWindow = new setDefaultDirectoryForCopiesWindow();
                    setDefaultDirectoryForCopiesWindow.setVisible(true);
                    setDefaultDirectoryForCopiesWindow.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                            MainWindow.super.setEnabled(false);
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            setDefaultDirectoryForCopiesWindow = null;
                            MainWindow.super.setEnabled(true);
                            MainWindow.super.toFront();
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {
                            MainWindow.super.setState(ICONIFIED);
                            setDefaultDirectoryForCopiesWindow.setState(ICONIFIED);
                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                            setDefaultDirectoryForCopiesWindow.setState(NORMAL);
                            setDefaultDirectoryForCopiesWindow.setFocusable(true);
                            setDefaultDirectoryForCopiesWindow.toFront();
                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {
                        }

                    });
                }
            }
        });
        fileJMenu.add(setCopyFolder);

        fileJMenu.addSeparator();

        closeOperation = new JMenuItem("Выйти из программы");
        fileJMenu.add(closeOperation);
        closeOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = { "Выйти", "Отмена" };
                int n = JOptionPane.showOptionDialog(null, "Вы уверены, что хотите выйти?",
                        "Выход из программы", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(n==0){
                    try {
                        DataManager.saveJournal();
                    }
                    catch (Exception except){ JOptionPane.showConfirmDialog(null,except.getMessage(),"Ошибка!",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE); }
                    setVisible(false);
                    System.exit(0);
                }
            }
        });

        menuBar.add(fileJMenu);

        setJMenuBar(menuBar);

        this.journal = journal;
        table = new JTable(new MyTableModel(this.journal));

        table.setPreferredSize(new Dimension(780, 450));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setColumnSelectionAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(0);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(2).setPreferredWidth(35);
        table.getColumnModel().getColumn(3).setResizable(false);
        table.getColumnModel().getColumn(4).setResizable(false);
        table.getColumnModel().getColumn(4).setPreferredWidth(17);
        table.getColumnModel().getColumn(5).setResizable(false);
        table.getColumnModel().getColumn(5).setPreferredWidth(38);
        table.getTableHeader().setReorderingAllowed(false);
        ListSelectionModel lm = new DefaultListSelectionModel();
        lm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionModel(lm);
        table.getSelectionModel().addListSelectionListener(new tableListener());

        DefaultTableCellRenderer r = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
        r.setHorizontalAlignment(JLabel.CENTER);
        r.setVerticalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(r);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(780, 480));
        add(scrollPane);


        upgrade = new JButton("Восстановить из копии");
        upgrade.setEnabled(false);
        information = new JButton("Сведения о файле");
        information.setEnabled(false);
        delete = new JButton("Удалить копирование");
        delete.setEnabled(false);
        ActionListener buttons = new buttonsActionListener();
        upgrade.addActionListener(buttons);
        information.addActionListener(buttons);
        delete.addActionListener(buttons);

        add(upgrade);
        add(information);
        add(delete);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = { "Выйти", "Отмена" };
                int n = JOptionPane.showOptionDialog(e.getWindow(), "Вы уверены, что хотите выйти?",
                        "Выход из программы", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(n==0){
                    try {
                        DataManager.saveJournal();
                    }
                    catch (Exception except){ JOptionPane.showConfirmDialog(null,except.getMessage(),"Ошибка!",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE); }
                    setVisible(false);
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                MainWindow.super.toFront();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                if (infoWindow != null) {
                    infoWindow.setState(NORMAL);
                    infoWindow.toFront();
                } else if (addWindow != null) {
                    addWindow.setState(NORMAL);
                    addWindow.toFront();
                } else if (upgradeWindow != null) {
                    upgradeWindow.setState(NORMAL);
                    upgradeWindow.toFront();
                }

            }

            @Override
            public void windowActivated(WindowEvent e) {
                if (infoWindow != null) {
                    infoWindow.setState(NORMAL);
                    infoWindow.toFront();
                } else if (addWindow != null) {
                    addWindow.setState(NORMAL);
                    addWindow.toFront();
                } else if (upgradeWindow != null) {
                    upgradeWindow.setState(NORMAL);
                    upgradeWindow.toFront();
                }
            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public class MyTableModel implements TableModel {

        private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

        private Journal journal;

        public MyTableModel(Journal journal) {
            this.journal = journal;
        }

        public void addTableModelListener(TableModelListener listener) {
            listeners.add(listener);
        }

        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        public int getColumnCount() {
            return 6;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Имя";
                case 1:
                    return "Вид файла";
                case 2:
                    return "Режим копирования";
                case 3:
                    return "Интервал копирования";
                case 4:
                    return "Количество копий";
                case 5:
                    return "Дата последней копии";
            }
            return "";
        }

        public int getRowCount() {
            return journal.size();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return journal.get(rowIndex).getName();
                case 1:
                    if (journal.get(rowIndex).isFile())
                        return "Файл";
                    else
                        return "Директория";
                case 2:
                    switch (journal.get(rowIndex).getMode()) {
                        case INC:
                            return "Инкременталтный";
                        case DIF:
                            return "Дифференциальный";
                    }
                case 3:
                    return TimeInMillisParcer.timeToCopyInMillisToString(journal.get(rowIndex).getTimeToCopy());
                case 4:
                    return journal.get(rowIndex).getListOfCopiesTimes().size();
                case 5:
                    if (journal.get(rowIndex).getListOfCopiesTimes().size() == 0)
                        return "-";
                    else
                        return TimeInMillisParcer.millisToDate(journal.get(rowIndex).getLastCopyTime());
            }
            return "";
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public void removeTableModelListener(TableModelListener listener) {
            listeners.remove(listener);
        }

        public void setValueAt(Object value, int rowIndex, int columnIndex) {

        }

    }

    public class tableListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            upgrade.setEnabled(true);
            information.setEnabled(true);
            delete.setEnabled(true);
        }
    }

    public class buttonsActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == information) {
                if (infoWindow == null) {
                    infoWindow = new InfoWindow(journal.get(table.getSelectedRow()));
                    infoWindow.setVisible(true);
                    infoWindow.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                            MainWindow.super.setEnabled(false);
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            infoWindow = null;
                            MainWindow.super.setEnabled(true);
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {
                            MainWindow.super.setState(ICONIFIED);
                            infoWindow.setState(ICONIFIED);
                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {
                            MainWindow.super.setState(NORMAL);
                            MainWindow.super.toFront();
                            infoWindow.setState(NORMAL);
                            infoWindow.setFocusable(true);
                            infoWindow.toFront();
                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }

                    });
                }
            }

            if (e.getSource() == upgrade) {
                upgradeWindow = new UpgradeWindow(journal.get(table.getSelectedRow()));
                upgradeWindow.setVisible(true);
                upgradeWindow.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        MainWindow.super.setEnabled(false);
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        MainWindow.super.setState(NORMAL);
                        MainWindow.super.toFront();
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        upgradeWindow = null;
                        MainWindow.super.setEnabled(true);
                        MainWindow.super.setState(NORMAL);
                        MainWindow.super.toFront();
                    }

                    @Override
                    public void windowIconified(WindowEvent e) {
                        MainWindow.super.setState(ICONIFIED);
                        upgradeWindow.setState(ICONIFIED);
                    }

                    @Override
                    public void windowDeiconified(WindowEvent e) {
                        MainWindow.super.setState(NORMAL);
                        MainWindow.super.toFront();
                        upgradeWindow.setState(NORMAL);
                        upgradeWindow.setFocusable(true);
                        upgradeWindow.toFront();
                    }

                    @Override
                    public void windowActivated(WindowEvent e) {

                    }

                    @Override
                    public void windowDeactivated(WindowEvent e) {

                    }

                });
            }
            if (e.getSource() == delete) {
                Object[] options = {"Удалить", "Отмена"};
                int n = JOptionPane.showOptionDialog(MainWindow.super.getParent(), "Вы уверены, что хотите удалить объект копирования? Безвозвратно будут удалены все созданные копии файла.", "Удалить копирование", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (n == 0) journal.delete(table.getSelectedRow());
                information.setEnabled(false);
                delete.setEnabled(false);
                upgrade.setEnabled(false);
                table.updateUI();
            }

        }


    }

    private Journal getJournal(){ return  journal; }

    @Override
    public void dataChanged() {
        table.updateUI();
    }


}



