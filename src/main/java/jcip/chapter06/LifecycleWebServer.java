package jcip.chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序6-8：支持关闭操作的Web服务器
 */
public class LifecycleWebServer {

  /**
   * ExecutorService中有生命周期的管理方法
   */
  private final ExecutorService exec = Executors.newCachedThreadPool();

  public void start() throws IOException {
    ServerSocket socket = new ServerSocket(8080);
    while (!exec.isShutdown()) {
      try {
        final Socket conn = socket.accept();
        exec.execute(new Runnable() {
          @Override
          public void run() {
            handleRequest(conn);
          }
        });
      } catch (RejectedExecutionException e) {
        if (!exec.isShutdown()) {
          log("task submission rejected", e);
        }
      }
    }
  }

  public void stop() {
    exec.shutdown();
  }

  private void log(String msg, Exception e) {
    Logger.getAnonymousLogger().log(Level.WARNING, msg, e);
  }

  private void handleRequest(Socket connection) {
    Request req = readRequest(connection);
    if (isShutdownRequest(req)) {
      stop();
    } else {
      dispatchRequest(req);
    }
  }

  interface Request {

  }

  private Request readRequest(Socket s) {
    return null;
  }

  private void dispatchRequest(Request r) {
  }

  private boolean isShutdownRequest(Request r) {
    return false;
  }
}
