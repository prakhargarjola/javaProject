package screens;

import javax.swing.*;
import java.awt.*;

public class ReceiverScreen extends JFrame {

    private JLabel statusLabel;

    private JTextArea logArea;

    public ReceiverScreen() {

        setTitle("LAN File Sharing System");

        setSize(600, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Receive File");

        title.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                22
        ));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(title, gbc);

        gbc.gridy++;

        JButton startButton =
                new JButton("Start Receiver");

        mainPanel.add(startButton, gbc);

        gbc.gridy++;

        statusLabel =
                new JLabel("Receiver Offline");

        mainPanel.add(statusLabel, gbc);

        gbc.gridy++;

        logArea = new JTextArea(10, 25);

        logArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(logArea);

        mainPanel.add(scrollPane, gbc);

        gbc.gridy++;

        JPanel buttonPanel = new JPanel();

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, gbc);

        startButton.addActionListener(e -> {

            statusLabel.setText(
                    "Receiver Running"
            );

            logArea.append(
                    "Waiting for incoming connection...\n"
            );
        });

        backButton.addActionListener(e -> {

            dispose();

            new HomeScreen();
        });

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}