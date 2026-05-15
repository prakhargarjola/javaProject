package screens;

import network.FileTransferManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.InetAddress;

public class ReceiverScreen extends JFrame {

    private JLabel statusLabel;

    private JTextArea logArea;

    private JProgressBar progressBar;

    private final FileTransferManager manager;

    private boolean receiverStarted = false;

    public ReceiverScreen() {

        manager = new FileTransferManager();

        setTitle("Receive Files");

        setSize(900, 760);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color background =
                new Color(15, 23, 42);

        Color cardColor =
                new Color(30, 41, 59);

        Color fieldColor =
                new Color(17, 24, 39);

        Color green =
                new Color(16, 185, 129);

        setLayout(new GridBagLayout());

        getContentPane().setBackground(background);

        JPanel card =
                new JPanel(new GridBagLayout());

        card.setPreferredSize(
                new Dimension(620, 660)
        );

        card.setBackground(cardColor);

        card.setBorder(
                new EmptyBorder(30, 35, 30, 35)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;

        gbc.gridy = 0;

        gbc.weightx = 1;

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(8, 0, 8, 0);

        // TITLE

        JLabel title =
                new JLabel(
                        "Receive Files",
                        SwingConstants.CENTER
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        32
                )
        );

        card.add(title, gbc);

        // SUBTITLE

        gbc.gridy++;

        JLabel subtitle =
                new JLabel(
                        "Receive files over local network",
                        SwingConstants.CENTER
                );

        subtitle.setForeground(
                new Color(148, 163, 184)
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        card.add(subtitle, gbc);

        // SYSTEM INFO

        try {

            String systemName =
                    InetAddress
                            .getLocalHost()
                            .getHostName();

            String ip =
                    InetAddress
                            .getLocalHost()
                            .getHostAddress();

            gbc.gridy++;

            JLabel systemLabel =
                    createSecondaryLabel(
                            "System: " + systemName
                    );

            systemLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            card.add(systemLabel, gbc);

            gbc.gridy++;

            JLabel ipLabel =
                    createSecondaryLabel(
                            "IP Address: " + ip
                    );

            ipLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            card.add(ipLabel, gbc);

        } catch (Exception e) {

            e.printStackTrace();
        }

        // START BUTTON

        gbc.gridy++;

        gbc.insets =
                new Insets(28, 0, 14, 0);

        JButton startButton =
                createButton(
                        "Start Receiver",
                        green
                );

        card.add(startButton, gbc);

        // STATUS

        gbc.gridy++;

        gbc.insets =
                new Insets(0, 0, 18, 0);

        statusLabel =
                createSecondaryLabel(
                        "Receiver Offline"
                );

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        card.add(statusLabel, gbc);

        // PROGRESS BAR

        gbc.gridy++;

        progressBar =
                new JProgressBar();

        progressBar.setStringPainted(true);

        progressBar.setPreferredSize(
                new Dimension(500, 18)
        );

        card.add(progressBar, gbc);

        // LOG AREA

        gbc.gridy++;

        gbc.insets =
                new Insets(20, 0, 18, 0);

        logArea =
                new JTextArea();

        logArea.setEditable(false);

        logArea.setBackground(fieldColor);

        logArea.setForeground(Color.WHITE);

        logArea.setCaretColor(Color.WHITE);

        logArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        logArea.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(logArea);

        scrollPane.setPreferredSize(
                new Dimension(500, 260)
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(51, 65, 85)
                )
        );

        card.add(scrollPane, gbc);

        // BUTTON PANEL

        gbc.gridy++;

        gbc.insets =
                new Insets(12, 0, 0, 0);

        JButton backButton =
                createButton(
                        "Back",
                        new Color(71, 85, 105)
                );

        card.add(backButton, gbc);

        add(card);

        // RECEIVER LOG CALLBACK

        manager.setReceiverLogCallback(message -> {

            SwingUtilities.invokeLater(() -> {

                logArea.append(message + "\n");

                logArea.setCaretPosition(
                        logArea.getDocument().getLength()
                );
            });
        });

        // RECEIVER PROGRESS CALLBACK

        manager.setReceiverProgressCallback(progress -> {

            SwingUtilities.invokeLater(() -> {

                progressBar.setValue(progress);
            });
        });

        // START RECEIVER

        startButton.addActionListener(e -> {

            if (receiverStarted) {

                return;
            }

            receiverStarted = true;

            statusLabel.setText(
                    "Receiver Running"
            );

            logArea.append(
                    "Receiver started successfully...\n"
            );

            manager.startReceiver();
        });

        // BACK BUTTON

        backButton.addActionListener(e -> {

            manager.stopReceiver();

            dispose();

            new HomeScreen();
        });

        setVisible(true);
    }

    private JLabel createSecondaryLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                new Color(148, 163, 184)
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        return label;
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
                        15
                )
        );

        button.setPreferredSize(
                new Dimension(200, 42)
        );

        return button;
    }
}