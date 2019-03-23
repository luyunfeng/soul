package tech.soulcoder.learn.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用 stream 去切割数组
 * @author yunfeng.lu
 * @create 2019/3/20.
 */
public class StreamToSubListDemo {

    public static void main(String[] args) {
        List<String> testList = new ArrayList<>();
        testList.add("1");
        testList.add("2");
        testList.add("3");
        testList.add("4");
        testList.add("5");
        // 如果想要截取字符串最好不要采用 submit 的方式，如果下面还对字符串做增删改操作实际的父List 还是会发生变化
        List<String> testList2 = testList.subList(2,4);
        // 直接采用 java 8 的 stream操作
///     List<String> testList2 =  testList.stream().skip(2).limit(2).collect(Collectors.toList());
        testList2.remove(1);
        System.out.println(testList2);
        System.out.println(testList);

    }
}
