package jcip.chapter06;

import static jcip.chapter05.LaunderThrowable.launderThrowable;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-15：使用CompletionService，使页面元素在下载完成后立即显示出来
 */
public abstract class Renderer {

  private final ExecutorService executor;

  Renderer(ExecutorService executor) {
    this.executor = executor;
  }

  void renderPage(CharSequence source) {
    final List<ImageInfo> infos = scanForImageInfo(source);
    CompletionService completionService = new ExecutorCompletionService(executor);
    for (final ImageInfo imageInfo : infos) {
      completionService.submit(new Callable<ImageData>() {
        @Override
        public ImageData call() {
          return imageInfo.downloadImage();
        }
      });
    }
    renderText(source);
    try {
      for (int t = 0, n = infos.size(); t < n; t++) {
        Future<ImageData> f = completionService.take();
        ImageData imageData = f.get();
        renderImage(imageData);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
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
