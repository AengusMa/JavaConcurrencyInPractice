package jcip.chapter03;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-8：使用工厂方法来防止this引用在构造过程中逸出。
 * 只有当构造函数返回时，this引用才应该从线程中逸出。构造函数可以将this引用保存到某个地方，
 * 只要其他线程不会在构造函数完成之前使用它。
 */
public class SafeListener {

  private final EventListener listener;

  /**
   * 在这个构造中，我们看到的最大的一个区别就是：当构造好了SafeListener对象（通过构造器构造）之后，我们才启动了监听线程，
   * 也就确保了SafeListener对象是构造完成之后再使用的SafeListener对象。
   */
  private SafeListener() {
    listener = new EventListener() {
      @Override
      public void onEvent(Event e) {
        doSomething(e);
      }
    };
  }

  public static SafeListener newInstance(EventSource source) {
    SafeListener safeListener = new SafeListener();
//    registerListener方法会启动新的线程
    source.registerListener(safeListener.listener);
    return safeListener;
  }


  void doSomething(Event e) {
  }


  interface EventSource {

    void registerListener(EventListener e);
  }

  interface EventListener {

    void onEvent(Event e);
  }

  interface Event {

  }
}
