package pubsub;


import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>PubSubV2</h1>
 *
 * @Author : YoungHo Cha
 */

public class PubSubV2 {

    public static void main(String[] args) {

        // publisher 생성
        Publisher pub = new Publisher();

        // 계산 Sub 생성
        CalcSubscriber calcSubscriber = new CalcSubscriber();

        // Log Sub 생성
        LogSubscriber logSubscriber = new LogSubscriber(calcSubscriber);

        // publish -> log sub -> calc sub
        pub.subscribe(logSubscriber);
    }

    public static class Publisher implements Flow.Publisher<Integer> {
        Iterable<Integer> iter = Stream.iterate(1, v -> v + 1).limit(10).collect(Collectors.toList());

        @Override
        public void subscribe(Subscriber<? super Integer> sub) {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    try {
                        iter.forEach(v -> sub.onNext(v));
                        sub.onComplete();
                    } catch (Throwable t) {
                        sub.onError(t);
                    }
                }

                @Override
                public void cancel() {

                }
            });
        }
    }

        public static class LogSubscriber implements Subscriber<Integer> {
            private final CalcSubscriber calcSubscriber;

            public LogSubscriber(CalcSubscriber calcSubscriber) {
                this.calcSubscriber = calcSubscriber;
            }

            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("LogSubscribe onNext() 실행 --- value = " + item);
                calcSubscriber.onNext(item);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        }

        public static class CalcSubscriber implements Subscriber<Integer> {

            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Calc Sub OnNext() 실행  ---- value = " + (item + 10));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        }
    }

