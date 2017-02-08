package gui;

import copyingFiles.CopyObject;
import util.TimeOperations;

import javax.swing.*;
import java.awt.*;

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
    private int height;
    private int width;

    public InfoWindow(CopyObject object) {
        super("Сведения о файле");
        this.object = object;

       width = 50;
       if(this.object.getPathToCopies().length()+15>width)
           width = this.object.getPathToCopies().length()+15;
       else
           if(this.object.getPath().length()+15>width)
               width = this.object.getPath().length()+15;



        nameLabel = new JLabel("Имя: "+this.object.getName());
        nameLabel.setPreferredSize(new Dimension(width*7,20));
        add(nameLabel);

        if(this.object.isFile())
            typeLabel = new JLabel("Тип: файл");
        else
            typeLabel = new JLabel("Тип: директория");
        add(typeLabel);
        typeLabel.setPreferredSize(new Dimension(width*7,20));

        pathToFileLabel = new JLabel("Путь к файлу: "+this.object.getPath());
        pathToFileLabel.setPreferredSize(new Dimension(width*7,20));
        add(pathToFileLabel);

        switch (object.getMode()){
            case INC:
                modeLabel = new JLabel("Режим резервного копирования: инкрементальный");
            case DIF:
                modeLabel = new JLabel("Режим резервного копирования: дифференциальный");
        }
        modeLabel.setPreferredSize(new Dimension(width*7,20));
        add(modeLabel);

        pathToCopiesLabel = new JLabel("Путь к копиям: " + this.object.getPathToCopies());
        pathToCopiesLabel.setPreferredSize(new Dimension(width*7,20));
        add(pathToCopiesLabel);

        numberOfCopiesLabel = new JLabel("Количество сделанных копий: " + this.object.getListOfCopiesTimes().size());
        add(numberOfCopiesLabel);
        numberOfCopiesLabel.setPreferredSize(new Dimension(width*7,20));

        if((this.object.getListOfCopiesTimes().size()>0) && (object.isDeleted())) {
            isDeletedLabel = new JLabel("Исходный файл на данный момент удалён");
            isDeletedLabel.setPreferredSize(new Dimension(width*7,20));
            add(isDeletedLabel);
        }

        if(this.object.getListOfCopiesTimes().size()>0) {
            copiesTimesLabel = new JLabel("Копии сделаны в следующие моменты времени:");
            copiesTimesLabel.setPreferredSize(new Dimension(width*7,20));
            add(copiesTimesLabel);
            for(int i=0;i<this.object.getListOfCopiesTimes().size();i++){
                JLabel temp = new JLabel(TimeOperations.millisToDate(this.object.getListOfCopiesTimes().get(i)));
                temp.setPreferredSize(new Dimension(width*7,10));
                add(temp);
            }
        }

        height = 120;
        if (this.object.getListOfCopiesTimes().size()>0)
            height=height+20+object.getListOfCopiesTimes().size()*10;
        if(this.object.getListOfCopiesTimes().size()>0 && object.isDeleted())
            height+=20;

        setSize(width*7+30,height+60);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultLookAndFeelDecorated(true);
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());
    }
}
