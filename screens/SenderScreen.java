package screens;

import utils.FakeData;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SenderScreen extends JFrame {

    private JTextField ipField;
    private JTextField portField;
    private JLabel fileLabel;
    private JLabel statusLabel;
    private File selectedFile;

    public SenderScreen() {

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

        JLabel title = new JLabel("Send File");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        mainPanel.add(new JLabel("Receiver IP:"), gbc);

        gbc.gridx = 1;

        ipField = new JTextField(20);
        mainPanel.add(ipField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(new JLabel("Port:"), gbc);

        gbc.gridx = 1;

        portField = new JTextField("5000");
        mainPanel.add(portField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(new JLabel("Nearby Devices:"), gbc);

        gbc.gridx = 1;

        JList<String> deviceList =
                new JList<>(FakeData.devices);

        JScrollPane scrollPane =
                new JScrollPane(deviceList);

        scrollPane.setPreferredSize(
                new Dimension(250, 80)
        );

        mainPanel.add(scrollPane, gbc);

        deviceList.addListSelectionListener(e -> {

            String selected =
                    deviceList.getSelectedValue();

            if (selected != null) {

                String ip = selected.substring(
                        selected.indexOf("(") + 1,
                        selected.indexOf(")")
                );

                ipField.setText(ip);
            }
        });

        gbc.gridx = 0;
        gbc.gridy++;

        JButton chooseButton =
                new JButton("Choose File");

        mainPanel.add(chooseButton, gbc);

        gbc.gridx = 1;

        fileLabel =
                new JLabel("No file selected");

        mainPanel.add(fileLabel, gbc);

        chooseButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            int result =
                    chooser.showOpenDialog(this);

            if (result ==
                    JFileChooser.APPROVE_OPTION) {

                selectedFile =
                        chooser.getSelectedFile();

                fileLabel.setText(
                        selectedFile.getName()
                );
            }
        });

        gbc.gridx = 0;
        gbc.gridy++;

        JButton sendButton =
                new JButton("Send File");

        mainPanel.add(sendButton, gbc);

        gbc.gridx = 1;

        JButton backButton =
                new JButton("Back");

        mainPanel.add(backButton, gbc);

        sendButton.addActionListener(e -> {

            if (selectedFile == null) {

                statusLabel.setText(
                        "Please select a file"
                );

                return;
            }

            statusLabel.setText(
                    "File Ready To Send"
            );
        });

        backButton.addActionListener(e -> {

            dispose();

            new HomeScreen();
        });

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        statusLabel =
                new JLabel("Waiting...");

        mainPanel.add(statusLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}