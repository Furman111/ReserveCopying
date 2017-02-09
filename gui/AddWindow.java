package gui;

import copyingFiles.Journal;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;


/**
 * Created by Furman on 09.02.2017.
 */
public class AddWindow extends JFrame {

    private Journal journal;
    private JLabel chooseFileLabel;
    private JLabel filePathLabel;
    private JButton fileChooserButton;
    private JLabel chooseCopyDirectoryLabel;
    private JLabel copyDirectoryPath;
    private JButton copyDirectoryButton;
    private JComboBox<String> modeComboBox;
    private JLabel chooseModeLabel;
    private JLabel chooseTimeLabel;
    private JLabel intervalLabel;
    private JFormattedTextField dateField;
    private JButton createButton,clearButton;
    private JLabel attentionLabel;


    private int height,width;

    public AddWindow(){
        super("Добавить новый файл для резервного копирования");
        height=360;
        width=800;
        setSize(width,height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        setDefaultLookAndFeelDecorated(true);
        ImageIcon icon = new ImageIcon("data.png");
        setIconImage(icon.getImage());

        chooseFileLabel = new JLabel("Выберете файл для резервного копирования:");
        chooseFileLabel.setPreferredSize(new Dimension(780,20));
        chooseFileLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(chooseFileLabel);

        filePathLabel = new JLabel("Файл не выбран...");
        filePathLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(25, 0, 25, 0)));
        filePathLabel.setPreferredSize(new Dimension(640,30));
        filePathLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(filePathLabel);

        fileChooserButton = new JButton("Выбрать файл");
        fileChooserButton.setPreferredSize(new Dimension(130,30));
        fileChooserButton.setHorizontalAlignment(SwingConstants.CENTER);
        fileChooserButton.setFocusPainted(false);
        add(fileChooserButton);

        chooseCopyDirectoryLabel = new JLabel("Выберите директорию, где будут хранится резервные копии указанного файла:");
        chooseCopyDirectoryLabel.setPreferredSize(new Dimension(780,20));
        chooseCopyDirectoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(chooseCopyDirectoryLabel);

        copyDirectoryPath = new JLabel("Файл не выбран...");
        copyDirectoryPath.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(25, 0, 25, 0)));
        copyDirectoryPath.setPreferredSize(new Dimension(640,30));
        copyDirectoryPath.setHorizontalAlignment(SwingConstants.LEFT);
        add(copyDirectoryPath);

        copyDirectoryButton = new JButton("Выбрать файл");
        copyDirectoryButton.setPreferredSize(new Dimension(130,30));
        copyDirectoryButton.setHorizontalAlignment(SwingConstants.CENTER);
        copyDirectoryButton.setFocusPainted(false);
        add(copyDirectoryButton);

        chooseModeLabel = new JLabel("Выберите режим резервного копирования: ");
        chooseModeLabel.setPreferredSize(new Dimension(260,50));
        chooseModeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        chooseModeLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(chooseModeLabel);

        modeComboBox = new JComboBox<>();
        modeComboBox.setPreferredSize(new Dimension(200,20));
        modeComboBox.addItem("Дифференциальный");
        modeComboBox.addItem("Инкрементальный");
        add(modeComboBox);

        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(300,50));
        add(panel);

        chooseTimeLabel = new JLabel("<html>Выберите временной интервал, через который будут создаваться резервные копии указанного файла.<br>Для этого, введите недели, дни, часы, минуты в соответствующие столбцы:</html>");
        chooseTimeLabel.setPreferredSize(new Dimension(780,50));
        chooseTimeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        chooseTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(chooseTimeLabel);

        intervalLabel = new JLabel("Введите интервал: ");
        intervalLabel.setHorizontalAlignment(SwingConstants.LEFT);
        intervalLabel.setPreferredSize(new Dimension(115,20));
        add(intervalLabel);

        try {
            MaskFormatter format = new MaskFormatter("Недели: ## Дни: ## Часы: ## Минуты: ##");
            format.setPlaceholderCharacter('_');
            dateField = new JFormattedTextField(format);
            dateField.setPreferredSize(new Dimension(240,20));
            dateField.setHorizontalAlignment(SwingConstants.LEFT);
            add(dateField);
        }
        catch (Exception e){
            dateField.setText("");
        }

        panel.setPreferredSize(new Dimension(780,40));
        add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        createButton = new JButton("Создать");
        createButton.setFocusPainted(false);
        createButton.setPreferredSize(new Dimension(200,30));
        panel.add(createButton);

        clearButton = new JButton("Очистить поля");
        clearButton.setFocusPainted(false);
        clearButton.setPreferredSize(new Dimension(200,30));
        panel.add(clearButton);


      /*  panel.setPreferredSize(new Dimension(790,20));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(panel);*/
        attentionLabel = new JLabel("Какая-то ошибка");
        attentionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attentionLabel.setPreferredSize(new Dimension(780,20));
        attentionLabel.setOpaque(true);
        attentionLabel.setBackground(Color.red);
        attentionLabel.setForeground(Color.WHITE);
        attentionLabel.setVisible(false);
        add(attentionLabel);


    }

}
