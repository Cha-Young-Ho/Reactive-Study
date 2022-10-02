package async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <h1>AsyncProgrammingV3</h1>
 * <p>f.get()의 blocking동안 다른 작업을 하기 위한 구현</p>
 *
 * @Author : YoungHo Cha
 */
public class AsyncProgrammingV3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        Future<String> f = es.submit(() ->{
            Thread.sleep(8000);
            System.out.println("start");
            return "finish";
        });

        System.out.println("exit");

        while(!f.isDone()){
            System.out.println("do another task~");
            Thread.sleep(2000);
        }
        System.out.println(f.get());
        es.shutdown();
    }
}
