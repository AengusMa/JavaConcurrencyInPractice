package jcip.chapter03;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-4：数绵羊，volatile关键字
 */
public class CountingSheep {

  volatile boolean asleep;

  void tryToSleep() {
    while (!asleep) {
      countSomeSheep();
    }
  }

  void countSomeSheep() {
    // One, two, three...
  }
}
