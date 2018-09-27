package jcip.chapter04;

import java.util.HashSet;
import java.util.Set;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/26
 *
 * 程序清单4-2：通过封闭机制来确保线程安全
 */
@ThreadSafe
public class PersonSet {

  private final Set<Person> mySet = new HashSet<>();

  public synchronized void addPerson(Person p) {
    mySet.add(p);
  }

  public synchronized boolean containsPerson(Person p) {
    return mySet.contains(p);
  }

  interface Person {

  }
}
