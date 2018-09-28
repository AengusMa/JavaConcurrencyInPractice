package jcip.chapter05;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-14：使用Semaphore为容器设置边界（信号量）
 */
public class BoundedHashSet<T> {

  private final Set<T> set;
  //  信号量
  private final Semaphore sem;

  public BoundedHashSet(int bound) {
    this.set = Collections.synchronizedSet(new HashSet<>());
    sem = new Semaphore(bound);
  }

  public boolean add(T o) throws InterruptedException {
    sem.acquire();
    boolean wasAdded = false;
    try {
      wasAdded = set.add(o);
      return wasAdded;
    } finally {
//      如果未添加释放许可
      if (!wasAdded) {
        sem.release();
      }
    }
  }

  public boolean remove(Object o) {
    boolean wasRemoved = set.remove(o);
    if (wasRemoved) {
      sem.release();
    }
    return wasRemoved;
  }

}
