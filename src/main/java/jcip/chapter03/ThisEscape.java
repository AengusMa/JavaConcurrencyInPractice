package jcip.chapter03;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-7：隐式地使用this引用逸出（不要这么做）
 */
public class ThisEscape {

  /**
   * 这会导致this逸出，所谓逸出，就是在不该发布的时候发布了一个引用。在这个例子里面， 当我们实例化ThisEscape对象时，会调用source的registerListener方法，这时便启动了一个线程，
   * 而且这个线程持有了ThisEscape对象（调用了对象的doSomething方法）， 但此时ThisEscape对象却没有实例化完成（还没有返回一个引用），
   * 所以我们说，此时造成了一个this引用逸出，即还没有完成的实例化ThisEscape对象的动作， 却已经暴露了对象的引用。其他线程访问还没有构造好的对象，可能会造成意料不到的问题。
   */
  public ThisEscape(EventSource source) {
//  registerListener方法会启动新的线程
    source.registerListener(new EventListener() {
      @Override
      public void onEvent(Event e) {
        doSomething(e);
      }
    });
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

