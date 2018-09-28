package jcip.chapter05;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-13：强制将未检查的Throwable转换为RuntimeException
 */
public class LaunderThrowable {

  public static RuntimeException launderThrowable(Throwable t) {
    if (t instanceof RuntimeException) {
//      RuntimeException
      return (RuntimeException) t;
    } else if (t instanceof Error) {
//      Error
      throw (Error) t;
    } else {
      throw new IllegalStateException("Not unchecked", t);
    }
  }
}
