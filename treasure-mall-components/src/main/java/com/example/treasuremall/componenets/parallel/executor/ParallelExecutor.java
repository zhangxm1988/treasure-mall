package com.example.treasuremall.componenets.parallel.executor;

import java.util.List;

/**
 * @author 小沙弥
 * @description 并发执行器
 * @date 2021/8/16 4:17 下午
 */
public interface ParallelExecutor<T, R> {

    /**
     * 业务并发执行
     *
     * @param parallelData 数据
     * @return 执行结果
     */
    R execute(List<T> parallelData);

}
