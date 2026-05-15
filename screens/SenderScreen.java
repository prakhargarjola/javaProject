package screens;

import network.FileTransferManager;
import utils.NetworkUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.net.InetAddress;
import java.util.List;

public class SenderScreen extends JFrame {

    private JTextField ipField;

    private JLabel fileLabel;

    private JLabel statusLabel;

    private JProgressBar progressBar;

    private JPanel dropPanel;

    private File[] selectedFiles;

    private final FileTransferManager manager;

    private DefaultListModel<String> deviceModel;

    public SenderScreen() {

        manager = new FileTransferManager();

        setTitle("Send Files");

        setSize(900, 760);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color background =
                new Color(15, 23, 42);

        Color cardColor =
                new Color(30, 41, 59);

        Color fieldColor =
                new Color(17, 24, 39);

        Color blue =
                new Color(59, 130, 246);

        Color green =
                new Color(16, 185, 129);

        setLayout(new GridBagLayout());

        getContentPane().setBackground(background);

        JPanel card =
                new JPanel(new GridBagLayout());

        card.setPreferredSize(
                new Dimension(620, 670)
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

        JLabel title =
                new JLabel(
                        "Send Files",
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

        gbc.gridy++;

        JLabel subtitle =
                new JLabel(
                        "Fast local network transfer",
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
                            "Your System: " + systemName
                    );

            systemLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            card.add(systemLabel, gbc);

            gbc.gridy++;

            JLabel ipInfo =
                    createSecondaryLabel(
                            "Your IP: " + ip
                    );

            ipInfo.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            card.add(ipInfo, gbc);

        } catch (Exception e) {

            e.printStackTrace();
        }

        gbc.gridy++;

        gbc.insets =
                new Insets(25, 0, 6, 0);

        JLabel ipLabel =
                createLabel("Receiver IP");

        card.add(ipLabel, gbc);

        gbc.gridy++;

        gbc.insets =
                new Insets(0, 0, 10, 0);

        ipField =
                new JTextField();

        styleField(ipField, fieldColor);

        card.add(ipField, gbc);

        gbc.gridy++;

        JButton scanButton =
                createButton(
                        "Scan Devices",
                        blue
                );

        card.add(scanButton, gbc);

        gbc.gridy++;

        gbc.insets =
                new Insets(12, 0, 18, 0);

        deviceModel =
                new DefaultListModel<>();

        JList<String> deviceList =
                new JList<>(deviceModel);

        deviceList.setBackground(fieldColor);

        deviceList.setForeground(Color.WHITE);

        deviceList.setFixedCellHeight(30);

        JScrollPane scrollPane =
                new JScrollPane(deviceList);

        scrollPane.setPreferredSize(
                new Dimension(500, 120)
        );

        card.add(scrollPane, gbc);

        gbc.gridy++;

        dropPanel =
                new JPanel(
                        new GridBagLayout()
                );

        dropPanel.setBackground(fieldColor);

        dropPanel.setPreferredSize(
                new Dimension(500, 100)
        );

        JLabel dropLabel =
                new JLabel(
                        "Drag & Drop Files Here"
                );

        dropLabel.setForeground(
                new Color(148, 163, 184)
        );

        dropPanel.add(dropLabel);

        setupDragAndDrop();

        card.add(dropPanel, gbc);

        gbc.gridy++;

        gbc.insets =
                new Insets(18, 0, 10, 0);

        JButton chooseButton =
                createButton(
                        "Choose Files",
                        green
                );

        card.add(chooseButton, gbc);

        gbc.gridy++;

        fileLabel =
                createSecondaryLabel(
                        "No files selected"
                );

        card.add(fileLabel, gbc);

        gbc.gridy++;

        progressBar =
                new JProgressBar();

        progressBar.setStringPainted(true);

        card.add(progressBar, gbc);

        gbc.gridy++;

        gbc.insets =
                new Insets(22, 0, 10, 0);

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(1, 2, 12, 0)
                );

        buttonPanel.setOpaque(false);

        JButton sendButton =
                createButton(
                        "Send Files",
                        blue
                );

        JButton backButton =
                createButton(
                        "Back",
                        new Color(71, 85, 105)
                );

        buttonPanel.add(sendButton);

        buttonPanel.add(backButton);

        card.add(buttonPanel, gbc);

        gbc.gridy++;

        statusLabel =
                createSecondaryLabel(
                        "Waiting..."
                );

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        card.add(statusLabel, gbc);

        add(card);

        deviceList.addListSelectionListener(e -> {

            String selected =
                    deviceList.getSelectedValue();

            if (selected != null) {

                String ip =
                        selected.substring(
                                selected.indexOf("(") + 1,
                                selected.indexOf(")")
                        );

                ipField.setText(ip);
            }
        });

        scanButton.addActionListener(e -> {

            deviceModel.clear();

            statusLabel.setText(
                    "Scanning devices..."
            );

            new Thread(() -> {

                List<String> devices =
                        NetworkUtils.discoverDevices();

                SwingUtilities.invokeLater(() -> {

                    if (devices.isEmpty()) {

                        deviceModel.addElement(
                                "No devices found"
                        );
                    }

                    for (String device : devices) {

                        deviceModel.addElement(device);
                    }

                    statusLabel.setText(
                            devices.size()
                                    + " devices found"
                    );
                });

            }).start();
        });

        chooseButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            chooser.setMultiSelectionEnabled(true);

            int result =
                    chooser.showOpenDialog(this);

            if (result ==
                    JFileChooser.APPROVE_OPTION) {

                selectedFiles =
                        chooser.getSelectedFiles();

                fileLabel.setText(
                        selectedFiles.length
                                + " files selected"
                );
            }
        });

        sendButton.addActionListener(e -> {

            String ip =
                    ipField.getText().trim();

            if (selectedFiles == null ||
                    selectedFiles.length == 0) {

                statusLabel.setText(
                        "Choose files first"
                );

                return;
            }

            progressBar.setValue(0);

            statusLabel.setText(
                    "Sending..."
            );

            new Thread(() -> {

                boolean success =
                        manager.sendFiles(
                                ip,
                                java.util.Arrays.asList(
                                        selectedFiles
                                ),
                                progress -> {

                                    SwingUtilities.invokeLater(() -> {

                                        progressBar.setValue(progress);
                                    });
                                }
                        );

                SwingUtilities.invokeLater(() -> {

                    statusLabel.setText(
                            success
                                    ? "Transfer Complete"
                                    : "Transfer Failed"
                    );
                });

            }).start();
        });

        backButton.addActionListener(e -> {

            dispose();

            new HomeScreen();
        });

        setVisible(true);
    }

    private void setupDragAndDrop() {

        dropPanel.setTransferHandler(
                new TransferHandler() {

                    @Override
                    public boolean canImport(
                            TransferSupport support
                    ) {

                        return support.isDataFlavorSupported(
                                DataFlavor.javaFileListFlavor
                        );
                    }

                    @Override
                    public boolean importData(
                            TransferSupport support
                    ) {

                        try {

                            @SuppressWarnings("unchecked")

                            List<File> droppedFiles =
                                    (List<File>) support
                                            .getTransferable()
                                            .getTransferData(
                                                    DataFlavor.javaFileListFlavor
                                            );

                            selectedFiles =
                                    droppedFiles.toArray(
                                            new File[0]
                                    );

                            fileLabel.setText(
                                    selectedFiles.length
                                            + " files selected"
                            );

                            return true;

                        } catch (Exception e) {

                            e.printStackTrace();

                            return false;
                        }
                    }
                }
        );
    }

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setForeground(Color.WHITE);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        return label;
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

    private void styleField(
            JTextField field,
            Color bg
    ) {

        field.setBackground(bg);

        field.setForeground(Color.WHITE);

        field.setCaretColor(Color.WHITE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(51, 65, 85)
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(500, 42)
        );
    }
}