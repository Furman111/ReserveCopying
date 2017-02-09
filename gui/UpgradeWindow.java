package gui;

import copyingFiles.CopyObject;
import util.TimeOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
        timesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                chooseButton.setEnabled(true);
                chooseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        object.upgrade(object.getListOfCopiesTimes().get(timesComboBox.getSelectedIndex()));
                    }
                });
            }
        });

        for(long time: upgradingObject.getListOfCopiesTimes())
            timesComboBox.addItem(TimeOperations.millisToDate(time));

        chooseButton = new JButton("Восстановить");
        chooseButton.setPreferredSize(new Dimension(140,20));
        chooseButton.setFocusPainted(false);
        chooseButton.setEnabled(false);
        add(chooseButton);

        setSize(460, 120);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());
    }

}
