package jcip.chapter07;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import jcip.annotations.GuardedBy;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序7-12：通过newTaskFor将非标准的取消操作封装在一个任务中
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T> {

  @GuardedBy("this")
  private Socket socket;

  protected synchronized void setSocket(Socket s) {
    socket = s;
  }

  @Override
  public synchronized void cancel() {
    try {
      if (socket != null) {
        socket.close();
      }
    } catch (IOException ignored) {

    }
  }

  @Override
  public RunnableFuture<T> newTask() {
    return new FutureTask<T>(this) {
      @Override
      public boolean cancel(boolean mayInterruptIfRunning) {
        try {
          SocketUsingTask.this.cancel();
        } finally {
          return super.cancel(mayInterruptIfRunning);
        }
      }
    };
  }
}

interface CancellableTask<T> extends Callable<T> {

  void cancel();

  RunnableFuture<T> newTask();
}