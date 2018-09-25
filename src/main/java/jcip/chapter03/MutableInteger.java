package jcip.chapter03;

import jcip.annotations.NotThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-2：非线程安全的可变整数类
 */
@NotThreadSafe
public class MutableInteger {

  private int value;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
