package jcip.chapter05;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-15：通过使用CyclicBarrier协调细胞自动衍生系统的计算（栅栏）
 */
public class CellularAutomata {

  private final Board mainBoard;
  private final CyclicBarrier barrier;
  private final Worker[] workers;

  public CellularAutomata(Board board) {
    this.mainBoard = board;
    int count = Runtime.getRuntime().availableProcessors();
    this.barrier = new CyclicBarrier(count, new Runnable() {
      @Override
      public void run() {
        mainBoard.commitNewValues();
      }
    });
    this.workers = new Worker[count];
    for (int i = 0; i < count; i++) {
      workers[i] = new Worker(mainBoard.getSubBoard(count, i));
    }
  }

  private class Worker implements Runnable {

    private final Board board;

    public Worker(Board board) {
      this.board = board;
    }

    @Override
    public void run() {
      while (!board.hasConverged()) {
        for (int x = 0; x < board.getMaxX(); x++) {
          for (int y = 0; y < board.getMaxY(); y++) {
            board.setNewValue(x, y, computeValue(x, y));
          }
        }
        try {
//          该方法将阻塞直到所有线程到达栅栏位置
          barrier.await();
        } catch (InterruptedException ex) {
          return;
        } catch (BrokenBarrierException ex) {
          return;
        }
      }
    }

    private int computeValue(int x, int y) {
      // Compute the new value that goes in (x,y)
      return 0;
    }
  }

  public void start() {
    for (int i = 0; i < workers.length; i++) {
      new Thread(workers[i]).start();
    }
    mainBoard.waitForConvergence();
  }

  interface Board {

    int getMaxX();

    int getMaxY();

    int getValue(int x, int y);

    int setNewValue(int x, int y, int value);

    void commitNewValues();

    boolean hasConverged();

    void waitForConvergence();

    Board getSubBoard(int numPartitions, int index);
  }
}
