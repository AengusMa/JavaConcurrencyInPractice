package jcip.chapter05;

import java.util.Vector;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单5-2：在使用客户端加锁的Vector上的复合操作
 */
public class SafeVectorHelpers {

  public static Object getLast(Vector list) {
    synchronized (list) {
      int lastIndex = list.size() - 1;
      return list.get(lastIndex);
    }
  }

  public static void deleteLast(Vector list) {
    synchronized (list) {
      int lastIndex = list.size() - 1;
      list.remove(lastIndex);
    }

  }
}
