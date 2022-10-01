package observable;

import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>ObservableV2</h1>
 *
 * @Author : YoungHo Cha
 */
@SuppressWarnings("deprecation")
public class ObservableV2 {

    static class IntObservable extends java.util.Observable implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i <= 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }
    public static void main(String[] args) {

        Observer ob = new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                System.out.println("ob = " + arg);
            }
        };
        Observer ob2 = new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                System.out.println("ob2 = " + arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob);
        io.addObserver(ob2);


        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);
        System.out.println("옵저버 개수 = " + io.countObservers());
        es.shutdown();
    }

}
