# Observable

해당 클래스는 옵저버 패턴을 지원해주기위한 java 라이브러리 입니다.

해당 코드의 실행 순서는 다음과 같습니다.

1. Observer 객체 생성
2. IntObservable 객체 생성
3. Observer 객체를 IntObservable 객체에 등록
4. IntObservable 객체 run
5. IntObservable 객체 update 실행
6. IntObservable 객체 update 메소드 내의 setChanged() 실행
7. IntObservable 객체에 저장된 Observer 객체 notify 및 argument 전달
8. Observer 객체 update 실행
9. 4번 ~ 8번 반복
10. 옵저버 개수 print

# ObservableV2

해당 클래스의 실행은 다음과 같습니다.

1. 옵저버 개수 print
2. 반복을 통한 print 실행

> 위의 코드와는 다른 점이 있습니다.

아래 코드는 "옵저버 개수"가 먼저 print 된 것을 볼 수 있습니다.

이유는 위의 코드는 별도의 Thread 에서 동작하였지만, 아래 코드는 single thread 에서 동작하였기 때문입니다.

# 요점

> 해당 객체에서 존재하는 내용과 basic code에서 존재하는 내용이 달라보이지만 개념면에서 push, pull 하는 부분은 똑같습니다.

옵저버 패턴은 스타크래프트의 옵저버와 같이 어떠한 객체를 쳐다보고 있는 상태입니다.

위의 코드에서 ob와 ob2는 IntObservable을 기다리는 상태(쳐다보는 상태)입니다.

> 결과적으로, 비동기적으로 처리한 모습을 볼 수 있었습니다.

# 한계

이러한 옵저버 패턴은 한계가 존재합니다.

1. 데이터를 모두 주었다는 trigger 가 존재하지 않는다. -> 완료라는 개념이 존재하지 않는다.
2. 에러를 대응하기가 힘듭니다.


