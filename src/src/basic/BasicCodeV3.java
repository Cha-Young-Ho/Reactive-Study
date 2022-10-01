package basic;

import java.util.Iterator;

/**
 * <h1>BasicCodeV3</h1>
 * @Author : YoungHo cha
 */
public class BasicCodeV3 {
    public static void main(String[] args) {


        Iterable<Integer> iter = () ->{
            return new Iterator<>() {
                int i = 0;
                static final int MAX_V2 = 10;

                public boolean hasNext() {
                    return i < MAX_V2;
                }

                public Integer next() {
                    return ++i;
                }
            };
        };

        iter.forEach(v -> System.out.println(v));
    }
}
