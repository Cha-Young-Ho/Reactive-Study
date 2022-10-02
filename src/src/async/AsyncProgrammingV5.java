package async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 *<h1>AsyncProgrammingV5</h1>
 * <p>FutureTask에서 지원해주는 콜백함수 사용하기</p>
 *
 * @Author YoungH Cha
 */
public class AsyncProgrammingV5 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<String> f = new FutureTask<String>(() ->{
            Thread.sleep(1000);
            System.out.println("start --- Thread = " + Thread.currentThread().getName());
            return "finish";
        }){
            @Override
            protected void done() {
                try {
                    System.out.println(get() + " --- Thread = " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
            }
        };

        es.execute(f);

        System.out.println("exit --- Thread = " + Thread.currentThread().getName());

        es.shutdown();

    }
}
