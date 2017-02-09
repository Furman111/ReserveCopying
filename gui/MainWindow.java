package gui;

import copyingFiles.DirectoryCopyObject;
import copyingFiles.FileCopyObject;
import copyingFiles.Journal;
import modesOfCopying.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import util.*;

/**
 * Created by Furman on 02.02.2017.
 */
public class MainWindow extends JFrame {

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

    public MainWindow() {
        super("Резревное копирование");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultLookAndFeelDecorated(true);
        setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileJMenu = new JMenu("Файл");

        createNewCopyJMenu = new JMenuItem("Добавить файл для копирования");
        fileJMenu.add(createNewCopyJMenu);

        setCopyFolder = new JMenuItem("Указать папку для сохранения копий");
        fileJMenu.add(setCopyFolder);

        fileJMenu.addSeparator();

        closeOperation = new JMenuItem("Выйти из программы");
        fileJMenu.add(closeOperation);

        menuBar.add(fileJMenu);

        setJMenuBar(menuBar);

        this.journal = new Journal();
        this.journal.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\ewew.txt"), new File("dsdsdsddxasx"), Mode.INC, 20000));
        this.journal.add(new DirectoryCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\dsasew"), new File("dsdsdsddxasx"), Mode.DIF, 14400));
        table = new JTable(new MyTableModel(this.journal));

        table.setPreferredSize(new Dimension(780, 450));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setColumnSelectionAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(3).setResizable(false);
        table.getColumnModel().getColumn(4).setResizable(false);
        table.getColumnModel().getColumn(5).setResizable(false);
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
                            return "Инкрементное";
                        case DIF:
                            return "Дифференциальное";
                    }
                case 3:
                    return journal.get(rowIndex).getTimeToCopy() / 1000;
                case 4:
                    return journal.get(rowIndex).getListOfCopiesTimes().size();
                case 5:
                    return TimeOperations.millisToDate(journal.get(rowIndex).getLastCopyTime());
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

                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            infoWindow = null;
                            MainWindow.super.setEnabled(true);
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

                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        upgradeWindow = null;
                        MainWindow.super.setEnabled(true);
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
        }


    }
}



