package com.ygm.protocol;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 基于应用层socket通讯  tcp协议
 * 建立tcp服务端的思路：
 * 1，创建服务端socket服务。通过ServerSocket对象。
 * 2，服务端必须对外提供一个端口，否则客户端无法连接。
 * 3，获取连接过来的客户端对象。
 * 4，通过客户端对象获取socket流读取客户端发来的数据
 * 并打印在控制台上。或者可以输入流存在缓存区；
 * 5，关闭资源。关客户端，关服务端。
*/

public class ServerSocketDemo {
    public static void singleServer() throws IOException {
        // 创建服务端对象
        ServerSocket serverSocket = null;
        // 获取客户端连接对象
        Socket socket = null;
        // 创建缓存区对象
        BufferedReader bufferedReader = null;
        try {
            serverSocket = new ServerSocket(8081);
            socket = serverSocket.accept();
            String ipA = socket.getInetAddress().getHostAddress();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("客户端IP:"+ipA+",服务端收到信息："+bufferedReader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader != null){
                bufferedReader.close();
                bufferedReader = null;// 置空有利于垃圾回收
            }
            if(socket != null){
                socket.close();
                socket = null;
            }
            if(serverSocket != null){
                serverSocket.close();
                serverSocket = null;
            }

        }
    }

    /**
     * 模拟聊天
     * @throws IOException
     */
    public static void halfDuplexServer() throws IOException {
        // 创建服务端对象
        ServerSocket serverSocket = null;
        // 获取客户端连接对象
        Socket socket = null;
        // 创建缓存区对象
        BufferedReader bufferedReader = null;
        // 获取数据输入流
        DataInputStream dis = null;
        // 获取数据输出流
        DataOutputStream dos = null;

        try {
            serverSocket = new ServerSocket(8081);
            socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputstream = socket.getOutputStream();

            dis = new DataInputStream(inputStream);
            dos = new DataOutputStream(outputstream);

            bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String account = dis.readUTF();
            String password = dis.readUTF();
            System.out.println("账号:"+account+",密码:"+password);
            while(!account.equals("ygm") || !password.equals("123")){
                System.out.println("输入错误");
                dos.writeUTF("输入错误");
                account = dis.readUTF();
                password = dis.readUTF();
            }
            dos.writeUTF("输入正确");
            System.out.println("验证通过");
            while(true){
                String info = dis.readUTF();
                System.out.println("客户端说:"+info+"\n");
                if(info.equals("bye"))
                    break;
                System.out.print("我:");
                dos.writeUTF(bufferedReader.readLine());
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(bufferedReader != null){
                bufferedReader.close();
                bufferedReader = null;// 置空有利于垃圾回收
            }
            if(dos != null){
                dos.close();
                dos = null;
            }
            if(dis != null){
                dis.close();
                dis = null;
            }
            if(socket != null){
                socket.close();
                socket = null;
            }
            if(serverSocket != null){
                serverSocket.close();
                serverSocket = null;
            }
        }
    }

    public static void main( String[] args ) throws IOException {
        // 单服务
        //singleServer();
        halfDuplexServer();


    }
}
