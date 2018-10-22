package jcip.chapter11;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import jcip.annotations.GuardedBy;

/**
 * @author mawenlong
 * @date 2018/10/22
 *
 * 程序清单11-5：减少锁的持有时间
 */
public class BetterAttributeStore {

  @GuardedBy("this")
  private final Map<String, String> attributes = new HashMap<String, String>();

  public boolean userLocationMatches(String name, String regexp) {
    String key = "users." + name + ".location";
    String location;
    synchronized (this) {
      location = attributes.get(key);
    }
    if (location == null) {
      return false;
    } else {
      return Pattern.matches(regexp, location);
    }
  }
}
