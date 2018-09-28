package jcip.chapter05;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-18：基于FutureTask的Memoizing封装器。缺点：仍然存在两个线程重复计算的漏洞
 */
public class Memoizer3<A, V> implements Computable<A, V> {

  private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
  private final Computable<A, V> c;

  public Memoizer3(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public V compute(final A arg) throws InterruptedException {
    Future<V> f = cache.get(arg);
    if (f == null) {
      Callable<V> eval = new Callable<V>() {
        @Override
        public V call() throws InterruptedException {
          return c.compute(arg);
        }
      };
      FutureTask<V> ft = new FutureTask<>(eval);
      f = ft;
      cache.put(arg, ft);
      ft.run();
    }
    try {
      return f.get();
    } catch (ExecutionException e) {
      throw LaunderThrowable.launderThrowable(e.getCause());
    }
  }
}
