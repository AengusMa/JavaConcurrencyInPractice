package jcip.chapter05;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-12：使用FutureTask来提前加载稍后需要的数据（闭锁）
 */
public class Preloader {
  ProductInfo loadProductInfo() throws DataLoadException{
    return null;
  }
    private final FutureTask<ProductInfo> future =new FutureTask<>(new Callable<ProductInfo>() {
      @Override
      public ProductInfo call() throws DataLoadException {
        return loadProductInfo();
      }
    });
  private final Thread thread= new Thread(future);
  public void start(){
    thread.start();
  }
public ProductInfo get()throws DataLoadException,InterruptedException{
    try {
      return future.get();
    }catch (ExecutionException e){
      Throwable cause = e.getCause();
//      Callable抛出的受检查异常
      if(cause instanceof DataLoadException){
        throw (DataLoadException) cause;
      }else{
        throw LaunderThrowable.launderThrowable(cause);
      }
    }
}

  interface ProductInfo{

  }
}
class DataLoadException extends Exception{

}
