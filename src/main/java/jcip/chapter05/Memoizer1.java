package jcip.chapter05;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import jcip.annotations.GuardedBy;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-16：使用HashMap和同步机制来初始化缓存。缺点：计算时间长时线程阻塞
 */
public class Memoizer1<A, V> implements Computable<A, V> {

  @GuardedBy("this")
  private final Map<A, V> cache = new HashMap<>();
  private final Computable<A, V> c;

  public Memoizer1(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public synchronized V compute(A arg) throws InterruptedException {
    V result = cache.get(arg);
    if (result == null) {
      result = c.compute(arg);
      cache.put(arg, result);
    }
    return result;
  }
}

interface Computable<A, V> {

  V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {

  @Override
  public BigInteger compute(String arg) {
    // 经过长时间的计算
    return new BigInteger(arg);
  }
}