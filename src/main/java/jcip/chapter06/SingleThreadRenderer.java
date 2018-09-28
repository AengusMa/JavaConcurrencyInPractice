package jcip.chapter06;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-10：串行地渲染页面元素
 */
public abstract class SingleThreadRenderer {

  void renderPage(CharSequence source) {
//    渲染文本
    renderText(source);
    List<ImageData> imageData = new ArrayList<>();
//    下载图片
    for (ImageInfo imageInfo : scanForImageInfo(source)) {
      imageData.add(imageInfo.downloadImage());
    }
//    渲染图片
    for (ImageData data : imageData) {
      renderImage(data);
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
