package com.ypb.jdk8;

/**************************** 学习笔记(2018年12月10日) ******************************/
// 学习jdk8
// https://github.com/winterbe/java8-tutorial
// https://www.jianshu.com/p/a9ad46f86fb4

// Java8 Collectors 收集器静态方法
// 主要方法：
//	名称                返回类型                  用于                                                 使用示例
//  toList()            List<T>                 将流中所有项目收集到一个List                            List<T> l = stream.collect(toList());
//	toSet()             Set<T>                  将流中所有项目收集到一个Set                             Set<T> s = stream.collect(toSet());
//	toCollection        Collection<T>           将数据转换为指定的集合                                  Collection<T> c = stream.collect(toCollection(),ArrayList::new);
// 	                                                                                                 Collection<T> c = stream.collect(toCollection(),HashSet::new);
//	counting()          Long                    计算流中数量                                           long l = stream.collect(counting());
//	summingInt()        Integer                 返回流中整数属性求和                                    int i = stream.collect(summingInt( T::getXX ));
//	averagingInt()      Double                  计算流中Integer属性的平均值                             double d = stream.collect(averagingInt(T::getXX));
//	summarizingInt()    IntSummaryStatistics    收集流中Integer属性的统计值                             IntSummaryStatistics i = stream.collect(summarizingInt(T::getXX));
//	joining()           String                  将流中每个元素调用toString方法拼接                       String s = stream.collect(joining(" , ");
//	maxBy()             Optional<T>             筛选流中最大元素的Optional，流为空则Optional.empty()     Optional<T> o = stream.collect(maxBy(Comparator.comparingInt(T::getXX)));
//	minBy()             Optional<T>             筛选流中最小元素的Optional，流为空则Optional.empty()     Optional<T> o = stream.collect(minBy(Comparator.comparingInt(T::getXX)));
//	reducing()          归约操作产生的类型        从初始值开始，利用BinaryOperator与流中每个元素相结合，从而将流归约成单个值    int i = stream.collect(reducing(0, T::getXX, Integer :: sum));
//	collectingAndThen() 转换函数返回的类型        包裹一个收集器，对其结果应用转换函数                      int i = stream.collect(collectingAndThen(toList(),List :: size);
//	groupingBy()        Map<K,List<T>>          根据流中一个属性的值做分组，并以该属性为Map的一个Key       Map<T.XX,List<T>> map = stream.collect(groupingBy(T::getXX));
//	partitioningBy()    Map<Boolean,List<T>>    根据对流中的每个元素的应用谓词的结果进行分区(true,false)   Map<Boolean,List<T>> map = stream.collect(partitioningBy(T::isXX));