package jcip.chapter05;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-19：最终实现
 */
public class Memoizer<A, V> implements Computable<A, V> {

  private final ConcurrentMap<A, Future<V>> cache
      = new ConcurrentHashMap<A, Future<V>>();
  private final Computable<A, V> c;

  public Memoizer(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public V compute(final A arg) throws InterruptedException {
    while (true) {
      Future<V> f = cache.get(arg);
      if (f == null) {
        Callable<V> eval = new Callable<V>() {
          @Override
          public V call() throws InterruptedException {
            return c.compute(arg);
          }
        };
        FutureTask<V> ft = new FutureTask<>(eval);
        f = cache.putIfAbsent(arg, ft);
        if (f == null) {
          f = ft;
          ft.run();
        }
      }
      try {
        return f.get();
      } catch (CancellationException e) {
        cache.remove(arg, f);
      } catch (ExecutionException e) {
        throw LaunderThrowable.launderThrowable(e.getCause());
      }
    }
  }
}
