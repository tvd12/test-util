package com.tvd12.test.util;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.function.Predicate;

import javax.net.ServerSocketFactory;

/**
 * 
 * Support for networking
 * 
 * @author tvd12
 *
 */

public class NetUtil {

    public static final int PORT_RANGE_MIN = 1024;
    public static final int PORT_RANGE_MAX = 65535;

    private static final Predicate<Integer> TCP_PORT_PREDICATE = (port) -> {
        try {
            ServerSocket serverSocket = ServerSocketFactory.getDefault()
                    .createServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            serverSocket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    };

    private static final Predicate<Integer> UDP_PORT_PREDICATE = (port) -> {
        try {
            DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    };

    private NetUtil() {
    }

    public static int findAvailableTcpPort() {
        return findAvailableTcpPort(PORT_RANGE_MIN, PORT_RANGE_MAX);
    }

    public static int findAvailableTcpPort(int minPort, int maxPort) {
        return findAvailablePort(TransportType.TCP, minPort, maxPort);
    }

    public static int findAvailableUdpPort() {
        return findAvailableUdpPort(PORT_RANGE_MIN, PORT_RANGE_MAX);
    }

    public static int findAvailableUdpPort(int minPort, int maxPort) {
        return findAvailablePort(TransportType.UDP, minPort, maxPort);
    }

    public static int findAvailablePort(TransportType transportType, int minPort, int maxPort) {
        int answer = (minPort + maxPort) / 2;
        if (transportType == TransportType.TCP) {
            for (int i = minPort; i < maxPort; ++i) {
                if (TCP_PORT_PREDICATE.test(i)) {
                    answer = i;
                    break;
                }
            }
        } else {
            for (int i = minPort; i < maxPort; ++i) {
                if (UDP_PORT_PREDICATE.test(i)) {
                    answer = i;
                    break;
                }
            }
        }
        return answer;
    }

    /**
     * The transport type
     * 
     * @author tvd12
     *
     */
    public static enum TransportType {
        TCP,
        UDP
    }
}
