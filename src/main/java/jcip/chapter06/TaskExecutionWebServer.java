package jcip.chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-4：基于线程池的Web服务器
 */
public class TaskExecutionWebServer {

  private static final int NTHREADS = 100;
  private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

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
      exec.execute(task);
    }
  }

  private static void handleRequest(Socket connection) {
//    处理请求
    System.out.println("处理请求：" + connection);
  }
}
