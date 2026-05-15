package network;

import utils.Constants;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class FileReceiver {

    private ServerSocket serverSocket;

    private Consumer<String> logCallback;

    private Consumer<Integer> progressCallback;

    public void setLogCallback(
            Consumer<String> logCallback
    ) {

        this.logCallback = logCallback;
    }

    public void setProgressCallback(
            Consumer<Integer> progressCallback
    ) {

        this.progressCallback = progressCallback;
    }

    private void log(String message) {

        System.out.println(message);

        if (logCallback != null) {

            logCallback.accept(message);
        }
    }

    public void startServer() {

        try {

            serverSocket =
                    new ServerSocket(Constants.PORT);

            log(
                    "Receiver started on port: "
                            + Constants.PORT
            );

            while (true) {

                Socket socket =
                        serverSocket.accept();

                log("Sender connected.");

                receiveFiles(socket);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void receiveFiles(Socket socket) {

        try {

            DataInputStream dataInputStream =
                    new DataInputStream(
                            socket.getInputStream()
                    );

            int fileCount =
                    dataInputStream.readInt();

            // CHOOSE SAVE FOLDER

            JFileChooser chooser =
                    new JFileChooser();

            chooser.setFileSelectionMode(
                    JFileChooser.DIRECTORIES_ONLY
            );

            chooser.setDialogTitle(
                    "Choose Save Folder"
            );

            int result =
                    chooser.showSaveDialog(null);

            if (result !=
                    JFileChooser.APPROVE_OPTION) {

                socket.close();

                return;
            }

            File saveFolder =
                    chooser.getSelectedFile();

            byte[] buffer =
                    new byte[Constants.BUFFER_SIZE];

            long totalReceived = 0;

            long totalSize = 0;

            // FIRST READ TOTAL SIZE

            long[] fileSizes =
                    new long[fileCount];

            String[] fileNames =
                    new String[fileCount];

            for (int i = 0; i < fileCount; i++) {

                fileNames[i] =
                        dataInputStream.readUTF();

                fileSizes[i] =
                        dataInputStream.readLong();

                totalSize += fileSizes[i];
            }

            // RECEIVE FILES

            for (int i = 0; i < fileCount; i++) {

                String fileName =
                        fileNames[i];

                long fileSize =
                        fileSizes[i];

                File outputFile =
                        new File(
                                saveFolder,
                                fileName
                        );

                FileOutputStream fileOutputStream =
                        new FileOutputStream(outputFile);

                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(
                                fileOutputStream
                        );

                long currentFileBytes = 0;

                int bytesRead;

                while (currentFileBytes < fileSize &&
                        (bytesRead =
                                dataInputStream.read(
                                        buffer,
                                        0,
                                        (int) Math.min(
                                                buffer.length,
                                                fileSize - currentFileBytes
                                        )
                                )) != -1) {

                    bufferedOutputStream.write(
                            buffer,
                            0,
                            bytesRead
                    );

                    currentFileBytes += bytesRead;

                    totalReceived += bytesRead;

                    int progress =
                            (int) (
                                    (totalReceived * 100)
                                            / totalSize
                            );

                    if (progressCallback != null) {

                        progressCallback.accept(progress);
                    }
                }

                bufferedOutputStream.flush();

                bufferedOutputStream.close();

                fileOutputStream.close();

                log(
                        "Received: "
                                + fileName
                );
            }

            dataInputStream.close();

            socket.close();

            log(
                    "All files received successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void stopServer() {

        try {

            if (serverSocket != null &&
                    !serverSocket.isClosed()) {

                serverSocket.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}