package com.ypb.concurrent.stage3.blockingqueue;

/**
 * @ClassName: PriorityBlockingQueueTest
 * @Description: 具有优先级的阻塞队列测试
 * @author 杨鹏兵
 * @date 2017年7月3日-下午3:10:05
 * @version V1.0.0
 *
 */
public class PriorityBlockingQueueTest {

}

/******************************* 学习笔记 **************************************/
//	PriorityBlockingQueue类实现了BlockingQueue接口
//	PriorityBlockingQueue是一个无界的并发队列，它使用了和类java.util.PriorityQueue一样的排序规则，无法向这个队列中插入null值
//	所有插入到PriorityBlockingQueue的元素必须实现java.lang.Comparable接口，因此该队列中元素的排序就取决于你自己的Comparable实现
//	注意PriorityBlockingQueue对于具有相同优先级(compare() == 0)的元素并不强制任何特定行为。
//	用时注意，如果你从一个PriorityBlockingQueue获得一个Iterator的话，该Iterator并不能保证它对元素的遍历是以优先级为序的
