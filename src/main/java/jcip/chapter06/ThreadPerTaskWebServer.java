package jcip.chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-2：在Web服务器中为每个请求启动一个新的线程（不要这么做）
 */
public class ThreadPerTaskWebServer {

  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(8080);
    while (true) {
      final Socket connection = socket.accept();
      Runnable task = new Runnable() {
        @Override
        public void run() {
          handleRequest(connection);
        }
      };
      new Thread(task).start();
    }
  }

  private static void handleRequest(Socket connection) {
//    处理请求
    System.out.println("处理请求：" + connection);
  }
}
