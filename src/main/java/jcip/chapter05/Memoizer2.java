package jcip.chapter05;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-17：用ConcurrentHashMap替换HashMap。缺点：可能会重复计算
 */
public class Memoizer2<A, V> implements Computable<A, V> {

  private final Map<A, V> cache = new ConcurrentHashMap<>();
  private final Computable<A, V> c;

  public Memoizer2(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public V compute(A arg) throws InterruptedException {
    V result = cache.get(arg);
    if (result == null) {
      result = c.compute(arg);
      cache.put(arg, result);
    }
    return result;
  }
}
