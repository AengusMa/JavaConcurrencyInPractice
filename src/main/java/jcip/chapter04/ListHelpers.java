package jcip.chapter04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jcip.annotations.NotThreadSafe;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/27
 */

/**
 * 程序清单4-14：非线程安全的“若没有则添加”（不要这么做）。在错误的锁上进行了同步。
 */
@NotThreadSafe
class BadListHelper<E> {

  public List<E> list = Collections.synchronizedList(new ArrayList<>());

  public synchronized boolean putIfAbsent(E x) {
    boolean absent = !list.contains(x);
    if (absent) {
      list.add(x);
    }
    return absent;
  }
}

/**
 * 程序清单4-15：通过客户端加锁来实现“若没有则添加”
 */
@ThreadSafe
class GoodListHelper<E> {

  public List<E> list = Collections.synchronizedList(new ArrayList<>());

  public boolean puIfAbsent(E x) {
    synchronized (list) {
      boolean absent = !list.contains(x);
      if (absent) {
        list.add(x);
      }
      return absent;
    }
  }
}
