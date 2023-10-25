package com.okay.testcenter.tools;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.*;
import java.net.SocketException;
/**
 * @author zhou
 * @date 2021/3/17
 */
public class TelnetConnect {


    public static void main(String[] args){

        try {

            TelnetClient telnetClient = new TelnetClient("vt200");  //指明Telnet终端类型，否则会返回来的数据中文会乱码
            telnetClient.setDefaultTimeout(5000); //socket延迟时间：5000ms
//            telnetClient.connect("10.60.0.64", 25003);  //建立一个连接,默认端口是23
            telnetClient.connect("10.60.192.111", 23);  //建立一个连接,默认端口是23
            InputStream inputStream = telnetClient.getInputStream(); //读取命令的流
            PrintStream pStream = new PrintStream(telnetClient.getOutputStream());  //写命令的流
            byte[] b = new byte[1024];
            int size;
            StringBuffer sBuffer = new StringBuffer(300);
            while(true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
                size = inputStream.read(b);
                if(-1 != size) {
                    sBuffer.append(new String(b,0,size));
                    if(sBuffer.toString().trim().endsWith("login:")) {
                        break;
                    }
                }
            }
            System.out.println(sBuffer.toString());
            pStream.println("ls -l com.noriental.ksu.service.KsuService"); //写命令
            pStream.flush(); //将命令发送到telnet Server
            if(null != pStream) {
                pStream.close();
            }
            telnetClient.disconnect();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
