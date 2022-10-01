package basic;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>BasicCodeV1</h1>
 * @Author : YoungHo cha
 */
public class BasicCodeV1 {

    public static void main(String[] args) {
        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        for(Integer value : iter){
            System.out.println(value);
        }
    }
}
