package jcip.chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-1：串行的Web服务器
 */
public class SingleThreadWebServer {

  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(8080);
    while (true) {
      Socket connection = socket.accept();
      handleRequest(connection);
    }
  }

  private static void handleRequest(Socket connection) {
//    处理请求
    System.out.println("处理请求："+connection);
  }
}
