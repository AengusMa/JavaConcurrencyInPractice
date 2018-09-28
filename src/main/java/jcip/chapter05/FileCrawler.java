package jcip.chapter05;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-8：桌面搜索应用程序中的生产者任务与消费任务
 */
public class FileCrawler implements Runnable {

  private final BlockingQueue<File> fileQueue;
  private final FileFilter fileFilter;
  private final File root;

  public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
    this.fileQueue = fileQueue;
    this.root = root;
    this.fileFilter = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory() || fileFilter.accept(pathname);
      }
    };
  }

  private boolean alreadyIndexed(File f) {
    return false;
  }

  @Override
  public void run() {
    try {
      crawl(root);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private void crawl(File root) throws InterruptedException {
    File[] entries = root.listFiles(fileFilter);
    if (entries != null) {
      for (File entry : entries) {
        if (entry.isDirectory()) {
          crawl(entry);
        } else if (!alreadyIndexed(entry)) {
          fileQueue.put(entry);
        }
      }
    }
  }

  public static class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      try {
        while (true) {
          indexFile(queue.take());
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    public void indexFile(File file) {
//      为文件建立索引
    }
  }

  /**
   * 程序清单5-9：启动桌面搜索
   */
  private static final int BOUND = 10;
  private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

  public static void startIndexing(File[] roots) {
    BlockingQueue<File> queue = new LinkedBlockingDeque<>(BOUND);
    FileFilter filter = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return true;
      }
    };
    for (File root : roots) {
      new Thread(new FileCrawler(queue, filter, root)).start();
    }
    for (int i = 0; i < N_CONSUMERS; i++) {
      new Thread(new Indexer(queue)).start();
    }
  }
}


