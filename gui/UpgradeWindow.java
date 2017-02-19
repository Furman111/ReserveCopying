package gui;

import copyingFiles.CopyObject;
import copyingObjectsOperations.SynchronizedOperations;
import timeUtilits.TimeInMillisParcer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UpgradeWindow extends JFrame {

    private CopyObject object;
    private JLabel chooseLabel;
    private JLabel timeLabel;
    private JComboBox<String> timesComboBox;
    private JButton chooseButton;
    private SynchronizedOperations operator;
    private JFrame processingWindow;

    public UpgradeWindow(CopyObject upgradingObject, SynchronizedOperations operator) {
        super("Восстановить " + upgradingObject.getName());
        object = upgradingObject;
        processingWindow = null;

        this.operator = operator;

        chooseLabel = new JLabel("Выберите время состояния файла, которое вы хотите восстановить:");
        chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLabel.setPreferredSize(new Dimension(450, 20));
        add(chooseLabel);

        timeLabel = new JLabel("Дата и время: ");
        timeLabel.setPreferredSize(new Dimension(100, 20));
        add(timeLabel);

        timesComboBox = new JComboBox<>();
        timesComboBox.setPreferredSize(new Dimension(200, 20));
        add(timesComboBox);
        timesComboBox.setEditable(false);

        chooseButton = new JButton("Восстановить");
        chooseButton.setPreferredSize(new Dimension(140, 20));
        chooseButton.setFocusPainted(false);
        chooseButton.setEnabled(false);
        add(chooseButton);


        timesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                chooseButton.setEnabled(true);
                chooseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            setVisible(false);
                            processingWindow = new ProcessingWindow("Восстановление", "Файл восстанавливается...");
                            processingWindow.setLocationRelativeTo(UpgradeWindow.super.getRootPane());
                            processingWindow.setVisible(true);
                            operator.upgrade(object, object.getListOfCopiesTimes().get(timesComboBox.getSelectedIndex()));
                            processingWindow.setVisible(false);
                        } catch (NoSuchElementException e1) {
                            processingWindow.setVisible(false);
                            upgradingObject.repairCopies();
                            JOptionPane.showConfirmDialog(null, "Копия файла была удалена или повреждена!", "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                        } finally {
                            processingWindow.dispose();
                            processingWindow = null;
                            dispose();
                        }
                    }
                });
            }
        });

        for (long time : upgradingObject.getListOfCopiesTimes())
            timesComboBox.addItem(TimeInMillisParcer.millisToDate(time));


        setSize(460, 120);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        ImageIcon icon = new ImageIcon("Icon.png");
        setIconImage(icon.getImage());
    }

}
