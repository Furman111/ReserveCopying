package gui;

import copyingFiles.CopyObject;
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
public class InfoWindow extends JFrame implements Observer {
    private CopyObject object;

    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel pathToFileLabel;
    private JLabel pathToCopiesLabel;
    private JLabel numberOfCopiesLabel;
    private JLabel isDeletedLabel;
    private JLabel modeLabel;
    private JLabel copiesTimesLabel;
    private int height;
    private int width;
    private JButton okButton;
    private ArrayList<JLabel> times;

    public InfoWindow(CopyObject object) {
        super("Сведения о файле");
        this.object = object;

        width = 50;
        if (this.object.getPathToCopies().length() + 15 > width)
            width = this.object.getPathToCopies().length() + 15;
        else if (this.object.getPath().length() + 15 > width)
            width = this.object.getPath().length() + 15;


        nameLabel = new JLabel("Имя: " + this.object.getName());
        nameLabel.setPreferredSize(new Dimension(width * 7, 20));
        add(nameLabel);

        if (this.object.isFile())
            typeLabel = new JLabel("Тип: файл");
        else
            typeLabel = new JLabel("Тип: директория");
        add(typeLabel);
        typeLabel.setPreferredSize(new Dimension(width * 7, 20));

        pathToFileLabel = new JLabel("Путь к файлу: " + this.object.getPath());
        pathToFileLabel.setPreferredSize(new Dimension(width * 7, 20));
        add(pathToFileLabel);

        switch (object.getMode()) {
            case INC:
                modeLabel = new JLabel("Режим резервного копирования: инкрементальный");
            case DIF:
                modeLabel = new JLabel("Режим резервного копирования: дифференциальный");
        }
        modeLabel.setPreferredSize(new Dimension(width * 7, 20));
        add(modeLabel);

        pathToCopiesLabel = new JLabel("Путь к копиям: " + this.object.getPathToCopies());
        pathToCopiesLabel.setPreferredSize(new Dimension(width * 7, 20));
        add(pathToCopiesLabel);

        numberOfCopiesLabel = new JLabel("Количество сделанных копий: " + this.object.getListOfCopiesTimes().size());
        add(numberOfCopiesLabel);
        numberOfCopiesLabel.setPreferredSize(new Dimension(width * 7, 20));

        if ((this.object.getListOfCopiesTimes().size() > 0) && (object.isDeleted())) {
            isDeletedLabel = new JLabel("Исходный файл на данный момент удалён");
            isDeletedLabel.setPreferredSize(new Dimension(width * 7, 20));
            add(isDeletedLabel);
        }

        times = new ArrayList<>();

        if (this.object.getListOfCopiesTimes().size() > 0) {
            copiesTimesLabel = new JLabel("Копии сделаны в следующие моменты времени:");
            copiesTimesLabel.setPreferredSize(new Dimension(width * 7, 20));
            add(copiesTimesLabel);
            for (int i = 0; i < this.object.getListOfCopiesTimes().size(); i++) {
                times.add(new JLabel(TimeInMillisParcer.millisToDate(this.object.getListOfCopiesTimes().get(i))));
                times.get(i).setPreferredSize(new Dimension(width * 7, 10));
                add(times.get(i));
            }
        }

        height = 160;
        if (this.object.getListOfCopiesTimes().size() > 0)
            height = height + 20 + object.getListOfCopiesTimes().size() * 10;
        if (this.object.getListOfCopiesTimes().size() > 0 && object.isDeleted())
            height += 20;

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

        setSize(width * 7 + 30, height + 60);
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultLookAndFeelDecorated(true);
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());

    }

    public void dataChanged() {
        nameLabel.setText("Имя: " + this.object.getName());
        pathToFileLabel.setText("Путь к файлу: " + this.object.getPath());
        pathToCopiesLabel.setText("Путь к копиям: " + this.object.getPathToCopies());
        numberOfCopiesLabel.setText("Количество сделанных копий: " + this.object.getListOfCopiesTimes().size());
        if (isDeletedLabel != null)
            remove(isDeletedLabel);
        if ((this.object.getListOfCopiesTimes().size() > 0) && (object.isDeleted())) {
            isDeletedLabel = new JLabel("Исходный файл на данный момент удалён");
            isDeletedLabel.setPreferredSize(new Dimension(width * 7, 20));
            add(isDeletedLabel);
        }

        if(copiesTimesLabel!=null){
            remove(copiesTimesLabel);
            for(JLabel l:times)
                remove(l);
            times.clear();
        }
        if (this.object.getListOfCopiesTimes().size() > 0) {
            copiesTimesLabel = new JLabel("Копии сделаны в следующие моменты времени:");
            copiesTimesLabel.setPreferredSize(new Dimension(width * 7, 20));
            add(copiesTimesLabel);
            for (int i = 0; i < this.object.getListOfCopiesTimes().size(); i++) {
                times.add(new JLabel(TimeInMillisParcer.millisToDate(this.object.getListOfCopiesTimes().get(i))));
                times.get(i).setPreferredSize(new Dimension(width * 7, 10));
                add(times.get(i));
            }
        }


    }

}