package com.example.treasuremall.componenets.parallel.process;

import com.example.treasuremall.componenets.parallel.executor.ParallelExecutor;
import com.example.treasuremall.componenets.parallel.model.ParallelExecutorModel;
import com.example.treasuremall.componenets.parallel.model.impl.FJParallelExecutorModel;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 小沙弥
 * @description 并发执行类
 * @date 2021/8/16 4:16 下午
 */
public class ParallelProcessor<T, R> {


    /**
     * 每个List的size阈值，根据这个阈值进行截取
     */
    private static final Integer DEFAULT_THRESHOLD = 50;


    /**
     * 业务执行器
     */
    private final ParallelExecutor<T, R> parallelExecutor;


    /**
     * 并发执行模式，主要有以下几种模式：1、fork/join
     */
    private final ParallelExecutorModel<T, R> parallelExecutorModel;


    /**
     * @description: 执行并发操作
     * @param sourceData 元数据
     */
    public List<R> execute(List<T> sourceData) {
        if (CollectionUtils.isEmpty(sourceData)) {
            return null;
        }
        return execute(sourceData, DEFAULT_THRESHOLD);
    }


    /**
     * @description: 执行带阈值并发操作
     * @param sourceData 元数据
     * @param threshold 阈值
     * @return 返回结果
     */
    public List<R> execute(List<T> sourceData, Integer threshold) {
        if (CollectionUtils.isEmpty(sourceData)) {
            return null;
        }
        // 1. 给元数据分片
        List<List<T>> parallelDataList = subList(sourceData, threshold);
        // 2. 并发执行执行
        return parallelExecutorModel.execute(parallelDataList, parallelExecutor);
    }

    /**
     * 构建fork/join执行器
     */
    public static <T, R> ParallelProcessor<T, R> buildFJProcessor(ParallelExecutor<T, R> parallelExecutor) {
        return new ParallelProcessor<>(parallelExecutor, new FJParallelExecutorModel<>());
    }


    /**
     * 创建ParallelProcessor实例
     */
    public ParallelProcessor(ParallelExecutor<T, R> parallelExecutor, ParallelExecutorModel<T, R> parallelExecutorModel) {
        this.parallelExecutor = parallelExecutor;
        this.parallelExecutorModel = parallelExecutorModel;
    }


    /**
     * 截取collection，根据阈值进行分片
     * @param threshold 阈值
     * @return 分片后的数据
     */
    public static <T> List<List<T>> subList(Collection<T> collection, int threshold) {

        // 获取最大限制
        int limit = countStep(collection.size(), threshold);
        //获取分割后的集合
        return Stream.iterate(
                0, n -> n + 1).limit(limit).parallel().map(
                a -> collection.stream().skip((long) a * threshold).limit(threshold).parallel()
                        .collect(Collectors.toList())).collect(Collectors.toList());
    }


    /**
     * 计算性需要截取成几段
     * @param size 集合长度
     * @param maxNum 集合长度阈值
     * @return 截取成几段
     */
    private static Integer countStep(Integer size, Integer maxNum) {
        return (size + maxNum - 1) / maxNum;
    }

}
