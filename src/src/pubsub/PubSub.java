package pubsub;


import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>PubSub</h1>
 *
 * @Author : YoungHo Cha
 */

public class PubSub {
    public static void main(String[] args) {
        Iterable<Integer> iter = Stream.iterate(1, v-> v+1).limit(10).collect(Collectors.toList());

        Publisher<Integer> pub = new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(v -> sub.onNext(v));
                            sub.onComplete();
                        }
                        catch (Throwable t){
                            sub.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> sub = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe() 실행");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext() 실행 ---" +" value = " + item);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete() 실행");
            }
        };

        pub.subscribe(sub);
    }
}
