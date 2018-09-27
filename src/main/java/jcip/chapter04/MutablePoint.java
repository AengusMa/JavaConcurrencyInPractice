package jcip.chapter04;

import jcip.annotations.NotThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/26
 *
 * 程序清单4-5：与java.awt.Point类似的可变Point类（不要这么做）
 */
@NotThreadSafe
public class MutablePoint {

  public int x, y;

  public MutablePoint() {
    x = 0;
    y = 0;
  }

  public MutablePoint(MutablePoint p) {
    this.x = p.x;
    this.y = p.y;
  }
}
