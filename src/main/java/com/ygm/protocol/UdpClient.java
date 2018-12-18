package com.ygm.protocol;

import java.net.*;

/**
 * 实现基于upd协议
 * Created by admin on 2018/12/18.
 */
public class UdpClient {
    public static void myUdpClient() throws SocketException {
        // 定义服务端地址
        InetAddress address = null;
        DatagramSocket datagramSocket = null;
        try {
            address = InetAddress.getByName("127.0.0.1");
            byte[] sendData = "hello,我是udp客户端".getBytes();
            // 创建数据报，并包含发送的数据信息
            DatagramPacket datagramPacket = new DatagramPacket(sendData,sendData.length,address,8081);
            datagramSocket = new DatagramSocket();
            // 向服务端发送数据
            datagramSocket.send(datagramPacket);

            // 接收服务端响应的数据
            // 创建数据报，用于接收服务器端响应数据
            byte[] data = new byte[1024];
            DatagramPacket datagramPacket2 = new DatagramPacket(data,data.length);
            // 接收服务器端响应的数据
            datagramSocket.receive(datagramPacket2);
            // 读取数据
            String read = new String(data,0,datagramPacket2.getLength());
            System.out.println("我是udp客户端，服务端说："+ read);


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {
            if(datagramSocket != null){
                datagramSocket.close();
                datagramSocket = null;
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        // udp协议
        myUdpClient();
    }
}
