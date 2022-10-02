package async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>AsyncProgramming</h1>
 *
 * <p>비동기 프로그래밍</p>
 *
 * @Author : YoungHo Cha
 */
public class AsyncProgramming {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println("start");
        });

        System.out.println("exit");
    }
}
