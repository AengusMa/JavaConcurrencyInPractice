package jcip.chapter04;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author mawenlong
 * @date 2018/09/27
 */
public class ImprovedList<T> implements List<T> {

  private final List<T> list;

  public ImprovedList(List<T> list) {
    this.list = list;
  }

  public synchronized boolean puIfAbsent(T x) {
    boolean contains = list.contains(x);
    if (!contains) {
      list.add(x);
    }
    return !contains;
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return list.toArray(a);
  }

  @Override
  public synchronized boolean add(T e) {
    return list.add(e);
  }

  @Override
  public synchronized boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return list.containsAll(c);
  }

  @Override
  public synchronized boolean addAll(Collection<? extends T> c) {
    return list.addAll(c);
  }

  @Override
  public synchronized boolean addAll(int index, Collection<? extends T> c) {
    return list.addAll(index, c);
  }

  @Override
  public synchronized boolean removeAll(Collection<?> c) {
    return list.removeAll(c);
  }

  @Override
  public synchronized boolean retainAll(Collection<?> c) {
    return list.retainAll(c);
  }

  @Override
  public boolean equals(Object o) {
    return list.equals(o);
  }

  @Override
  public int hashCode() {
    return list.hashCode();
  }

  @Override
  public T get(int index) {
    return list.get(index);
  }

  @Override
  public T set(int index, T element) {
    return list.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    list.add(index, element);
  }

  @Override
  public T remove(int index) {
    return list.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return list.listIterator(index);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return list.subList(fromIndex, toIndex);
  }

  @Override
  public synchronized void clear() {
    list.clear();
  }
}
