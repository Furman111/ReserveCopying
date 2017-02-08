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
import javax.swing.event.TableModelListener;
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

        Journal d = new Journal();
        d.add(new FileCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\ewew.txt"), new File("dsdsdsddxasx"), Mode.INC, 20000));
        d.add(new DirectoryCopyObject(new File("C:\\Users\\Furman\\Desktop\\test\\from\\dsasew"), new File("dsdsdsddxasx"), Mode.DIF, 14400));
        table = new JTable(new MyTableModel(d));
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
        lm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        table.setSelectionModel(lm);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(780,480));
        add(scrollPane);



        upgrade = new JButton("Восстановить из копии");
        upgrade.setEnabled(false);
        information = new JButton("Сведения о файле");
        information.setEnabled(false);
        delete = new JButton("Удалить копирование");
        delete.setEnabled(true);

        add(upgrade);
        add(information);
        add(delete);
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



    public class eHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

             /*   if (e.getSource() == n1) {
                    if (res.getText().contains("="))
                            res.setText("");
                    res.setText(res.getText()+"1");
                }
                if (e.getSource() == n2) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"2");
                }
                if (e.getSource() == n3) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"3");
                }
                if (e.getSource() == n4) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"4");
                }
                if (e.getSource() == n5) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"5");
                }
                if (e.getSource() == n6) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"6");
                }
                if (e.getSource() == n7) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"7");
                }
                if (e.getSource() == n8) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"8");
                }
                if (e.getSource() == n9) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"9");
                }
                if (e.getSource() == n0) {
                    if (res.getText().contains("="))
                        res.setText("");
                    res.setText(res.getText()+"0");
                }
                if (e.getSource() == plus) {
                    if (res.getText().contains("=")) {
                        res.setText("");
                    }
                    else
                     if(res.getText()!="")
                         res.setText(res.getText()+" + ");
                }
                if (e.getSource() == equal) {
                    if (!res.getText().contains("=")) {
                        res.setText(res.getText()+ " = res");
                    }
                }*/


        }


    }

}
