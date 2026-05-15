package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {

        setTitle("LAN File Sharing");

        setSize(900, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color background =
                new Color(15, 23, 42);

        Color cardColor =
                new Color(30, 41, 59);

        Color blue =
                new Color(59, 130, 246);

        Color green =
                new Color(16, 185, 129);

        setLayout(new GridBagLayout());

        getContentPane().setBackground(background);

        JPanel card =
                new JPanel(new GridBagLayout());

        card.setPreferredSize(
                new Dimension(620, 520)
        );

        card.setBackground(cardColor);

        card.setBorder(
                new EmptyBorder(35, 40, 35, 40)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;

        gbc.gridy = 0;

        gbc.insets =
                new Insets(10, 0, 10, 0);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // LOGO

        JLabel logo =
                new JLabel(
                        "◉",
                        SwingConstants.CENTER
                );

        logo.setForeground(blue);

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        54
                )
        );

        card.add(logo, gbc);

        // TITLE

        gbc.gridy++;

        JLabel title =
                new JLabel(
                        "LAN File Sharing",
                        SwingConstants.CENTER
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        34
                )
        );

        card.add(title, gbc);

        // SUBTITLE

        gbc.gridy++;

        JLabel subtitle =
                new JLabel(
                        "Fast and secure local network transfer",
                        SwingConstants.CENTER
                );

        subtitle.setForeground(
                new Color(148, 163, 184)
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        card.add(subtitle, gbc);

        // SPACING

        gbc.gridy++;

        gbc.insets =
                new Insets(35, 0, 12, 0);

        JButton sendButton =
                createButton(
                        "Send Files",
                        blue
                );

        card.add(sendButton, gbc);

        // RECEIVE BUTTON

        gbc.gridy++;

        gbc.insets =
                new Insets(12, 0, 12, 0);

        JButton receiveButton =
                createButton(
                        "Receive Files",
                        green
                );

        card.add(receiveButton, gbc);

        // EXIT BUTTON

        gbc.gridy++;

        gbc.insets =
                new Insets(12, 0, 0, 0);

        JButton exitButton =
                createButton(
                        "Exit",
                        new Color(71, 85, 105)
                );

        card.add(exitButton, gbc);

        add(card);

        // ACTIONS

        sendButton.addActionListener(e -> {

            dispose();

            new SenderScreen();
        });

        receiveButton.addActionListener(e -> {

            dispose();

            new ReceiverScreen();
        });

        exitButton.addActionListener(e ->
                System.exit(0)
        );

        setVisible(true);
    }

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        button.setPreferredSize(
                new Dimension(200, 46)
        );

        return button;
    }
}