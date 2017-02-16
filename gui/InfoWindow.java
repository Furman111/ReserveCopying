package gui;

import copyingFiles.CopyObject;
import javafx.geometry.VerticalDirection;
import observing.Observer;
import util.TimeInMillisParcer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by Furman on 08.02.2017.
 */
public class InfoWindow extends JFrame {
    private CopyObject object;

    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel pathToFileLabel;
    private JLabel pathToCopiesLabel;
    private JLabel numberOfCopiesLabel;
    private JLabel isDeletedLabel;
    private JLabel modeLabel;
    private JLabel copiesTimesLabel;
    private JButton okButton;
    private ArrayList<JLabel> times;
    private JPanel pane;
    private JScrollPane scrollPane;


    public InfoWindow(CopyObject object) {
        super("Сведения о файле");
        this.object = object;


        setSize(480, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultLookAndFeelDecorated(true);
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());

        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));



        nameLabel = new JLabel("Имя: " + this.object.getName());
        pane.add(nameLabel);

        if (this.object.isFile())
            typeLabel = new JLabel("Тип: файл");
        else
            typeLabel = new JLabel("Тип: директория");
        pane.add(typeLabel);

        pathToFileLabel = new JLabel("Путь к файлу: " + this.object.getPath());
        pane.add(pathToFileLabel);

        switch (object.getMode()) {
            case INC:
                modeLabel = new JLabel("Режим резервного копирования: инкрементальный");
            case DIF:
                modeLabel = new JLabel("Режим резервного копирования: дифференциальный");
        }
        pane.add(modeLabel);

        pathToCopiesLabel = new JLabel("Путь к копиям: " + this.object.getPathToCopies());
        pane.add(pathToCopiesLabel);

        numberOfCopiesLabel = new JLabel("Количество сделанных копий: " + this.object.getListOfCopiesTimes().size());
        pane.add(numberOfCopiesLabel);

        if ((this.object.getListOfCopiesTimes().size() > 0) && (object.isDeleted())) {
            isDeletedLabel = new JLabel("Исходный файл на данный момент удалён");
            pane.add(isDeletedLabel);
        }

        times = new ArrayList<>();

        if (this.object.getListOfCopiesTimes().size() > 0) {
            copiesTimesLabel = new JLabel("Копии сделаны в следующие моменты времени:");
            pane.add(copiesTimesLabel);
            for (int i = 0; i < this.object.getListOfCopiesTimes().size(); i++) {
                times.add(new JLabel(i+1+") "+TimeInMillisParcer.millisToDate(this.object.getListOfCopiesTimes().get(i))));
                pane.add(times.get(i));
            }
        }


        scrollPane = new JScrollPane(pane);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(470,410));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
        okButton = new JButton("Ок");
        okButton.setFocusPainted(false);
        okButton.setPreferredSize(new Dimension(100, 20));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(okButton);

    }

}