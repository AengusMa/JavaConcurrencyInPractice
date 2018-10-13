package jcip.chapter08;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mawenlong
 * @date 2018/10/12
 *
 * 程序清单8-1：在单线程Executor中任务发生死锁（不要这么做）
 */
public class ThreadDeadlock {

  ExecutorService exec = Executors.newSingleThreadExecutor();

  public class LoadFileTask implements Callable<String> {

    private final String fileName;

    public LoadFileTask(String fileName) {
      this.fileName = fileName;
    }

    @Override
    public String call() throws Exception {
//      读取文件
      return "";
    }
  }

  public class RenderPageTask implements Callable<String> {

    @Override
    public String call() throws Exception {
      Future<String> header, footer;
      header = exec.submit(new LoadFileTask("header.html"));
      footer = exec.submit(new LoadFileTask("footer.html"));
      String page = renderBody();
      /**
       * 子任务可能没有机会运行
       *  Will deadlock -- task waiting for result of subtask
       */
      return header.get() + page + footer.get();
    }

    public String renderBody() {
//      绘制网页
      return "";
    }
  }


}
