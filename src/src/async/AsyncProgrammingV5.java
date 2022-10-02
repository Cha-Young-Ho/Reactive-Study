package async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 *
 */
public class AsyncProgrammingV5 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<String> f = new FutureTask<String>(() ->{
            Thread.sleep(1000);
            System.out.println("start");
            return "finish";
        }){
            @Override
            protected void done() {
                try {
                    System.out.println(get());
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
            }
        };

        es.execute(f);

        System.out.println("exit");

        es.shutdown();

    }
}
