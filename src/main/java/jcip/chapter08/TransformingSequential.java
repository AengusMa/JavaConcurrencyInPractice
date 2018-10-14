package jcip.chapter08;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mawenlong
 * @date 2018/10/14
 */
public abstract class TransformingSequential {

  /**
   * 程序清单8-10：将串行执行转换为并行执行
   */
  void processSequentially(List<Element> elements) {
    for (Element e : elements) {
      process(e);
    }
  }

  void processInParallel(Executor exec, List<Element> elements) {
    for (final Element e : elements) {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          process(e);
        }
      });
    }
  }

  public abstract void process(Element e);

  /**
   * 程序清单8-11：将串行递归转换为并行递归
   */
  public <T> void sequentialRecursive(List<Node<T>> nodes,
      Collection<T> results) {
    for (Node<T> n : nodes) {
      results.add(n.compute());
      sequentialRecursive(n.getChildren(), results);
    }
  }

  public <T> void parallelRecursive(final Executor exec,
      List<Node<T>> nodes,
      final Collection<T> results) {
    for (final Node<T> n : nodes) {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          results.add(n.compute());
        }
      });
      parallelRecursive(exec, n.getChildren(), results);
    }
  }

  /**
   * 程序清单8-12：等待通过并行方式计算的结果
   */
  public <T> Collection<T> getParallelResults(List<Node<T>> nodes)
      throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
    parallelRecursive(exec, nodes, resultQueue);
    exec.shutdown();
    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    return resultQueue;
  }

  interface Element {

  }

  interface Node<T> {

    T compute();

    List<Node<T>> getChildren();
  }
}
