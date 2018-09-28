package jcip.chapter06;

import static jcip.chapter05.LaunderThrowable.launderThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-13：使用Future等待图像下载
 */
public abstract class FutureRenderer {

  private final ExecutorService executor = Executors.newCachedThreadPool();

  void renderPage(CharSequence source) {
    final List<ImageInfo> imageInfos = scanForImageInfo(source);
    Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
      @Override
      public List<ImageData> call() throws Exception {
        List<ImageData> result = new ArrayList<>();
        for (ImageInfo imageInfo : imageInfos) {
          result.add(imageInfo.downloadImage());
        }
        return result;
      }
    };
    Future<List<ImageData>> future = executor.submit(task);
    renderText(source);

    try {
      List<ImageData> imageData = future.get();
      for (ImageData data : imageData) {
        renderImage(data);
      }
    } catch (InterruptedException e) {
//      重新设置线程的中断状态
      Thread.currentThread().interrupt();
//      由于不需要结果，因此取消任务
      future.cancel(true);
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }

  }

  interface ImageData {

  }

  interface ImageInfo {

    ImageData downloadImage();
  }

  abstract void renderText(CharSequence s);

  abstract List<ImageInfo> scanForImageInfo(CharSequence s);

  abstract void renderImage(ImageData i);
}
