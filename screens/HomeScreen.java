package screens;

import components.CustomButton;
import utils.Theme;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {

        setTitle("LAN File Sharing System");

        setSize(700, 500);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        getContentPane().setBackground(Theme.BACKGROUND);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();

        panel.setBackground(Theme.PANEL);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(
                40, 40, 40, 40));

        JLabel title = new JLabel("LAN File Sharing System");

        title.setFont(Theme.TITLE_FONT);

        title.setForeground(Color.WHITE);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Fast Local Network File Transfer"
        );

        subtitle.setForeground(Color.BLACK);

        subtitle.setFont(Theme.NORMAL_FONT);

        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        CustomButton sendButton =
                new CustomButton("Send File");

        CustomButton receiveButton =
                new CustomButton("Receive File");

        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sendButton.setMaximumSize(new Dimension(220, 45));
        receiveButton.setMaximumSize(new Dimension(220, 45));

        sendButton.addActionListener(e -> {
            dispose();
            new SenderScreen();
        });

        receiveButton.addActionListener(e -> {
            dispose();
            new ReceiverScreen();
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(sendButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(receiveButton);

        add(panel, gbc);

        setVisible(true);
    }
}