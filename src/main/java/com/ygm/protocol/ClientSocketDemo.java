package com.ygm.protocol;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*
 *基于应用层socket通讯  tcp协议
* Tcp传输，客户端建立的过程。
* 1，创建tcp客户端socket服务。使用的是Socket对象。
* 建议该对象一创建就明确目的地。要连接的主机。
* 2，如果连接建立成功，说明数据传输通道已建立。
* 该通道就是socket流 ,是底层建立好的。 既然是流，说明这里既有输入，又有输出。
* 想要输入或者输出流对象，可以找Socket来获取。
* 可以通过getOutputStream(),和getInputStream()来获取两个字节流。
* 3，使用输出流，将数据写出。
* 4，关闭资源。
*/
public class ClientSocketDemo {
    public static  void singleClient() throws IOException {
        // 创建客户端socket服务
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1",8081);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println("simple tcp hello");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(socket != null){
                socket.close();
            }
        }
    }

    /**
     * 模拟聊天
     * @throws IOException
     */
    public static void halfDuplexClient() throws IOException {
        // 创建客户端socket服务
        Socket socket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        BufferedReader bufferedReader = null;
        try {
            socket = new Socket("127.0.0.1",8081);
            Scanner scanner = new Scanner(System.in);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

             dos = new DataOutputStream(outputStream);
             dis = new DataInputStream(inputStream);
             bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("输入账号:");
          //  String account = scanner.nextLine();
            dos.writeUTF(bufferedReader.readLine());
            System.out.println("输入密码:");
       //     String password = scanner.nextLine();
            dos.writeUTF(bufferedReader.readLine());
            String info = dis.readUTF();
            while(info.equals("输入错误")){
                System.out.println("输入有误，请重新输入！");
                System.out.println("输入账号:");
                dos.writeUTF(bufferedReader.readLine());
                System.out.println("输入密码");
                dos.writeUTF(bufferedReader.readLine());
                info=dis.readUTF();

            }
            System.out.println("已连接到主机");
            System.out.println("进入聊天模式");
            while (true){
                System.out.print("我: ");
                String message = bufferedReader.readLine();
                System.out.println();
                dos.writeUTF(message);
                if (message.equals("bye")){
                    break;
                }
                message = dis.readUTF();
                System.out.println("服务器说:"+message+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader != null){
                bufferedReader.close();
                bufferedReader = null;// 置空有利于垃圾回收
            }
            if(dis != null){
                dis.close();
                dis = null;
            }
            if(dos != null){
                dos.close();
                dos = null;
            }
            if(socket != null){
                socket.close();
            }
        }

    }

    public static void main(String[] args) throws IOException {

        // 单工客户端
        //singleClient();
        halfDuplexClient();


    }
}
