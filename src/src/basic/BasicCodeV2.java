package basic;

import java.util.Iterator;

/**
 * <h1>BasicCodeV1</h1>
 * @Author : YoungHo cha
 */
public class BasicCodeV2 {

    public static void main(String[] args) {
        Iterable<Integer> iter1 = new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int i = 0;
                    final static int MAX_V1 = 10;
                    @Override
                    public boolean hasNext() {
                        return i < MAX_V1;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };
            }
        };

        Iterable<Integer> iter2 = () ->{
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

        for(Integer value : iter1){
            System.out.println(value);
        }

        System.out.println("-------------- \n");

        for (Integer value : iter2){
            System.out.println(value);
        }
    }
}
