package jcip.chapter05;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import jcip.annotations.GuardedBy;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单5-6：隐藏在字符串连接中的迭代操作（不要这么做）
 */
public class HiddenIterator {

  @GuardedBy("this")
  private final Set<Integer> set = new HashSet<>();

  public synchronized void add(Integer i) {
    set.add(i);
  }

  public synchronized void remove(Integer i) {
    set.remove(i);
  }

  public void addTenThings() {
    Random r = new Random();
    for (int i = 0; i < 10; i++) {
      add(r.nextInt());
    }
//    调用容器的toString方法会迭代容器
    System.out.println("DEBUG:add ten element to" + set);
  }
}
