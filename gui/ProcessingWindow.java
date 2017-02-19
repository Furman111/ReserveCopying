package gui;

import javax.swing.*;
import java.awt.*;


public class ProcessingWindow extends JFrame {

    private JLabel label;
    private JPanel panel;

    public ProcessingWindow(String title, String message) {
        super(title);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new FlowLayout());
        setSize(400, 20);
        ImageIcon icon = new ImageIcon("Icon.png");
        setIconImage(icon.getImage());
        setDefaultLookAndFeelDecorated(true);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(panel);

        label = new JLabel(message);
        label.setVerticalTextPosition(SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(label);

        setAlwaysOnTop(true);
    }

}
