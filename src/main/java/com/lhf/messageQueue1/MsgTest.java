package com.lhf.messageQueue1;

public class MsgTest {
	public static void main(String[] args) throws Exception {   
        
        // 启动一个生产者线程，模拟任务的产生   
        new Thread(new MsgProducer()).start();   
           
        Thread.sleep(15000);   
           
        //启动一个线程者线程，模拟任务的处理   
        new Thread(new MsgConsumer()).start();   
           
        //主线程休眠   
        Thread.sleep(Long.MAX_VALUE);   
    }   

}
