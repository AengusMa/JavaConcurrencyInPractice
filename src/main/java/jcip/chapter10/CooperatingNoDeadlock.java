package jcip.chapter10;

import java.util.HashSet;
import java.util.Set;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;
import jcip.chapter04.Point;

/**
 * @author mawenlong
 * @date 2018/10/18
 *
 * 程序清单10-6：通过公开调用来避免在相互协作的对象之间产生死锁
 */
public class CooperatingNoDeadlock {

  /**
   * 出租车对象
   */
  @ThreadSafe
  class Taxi {

    @GuardedBy("this")
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
      this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
      return location;
    }

    public synchronized void setLocation(Point location) {
      boolean reachedDestination;
      synchronized (this){
        this.location = location;
        reachedDestination = location.equals(destination);
      }
      if (reachedDestination) {
        dispatcher.notifyAvailable(this);
      }
    }

    public synchronized Point getDestination() {
      return destination;
    }

    public synchronized void setDestination(Point destination) {
      this.destination = destination;
    }

  }
  /**
   * 出租车车队，调度出租车
   */
  @ThreadSafe
  class Dispatcher {

    @GuardedBy("this")
    private final Set<Taxi> taxis;
    @GuardedBy("this")
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
      taxis = new HashSet<Taxi>();
      availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
      availableTaxis.add(taxi);
    }

    public Image getImage() {
      Set<Taxi> copy;
      synchronized (this) {
        copy = new HashSet<Taxi>(taxis);
      }
      Image image = new Image();
      for (Taxi t : copy) {
        image.drawMarker(t.getLocation());
      }
      return image;
    }
  }

  class Image {

    public void drawMarker(Point p) {
    }
  }
}
