package jcip.chapter11;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/10/21
 *
 * 程序清单11-4：将一个锁不必要地持有过长时间
 */
@ThreadSafe
public class AttributeStore {

  @GuardedBy("this")
  private final Map<String, String> attributes = new HashMap<String, String>();

  public synchronized boolean userLocationMatches(String name, String regexp) {
    String key = "users." + name + ".location";
    String location = attributes.get(key);
    if (location == null) {
      return false;
    } else {
      return Pattern.matches(regexp, location);
    }
  }
}
