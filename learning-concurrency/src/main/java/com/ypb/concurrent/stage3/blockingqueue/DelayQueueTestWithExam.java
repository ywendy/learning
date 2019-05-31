package com.ypb.concurrent.stage3.blockingqueue;//package com.qitoon.test.concurrent.blockingqueue;
//
//import java.util.Iterator;
//import java.util.Random;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.DelayQueue;
//import java.util.concurrent.Delayed;
//import java.util.concurrent.TimeUnit;
//
//import com.google.common.base.Strings;
//
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * @ClassName: DelayQueueTestWithExam
// * @Description: DelayQueue场景(多考生考试) http://www.cnblogs.com/sunzhenchao/p/3515085.html
// * 	业务场景一：多考生考试
//	该场景来自于http://ideasforjava.iteye.com/blog/657384，模拟一个考试的日子，考试时间为120分钟，30分钟后才可交卷，当时间到了，或学生都交完卷了考试结束。
//
//	这个场景中几个点需要注意：
//
//    	考试时间为120分钟，30分钟后才可交卷，初始化考生完成试卷时间最小应为30分钟
//    	对于能够在120分钟内交卷的考生，如何实现这些考生交卷
//    	对于120分钟内没有完成考试的考生，在120分钟考试时间到后需要让他们强制交卷
//    	在所有的考生都交完卷后，需要将控制线程关闭
//
//	实现思想：用DelayQueue存储考生（Student类），每一个考生都有自己的名字和完成试卷的时间，Teacher线程对DelayQueue进行监控，收取完成试卷小于120分钟的学生的试卷。当考试时间120分钟到时，先关闭Teacher线程，然后强制DelayQueue中还存在的考生交卷。每一个考生交卷都会进行一次countDownLatch.countDown()，当countDownLatch.await()不再阻塞说明所有考生都交完卷了，而后结束考试。
// * @author 杨鹏兵
// * @date 2017年7月3日-上午11:35:42
// * @version V1.0.0
// *
// */
//public class DelayQueueTestWithExam {
//
//	public static void main(String[] args) throws Exception {
//		int studentNumber = 100; //考生个数
//		int finishMinTime = 30; //30分钟后才可交卷
//		int finishMaxTime = 120; //考试时间为120分钟
//		CountDownLatch countDownLatch = new CountDownLatch(studentNumber+1);
//		BlockingQueue<com.qitoon.test.concurrent.blockingqueue.test.Student> students = new DelayQueue<>();
//		Random r = new Random();
//		for (int i = 0; i < studentNumber; i++) {
//			students.put(new com.qitoon.test.concurrent.blockingqueue.test.Student("student"+Strings.padStart(""+(i+1), 2, '0'), finishMinTime+r.nextInt(finishMaxTime), countDownLatch));
//		}
//
//		TeacherThread teacherThread = new TeacherThread(students);
//		students.put(new EndExam(students, finishMaxTime, countDownLatch, teacherThread)); //结束考试
//
//		teacherThread.start();
//		countDownLatch.await();
//		System.out.println("考试时间到，全部交卷！");
//	}
//}
//
///**
// * @ClassName: Teacher
// * @Description: 老师
// * @author 杨鹏兵
// * @date 2017年7月3日-下午12:33:26
// * @version V1.0.0
// *
// */
//class TeacherThread extends Thread{
//
//	private BlockingQueue<com.qitoon.test.concurrent.blockingqueue.test.Student> students;
//	private boolean stopRequested;
//
//	public TeacherThread(BlockingQueue<com.qitoon.test.concurrent.blockingqueue.test.Student> students){
//		this.students = students;
//	}
//
//	/**
//	 * @Title: requestStop
//	 * @Description: 停止线程
//	 * @author 杨鹏兵
//	 * @date 2017年7月3日-下午12:42:31
//	 *
//	 */
//	public void requestStop(){
//		stopRequested = true;
//	}
//
//	@Override
//	public void run() {
//		System.out.println("test start");
//		try {
//			while(!stopRequested){
//				students.take().run();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
//
//class EndExam extends com.qitoon.test.concurrent.blockingqueue.test.Student {
//
//	private BlockingQueue<com.qitoon.test.concurrent.blockingqueue.test.Student> students;
//	private CountDownLatch countDownLatch;
//	private TeacherThread teacherThread;
//
//	public EndExam(BlockingQueue<com.qitoon.test.concurrent.blockingqueue.test.Student> students, long workTime, CountDownLatch countDownLatch, TeacherThread teacherThread) {
//		super("强制收卷", workTime, countDownLatch);
//		this.students = students;
//		this.countDownLatch = countDownLatch;
//		this.teacherThread = teacherThread;
//	}
//
//	@Override
//	public void run() {
//		teacherThread.requestStop();
//		Iterator<com.qitoon.test.concurrent.blockingqueue.test.Student> it = students.iterator();
//		while(it.hasNext()){
//			com.qitoon.test.concurrent.blockingqueue.test.Student student = it.next();
//			student.setForce(true);
//			student.run();
//		}
//		countDownLatch.countDown();
//	}
//}
//
///**
// * @ClassName: Student
// * @Description: 考生
// * @author 杨鹏兵
// * @date 2017年7月3日-上午11:40:51
// * @version V1.0.0
// *
// */
//class Student implements Runnable, Delayed{
//
//	private String name;
//	private long workTime;
//	private long submitTime;
//	@Setter@Getter
//	private boolean force; //强制交卷
//	private CountDownLatch countDownLatch;
//
//	public Student(String name, long workTime, CountDownLatch countDownLatch) {
//		this.name = name;
//		this.workTime = workTime;
//		this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS) + System.nanoTime();
//		this.countDownLatch = countDownLatch;
//	}
//
//	@Override
//	public int compareTo(Delayed o) {
//		if(null == o || !(o instanceof com.qitoon.test.concurrent.blockingqueue.test.Student)) return 1;
//		if(o == this) return 0;
//		com.qitoon.test.concurrent.blockingqueue.test.Student s = (com.qitoon.test.concurrent.blockingqueue.test.Student) o;
//		return (int) (this.workTime - s.workTime);
//	}
//
//	@Override
//	public long getDelay(TimeUnit unit) {
//		return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
//	}
//
//	@Override
//	public void run() {
//		if(force){
//			workTime = 120;
//		}
//		System.out.println(String.format("%s交卷\t希望用时%d分钟\t实际用时%d分钟", name, workTime, workTime));
//		countDownLatch.countDown();
//	}
//}