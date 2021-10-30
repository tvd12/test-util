package com.tvd12.test.testing.util;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.NetUtil;
import com.tvd12.test.util.RandomUtil;

public class NetUtilTest {

    @SuppressWarnings("resource")
    @Test
    public void findAvailableTcpPortTest() throws Exception {
        // given
        int numberOfPort = RandomUtil.randomInt(3, 8);
        List<Integer> ports = new ArrayList<>();
        
        // when
        ServerSocket[] serverSockets = new ServerSocket[numberOfPort];
        InetAddress inetAddress= InetAddress.getByName("127.0.0.1");  
        for(int i = 0 ; i < numberOfPort ; i++) {
            int port = NetUtil.findAvailableTcpPort();
            ports.add(port);
            ServerSocket serverSocket = new ServerSocket();  
            SocketAddress endPoint = new InetSocketAddress(inetAddress, port);  
            Thread.sleep(100);
            try {
                serverSocket.bind(endPoint);  
            }
            catch (Exception e) {
            }
            serverSockets[i] = serverSocket;
        }
        
        // then
        System.out.println(ports);
        Asserts.assertEquals(numberOfPort, ports.size());
        for(ServerSocket serverSocket : serverSockets) {
            serverSocket.close();
        }
    }
    
    @SuppressWarnings("resource")
    @Test
    public void findAvailableTcpPortMinMaxTest() throws Exception {
        // given
        int numberOfPort = 5;
        int minPort = NetUtil.PORT_RANGE_MIN;
        int maxPort = minPort + 3;
        List<Integer> ports = new ArrayList<>();
        
        // when
        ServerSocket[] serverSockets = new ServerSocket[numberOfPort];
        InetAddress inetAddress= InetAddress.getByName("127.0.0.1");  
        for(int i = 0 ; i < numberOfPort ; i++) {
            int port = NetUtil.findAvailableTcpPort(minPort, maxPort);
            ports.add(port);
            ServerSocket serverSocket = new ServerSocket();  
            SocketAddress endPoint = new InetSocketAddress(inetAddress, port);  
            Thread.sleep(100);
            try {
                serverSocket.bind(endPoint);  
            }
            catch (Exception e) {
            }
            serverSockets[i] = serverSocket;
        }
        
        // then
        System.out.println(ports);
        Asserts.assertEquals(numberOfPort, ports.size());
        for(ServerSocket serverSocket : serverSockets) {
            serverSocket.close();
        }
    }
    
    @SuppressWarnings("resource")
    @Test
    public void findAvailableUdpPortTest() throws Exception {
        // given
        int numberOfPort = RandomUtil.randomInt(3, 8);
        List<Integer> ports = new ArrayList<>();
        
        // when
        DatagramSocket[] datagramSockets = new DatagramSocket[numberOfPort];
        InetAddress inetAddress= InetAddress.getByName("127.0.0.1");  
        for(int i = 0 ; i < numberOfPort ; i++) {
            int port = NetUtil.findAvailableUdpPort();
            ports.add(port);
            DatagramSocket datagramSocket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(inetAddress, port);
            try {
                datagramSocket.bind(address);
            }
            catch (Exception e) {
            }
            datagramSockets[i] = datagramSocket;
        }
        
        // then
        System.out.println(ports);
        Asserts.assertEquals(numberOfPort, ports.size());
        for(DatagramSocket datagramSocket : datagramSockets) {
            datagramSocket.close();
        }
    }
    
    @SuppressWarnings("resource")
    @Test
    public void findAvailableUdpPortMinMaxTest() throws Exception {
        // given
        int numberOfPort = 5;
        int minPort = NetUtil.PORT_RANGE_MIN;
        int maxPort = minPort + 3;
        List<Integer> ports = new ArrayList<>();
        
        // when
        DatagramSocket[] datagramSockets = new DatagramSocket[numberOfPort];
        InetAddress inetAddress= InetAddress.getByName("127.0.0.1");  
        for(int i = 0 ; i < numberOfPort ; i++) {
            int port = NetUtil.findAvailableUdpPort(minPort, maxPort);
            ports.add(port);
            DatagramSocket datagramSocket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(inetAddress, port);
            try {
                datagramSocket.bind(address);
            }
            catch (Exception e) {
            }
            datagramSockets[i] = datagramSocket;
        }
        
        // then
        System.out.println(ports);
        Asserts.assertEquals(numberOfPort, ports.size());
        for(DatagramSocket datagramSocket : datagramSockets) {
            datagramSocket.close();
        }
    }
}
