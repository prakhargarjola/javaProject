package utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    public static List<String> discoverDevices() {

        List<String> devices = new ArrayList<>();

        try {

            String localIp =
                    InetAddress
                            .getLocalHost()
                            .getHostAddress();

            String subnet =
                    localIp.substring(
                            0,
                            localIp.lastIndexOf(".") + 1
                    );

            for (int i = 1; i < 255; i++) {

                String host = subnet + i;

                try {

                    InetAddress address =
                            InetAddress.getByName(host);

                    if (address.isReachable(200)) {

                        String hostName =
                                address.getHostName();

                        devices.add(
                                hostName + " (" + host + ")"
                        );
                    }

                } catch (Exception ignored) {

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return devices;
    }
}