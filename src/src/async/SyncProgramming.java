package async;

/**
 * <h1>SyncProgramming</h1>
 * <p>동기 프로그래밍</p>
 *
 * @Author : YoungHo Cha
 */
public class SyncProgramming {

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(2000);
        System.out.println("start --- Thread = " + Thread.currentThread().getName());

        System.out.println("exit --- Thread = " + Thread.currentThread().getName());
    }
}
