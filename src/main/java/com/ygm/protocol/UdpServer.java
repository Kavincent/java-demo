package com.ygm.protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 实现基于udp协议
 * Created by admin on 2018/12/17.
 */
public class UdpServer {
    public static void myUdpServer() throws IOException {
        // 创建服务端,并指定端口
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(8081);
            // 创建数据报,用于接收客户端发送的数据
            byte[] data = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(data,data.length);
            // 接收客户端发送的数据
            // 此方法在接收数据报之前会一直阻塞
            datagramSocket.receive(datagramPacket);
            String info = new String(data,0,datagramPacket.getLength());
            System.out.println("服务端收到客户端的信息为:"+ info);

            // 向客户端响应数据
            // 获取客户端的地址，端口号，数据
            InetAddress address = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            byte[] data2 = "hello,欢迎您!".getBytes();
            // 创建数据报,用于响应数据信息
            DatagramPacket datagramPacket2 = new DatagramPacket(data2,data2.length,address,port);
            // 响应客户端
            datagramSocket.send(datagramPacket2);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(datagramSocket != null){
                datagramSocket.close();
                datagramSocket = null;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        myUdpServer();
    }
}
