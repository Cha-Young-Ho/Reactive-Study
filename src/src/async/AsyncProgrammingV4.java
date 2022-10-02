package async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>AsyncProgrammingV4</h1>
 *
 * <p>Future가 아닌 CallBack을 이용한 blocking 제어</p>
 *
 */
public class AsyncProgrammingV4 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        es.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println("start --- Thread = " + Thread.currentThread().getName());
            callback("finish --- Thread = " + Thread.currentThread().getName());
        });

        System.out.println("exit --- Thread =" + Thread.currentThread().getName());
        es.shutdown();
    }

    public static void callback(String str){
        System.out.println("작업 끝 --- Thread = " + Thread.currentThread().getName());
    }
}
