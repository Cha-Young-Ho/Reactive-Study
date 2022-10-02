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
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            System.out.println("start");
            callback("finish");
        });

        System.out.println("exit");
        es.shutdown();
    }

    public static void callback(String str){
        System.out.println("작업 끝");
    }
}
