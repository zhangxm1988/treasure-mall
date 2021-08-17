package com.example.treasuremall.componenets.parallel.model.impl;

import com.example.treasuremall.componenets.parallel.executor.ParallelExecutor;
import com.example.treasuremall.componenets.parallel.model.ParallelExecutorModel;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author 小沙弥
 * @description fork/join并发执行模式
 * @date 2021/8/16 5:05 下午
 */
public class FJParallelExecutorModel<T, R> implements ParallelExecutorModel<T, R> {

    /**
     * ForkJoinPool线程池
     */
    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    @Override
    public List<R> execute(List<List<T>> parallelDataList, ParallelExecutor<T, R> parallelExecutor) {
        if (CollectionUtils.isEmpty(parallelDataList)) {
            return Collections.emptyList();
        }
        //分片完成后并行执行所有记录
        if (parallelDataList.size() == 1) {
            //数量为1，不需要额外线程
            return Lists.newArrayList(parallelExecutor.execute(parallelDataList.get(0)));
        }
        //并行执行
        ForkJoinExecuteTask forkJoinWorker = new ForkJoinExecuteTask(parallelDataList, parallelExecutor);
        return forkJoinPool.invoke(forkJoinWorker);
    }


    /**
     * @description: 内部类，用来执行fj任务，RecursiveAction：用于没有返回结果的任务 RecursiveTask：用于有返回结果的任务
     */
    private class ForkJoinExecuteTask extends RecursiveTask<List<R>> {

        /**
         * 业务执行数据
         */
        private final List<List<T>> parallelDataList;

        /**
         * 业务执行器
         */
        private final ParallelExecutor<T, R> parallelExecutor;


        ForkJoinExecuteTask(List<List<T>> parallelDataList, ParallelExecutor<T, R> parallelExecutor) {
            this.parallelDataList = parallelDataList;
            this.parallelExecutor = parallelExecutor;
        }

        @Override
        protected List<R> compute() {
            if (parallelDataList.size() == 1) {
                //数量为0直接执行,否则通过fork/join
                return Lists.newArrayList(parallelExecutor.execute(parallelDataList.get(0)));
            }
            // 二分
            int halfSize = parallelDataList.size() / 2;
            ForkJoinExecuteTask leftTask = new ForkJoinExecuteTask(parallelDataList.subList(0, halfSize), parallelExecutor);
            ForkJoinExecuteTask rightTask = new ForkJoinExecuteTask(parallelDataList.subList(halfSize, parallelDataList.size()),
                    parallelExecutor);
            leftTask.fork();
            rightTask.fork();
            return merge(leftTask.join(), rightTask.join());
        }



        /**
         * 合并结果
         * @param result1 结果1
         * @param result2 结果2
         * @return 总的结果
         */
        public List<R> merge(List<R> result1, List<R> result2) {
            List<R> mergeResult = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(result1)) {
                mergeResult.addAll(result1);
            }
            if (!CollectionUtils.isEmpty(result2)) {
                mergeResult.addAll(result2);
            }
            return mergeResult;
        }

    }

}
