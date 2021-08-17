package com.example.treasuremall.componenets.parallel.model;

import com.example.treasuremall.componenets.parallel.executor.ParallelExecutor;

import java.util.List;

/**
 * @author 小沙弥
 * @description 根据不同的模式执行并发
 * @date 2021/8/16 4:38 下午
 */
public interface ParallelExecutorModel<T, R> {

    /**
     * 按照指定的并发模式执行
     */
    List<R> execute(List<List<T>> parallelDataList, ParallelExecutor<T, R> parallelExecutor);

}
