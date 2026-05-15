package network;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class FileTransferManager {

    private final FileReceiver receiver;

    public FileTransferManager() {

        receiver = new FileReceiver();
    }

    public void setReceiverLogCallback(
            Consumer<String> callback
    ) {

        receiver.setLogCallback(callback);
    }

    public void setReceiverProgressCallback(
            Consumer<Integer> callback
    ) {

        receiver.setProgressCallback(callback);
    }

    public void startReceiver() {

        new Thread(() -> {

            receiver.startServer();

        }).start();
    }

    public boolean sendFiles(
            String ipAddress,
            List<File> files,
            Consumer<Integer> progressCallback
    ) {

        return FileSender.sendFiles(
                ipAddress,
                files,
                progressCallback
        );
    }

    public void stopReceiver() {

        receiver.stopServer();
    }
}