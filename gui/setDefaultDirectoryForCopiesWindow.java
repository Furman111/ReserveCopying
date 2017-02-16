package gui;

import dataManager.DataManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by FurmanT on 11.02.2017.
 */
public class setDefaultDirectoryForCopiesWindow extends JFrame {

    private JLabel mainLabel;
    private JLabel pathLabel;
    private JButton changeButton;
    private JButton copyDirectoryButton;
    private JFileChooser copyDirectoryChooser;
    private JButton acceptButton, cancelButton;

    public setDefaultDirectoryForCopiesWindow() {

        super("Изменить директорию для сохранения копий");
        setSize(600, 145);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setDefaultLookAndFeelDecorated(true);
        setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("Icon.png");
        setIconImage(icon.getImage());

        mainLabel = new JLabel("Для выбора директории сохранения копий по умолчанию, нажмите кнопку \"Изменить\":");
        mainLabel.setPreferredSize(new Dimension(600, 30));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(mainLabel);

        try {
            pathLabel = new JLabel(DataManager.getDefaultDirectoryForCopies().getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this,e.getMessage(),"Ошибка!",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
        }
        pathLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(25, 0, 25, 0)));
        pathLabel.setPreferredSize(new Dimension(450, 30));
        pathLabel.setHorizontalAlignment(SwingConstants.LEFT);
        pathLabel.setOpaque(true);
        pathLabel.setBackground(Color.WHITE);
        add(pathLabel);

        changeButton = new JButton("Изменить");
        changeButton.setFocusPainted(false);
        changeButton.setPreferredSize(new Dimension(130, 30));
        add(changeButton);

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyDirectoryChooser = new JFileChooser();
                copyDirectoryChooser.setApproveButtonText("Выбрать");
                copyDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = copyDirectoryChooser.showDialog(null, "Выбрать директорию для сохранения копий");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    pathLabel.setText(copyDirectoryChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        acceptButton = new JButton("Принять");
        acceptButton.setPreferredSize(new Dimension(120, 30));
        acceptButton.setFocusPainted(false);
        add(acceptButton);

        cancelButton = new JButton("Отмена");
        cancelButton.setPreferredSize(new Dimension(120, 30));
        cancelButton.setFocusPainted(false);
        add(cancelButton);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManager.setDefaultDirectoryForCopies(new File(pathLabel.getText()));
                try {
                    DataManager.saveDefaultDirectoryForCopies();
                }
                catch (Exception exc){
                    JOptionPane.showConfirmDialog(null,exc.getMessage(),"Ошибка!",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }
}
