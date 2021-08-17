package com.example.treasuremall.api;

import com.example.treasuremall.componenets.parallel.process.ParallelProcessor;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author 小沙弥
 * @description 并发测试
 * @date 2021/8/17 3:36 下午
 */
public class ParallelTest {

    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        // 测试计算和
        ParallelProcessor<Integer, Integer> parallelProcessor = ParallelProcessor.buildFJProcessor(parallelData -> {
            Integer count = 0;
            for (Integer parallelDatum : parallelData) {
                count += parallelDatum;
            }
            return count;
        });
        Integer result = parallelProcessor.execute(list).stream().reduce(0, Integer::sum);
        System.out.println(result);
    }

}
