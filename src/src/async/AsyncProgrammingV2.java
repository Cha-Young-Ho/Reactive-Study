package async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <h1>AsyncProgrammingV2</h1>
 *
 * <p>Async 프로그래밍에서 Thread의 리턴을 받는 클래스</p>
 *
 * @Author : YoungHo Cha
 */
public class AsyncProgrammingV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        Future<String> f =  es.submit(() ->{
            Thread.sleep(2000);
            System.out.println("start --- Thread = " + Thread.currentThread().getName());
            return "finish";
        });
        System.out.println("exit -- Thread = " + Thread.currentThread().getName());
        System.out.println("결과 값 = " + f.get() + " --- Thread = " + Thread.currentThread().getName()); // blocking
        es.shutdown();

    }
}
