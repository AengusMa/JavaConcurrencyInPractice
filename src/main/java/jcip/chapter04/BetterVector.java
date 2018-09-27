package jcip.chapter04;

import java.util.Vector;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单4-13：扩展Vector并添加一个“若没有则添加”方法
 */
@ThreadSafe
public class BetterVector<E> extends Vector<E> {

  public synchronized boolean putIfAbsent(E x) {
    boolean absent = !contains(x);
    if (absent) {
      add(x);
    }
    return absent;
  }
}
