package com.example.treasuremall.componenets.parallel.executor.impl;

import com.example.treasuremall.componenets.parallel.executor.ParallelExecutor;

import java.util.List;

/**
 * @author 小沙弥
 * @description 测试并发执行器
 * @date 2021/8/16 5:25 下午
 */
public class TestParallelExecutor implements ParallelExecutor<Integer, Boolean> {

    @Override
    public Boolean execute(List<Integer> parallelData) {
        System.out.println(Thread.currentThread());
        return true;
    }

}
