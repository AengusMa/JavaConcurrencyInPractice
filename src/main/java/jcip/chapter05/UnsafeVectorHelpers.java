package jcip.chapter05;

import java.util.Vector;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单5-1：Vector上可能导致混乱结果的复合操作(同步容器类)
 */
public class UnsafeVectorHelpers {

  public static Object getLast(Vector list) {
    int lastIndex = list.size() - 1;
    return list.get(lastIndex);
  }

  public static void deleteLast(Vector list) {
    int lastIndex = list.size() - 1;
    list.remove(lastIndex);
  }
}
