package jcip.chapter11;

import java.util.HashSet;
import java.util.Set;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/10/22
 *
 * 程序清单11-6：对锁进行分解
 */
@ThreadSafe
public class ServerStatusBeforeSplit {

  @GuardedBy("this")
  public final Set<String> users;
  @GuardedBy("this")
  public final Set<String> queries;

  public ServerStatusBeforeSplit() {
    users = new HashSet<String>();
    queries = new HashSet<String>();
  }

  public synchronized void addUser(String u) {
    users.add(u);
  }

  public synchronized void addQuery(String q) {
    queries.add(q);
  }

  public synchronized void removeUser(String u) {
    users.remove(u);
  }

  public synchronized void removeQuery(String q) {
    queries.remove(q);
  }
}
