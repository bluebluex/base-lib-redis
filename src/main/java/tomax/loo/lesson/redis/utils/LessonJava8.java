package tomax.loo.lesson.redis.utils;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-16 19:23
 **/
public class LessonJava8 {

    public static void main(String[] args) {

        int[] ia = range(1, 10).map(i -> i * 2).toArray();

        List<Integer> result = range(1, 10).map(i -> i * 2).boxed().collect(toList());
    }

}
