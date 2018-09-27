package jcip.chapter04;

import jcip.annotations.Immutable;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单4-6：不可变Poing类
 */
@Immutable
public class Point {

  public final int x, y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "Point{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
