package jcip.chapter11;

import java.util.HashSet;
import java.util.Set;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/10/22
 *
 * 程序清单11-7：将ServerStatus重新改写为使用锁分解技术
 */
@ThreadSafe
public class ServerStatusAfterSplit {

  @GuardedBy("users")
  public final Set<String> users;
  @GuardedBy("queries")
  public final Set<String> queries;

  public ServerStatusAfterSplit() {
    users = new HashSet<String>();
    queries = new HashSet<String>();
  }

  public void addUser(String u) {
    synchronized (users) {
      users.add(u);
    }
  }

  public void addQuery(String q) {
    synchronized (queries) {
      queries.add(q);
    }
  }

  public void removeUser(String u) {
    synchronized (users) {
      users.remove(u);
    }
  }

  public void removeQuery(String q) {
    synchronized (users) {
      queries.remove(q);
    }
  }
}
