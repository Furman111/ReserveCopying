package gui;

import copyingFiles.CopyObject;
import util.TimeInMillisParcer;
import javax.management.OperationsException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;


/**
 * Created by Furman on 09.02.2017.
 */
public class UpgradeWindow extends JFrame {

    private CopyObject object;
    private JLabel chooseLabel;
    private JLabel timeLabel;
    private JComboBox<String> timesComboBox;
    private JButton chooseButton;

    public UpgradeWindow(CopyObject upgradingObject){
        super("Восстановить "+upgradingObject.getName());
        object = upgradingObject;

        chooseLabel = new JLabel("Выберите время состояния файла, которое вы хотите восстановить:");
        chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLabel.setPreferredSize(new Dimension(450,20));
        add(chooseLabel);

        timeLabel = new JLabel("Дата и время: ");
        timeLabel.setPreferredSize(new Dimension(100,20));
        add(timeLabel);

        timesComboBox = new JComboBox<>();
        timesComboBox.setPreferredSize(new Dimension(200,20));
        add(timesComboBox);
        timesComboBox.setEditable(false);

        chooseButton = new JButton("Восстановить");
        chooseButton.setPreferredSize(new Dimension(140,20));
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
                            if(!object.upgrade(object.getListOfCopiesTimes().get(timesComboBox.getSelectedIndex())))
                                throw new OperationsException();
                        }
                        catch (NoSuchElementException e1){
                            upgradingObject.repairCopies();
                            JOptionPane.showConfirmDialog(null, "Копия файла была удалена или повреждена!", "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                        }
                        catch (OperationsException e2){
                            JOptionPane.showConfirmDialog(null, "Операция восстановления не может быть выполнена! "+e2.getMessage(), "Ошибка!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                        }
                        finally {
                            dispose();
                        }
                    }
                });
            }
        });

        for(long time: upgradingObject.getListOfCopiesTimes())
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
