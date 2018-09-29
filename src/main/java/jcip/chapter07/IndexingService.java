package jcip.chapter07;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序清单7-17：通说“毒丸”对象来关闭服务
 */
public class IndexingService {

  private static final int CAPACITY = 100;
  private static final File POISON = new File("");
  private final IndexerThread consumer = new IndexerThread();
  private final CrawlerThread producer = new CrawlerThread();
  private final BlockingQueue<File> queue;
  private final FileFilter fileFilter;
  private final File root;

  public IndexingService(File root, final FileFilter fileFilter) {
    this.queue = new LinkedBlockingQueue<>(CAPACITY);
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

  class CrawlerThread extends Thread {

    @Override
    public void run() {
      try {
        crawl(root);
      } catch (InterruptedException e) {
      } finally {
        while (true) {
          try {
            queue.put(POISON);
            break;
          } catch (InterruptedException e1) {
          }
        }
      }
    }

    private void crawl(File root) throws InterruptedException {
      File[] entries = root.listFiles(fileFilter);
      if (entries != null) {
        for (File entry : entries) {
          if (entry.isDirectory()) {
            crawl(entry);
          } else if (!alreadyIndexed(entry)) {
            queue.put(entry);
          }
        }
      }
    }
  }

  class IndexerThread extends Thread {

    @Override
    public void run() {
      try {
        while (true) {
          File file = queue.take();
          if (file == POISON) {
            break;
          } else {
            indexFile(file);
          }
        }
      } catch (InterruptedException consumed) {

      }
    }

    public void indexFile(File file) {
      /*...s*/
    }
  }

  public void start() {
    producer.start();
    consumer.start();
  }

  public void stop() {
    producer.interrupt();
  }

  public void awaitTermination() throws InterruptedException {
    consumer.join();
  }

}
