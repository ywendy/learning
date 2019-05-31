package com.ypb.concurrent.stage3.blockingqueue.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoctorFactory {
    private static final Object locker=new Object();
    private static final Integer MAX_QUEUE_SIZE=3;
    private static  DoctorFactory doctorFactory=null;
    private Integer doctorCount=3;
    private List<BlockingQueue<Student>> studentQueueList=new ArrayList<>();
 
    private DoctorFactory()
    {
        ExecutorService executor = Executors.newFixedThreadPool(doctorCount); //线程池
        for (int i = 0; i < doctorCount; i++) {
            BlockingQueue<Student> recoDataBlockingQueue = new ArrayBlockingQueue<Student>(MAX_QUEUE_SIZE);
            studentQueueList.add(recoDataBlockingQueue);
            int time=(i+1)*1000;
            executor.execute(new DetectRunnable(Integer.toString(i),time, recoDataBlockingQueue));
        }
        executor.shutdown(); //关闭执行器，不允许继续提交其它线程
    }
    public static DoctorFactory getInstance() {
        if (doctorFactory == null) {
            synchronized (locker) {
                if (doctorFactory == null) {
                    doctorFactory = new DoctorFactory();
                }
            }
        }
        return doctorFactory;
    }
 
    public boolean putStudentData(Student student) {
        try {
            int pos = findShortQueue();
            BlockingQueue<Student> studentBlockingQueue = studentQueueList.get(pos);
            if (studentBlockingQueue != null) {
                if (studentBlockingQueue.size() >= MAX_QUEUE_SIZE) {
                    System.out.println("student "+ student.getId()+" go");
                    throw new Exception("人数爆了！");
                }
                System.out.println("student "+ student.getId()+" enter "+" dotctor "+pos);
                studentBlockingQueue.put(student);
             }
        } catch (Exception e) {
        }
        return false;
    }
 
    private int findShortQueue() {
        int pos = 0;
        int size = MAX_QUEUE_SIZE;
        for (int i = 0; i < studentQueueList.size(); i++) {
            BlockingQueue<Student> studentBlockingQueue = studentQueueList.get(i);
            if (studentBlockingQueue != null) {
                if (studentBlockingQueue.size() < size) {
                    size = studentBlockingQueue.size();
                    pos = i;
                }
            }
        }
        return pos;
    }
}