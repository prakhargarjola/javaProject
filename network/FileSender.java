package network;

import utils.Constants;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.function.Consumer;

public class FileSender {

    public static boolean sendFiles(
            String ipAddress,
            List<File> files,
            Consumer<Integer> progressCallback
    ) {

        try {

            Socket socket =
                    new Socket(
                            ipAddress,
                            Constants.PORT
                    );

            DataOutputStream dataOutputStream =
                    new DataOutputStream(
                            socket.getOutputStream()
                    );

            // SEND FILE COUNT

            dataOutputStream.writeInt(
                    files.size()
            );

            long totalSize = 0;

            for (File file : files) {

                totalSize += file.length();
            }

            long totalSent = 0;

            byte[] buffer =
                    new byte[Constants.BUFFER_SIZE];

            for (File file : files) {

                // FILE NAME

                dataOutputStream.writeUTF(
                        file.getName()
                );

                // FILE SIZE

                dataOutputStream.writeLong(
                        file.length()
                );

                FileInputStream fileInputStream =
                        new FileInputStream(file);

                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(fileInputStream);

                int bytesRead;

                while ((bytesRead =
                        bufferedInputStream.read(buffer))
                        != -1) {

                    dataOutputStream.write(
                            buffer,
                            0,
                            bytesRead
                    );

                    totalSent += bytesRead;

                    int progress =
                            (int) (
                                    (totalSent * 100)
                                            / totalSize
                            );

                    if (progressCallback != null) {

                        progressCallback.accept(progress);
                    }
                }

                bufferedInputStream.close();

                fileInputStream.close();
            }

            dataOutputStream.flush();

            dataOutputStream.close();

            socket.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}