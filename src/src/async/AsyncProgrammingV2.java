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
            System.out.println("start");
            return "finish";
        });
        System.out.println(f.get()); // blocking
        System.out.println("exit");

    }
}
