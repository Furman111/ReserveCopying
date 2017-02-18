package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Furman on 18.02.2017.
 */
public class ProcessingWindow extends JFrame {

    private JLabel label;

    public ProcessingWindow(String title, String message) {
        super(title);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(300, 70);
        ImageIcon icon = new ImageIcon("Icon.png");
        setIconImage(icon.getImage());


        label = new JLabel(message);
        label.setVerticalTextPosition(SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(290, 65));
        label.updateUI();
        add(label);

        setAlwaysOnTop(true);
    }

}
