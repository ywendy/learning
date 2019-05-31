package com.ypb.concurrent.stage3.blockingqueue.test;

import java.util.concurrent.BlockingQueue;

public class DetectRunnable implements Runnable {
    public DetectRunnable(String name,Integer detectTime, BlockingQueue<Student> studentQueue) {
        this.studentQueue = studentQueue;
        this.name = name;
        this.detectTime=detectTime;
    }
 
    private String name;
    private BlockingQueue<Student> studentQueue;
    private Integer detectTime=1000;
    @Override
    public void run()
    {
        while (true){
            try{
                Student student=studentQueue.take();
                Thread.sleep(detectTime);
                System.out.println("doctor:" + name + " detect student:" + student.getId() +" end time " + detectTime);
            }
            catch(Exception e){}
        }
    }
}