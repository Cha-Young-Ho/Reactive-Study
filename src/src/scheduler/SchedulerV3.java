package scheduler;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * <h1>SchedulerV3</h1>
 * <p>subscriber가 느린 경우</p>
 * @Author : YoungHo Cha
 */
public class SchedulerV3 {
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        Publisher<Integer> pubOnPub = sub->{
            pub.subscribe(new Subscriber<Integer>() {

                ExecutorService es = Executors.newSingleThreadExecutor();
                @Override
                public void onSubscribe(Subscription subscription) {
                    sub.onSubscribe(subscription);
                }

                @Override
                public void onNext(Integer item) {
                    es.execute(()->sub.onNext(item));
                }

                @Override
                public void onError(Throwable throwable) {
                    es.execute(()->sub.onError(throwable));
                }

                @Override
                public void onComplete() {
                    es.execute(()->sub.onComplete());
                    es.shutdown();
                }
            });
        };

        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext 실행 -- value = " + item + " ---Thread 정보 = " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete 실행 -- Thread 정보 = " + Thread.currentThread().getName());
            }
        });

        System.out.println("main exit");
    }
}
