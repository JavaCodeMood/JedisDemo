package com.lhf.messageQueue;

import redis.clients.jedis.Jedis;

/**
 * 消息发布者
 * 1.建立两个Jedis客户端，
 * 2.建立两个发布/订阅监听器，
 * 3.启动两个线程，分别用于监听一个频道和一个模式。
 * 
 * 操作步骤：
 * 1.首选需要开启两个Redis服务
 * 2.运行主程序MessagePublisher
 * 3.在开启两个客户端，命令：./redis-cli -p 6379    ./redis-cli -p 6380
 * 4.分别在这两个客户端中执行以下命令，发布消息：
 * publish mychannel "hello message for channel"
 * publish mychannel.test "hello message for pattern"
 * 5.在Eclipse控制台中就可以看到结果
 * 
 * @author liuhefei 2018年9月20日
 */
public class MessagePublisher {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		final Jedis jedis = new Jedis("127.0.0.1", 6379);
		final Jedis pjedis = new Jedis("127.0.0.1", 6380);

		final MessageSubscriber listener = new MessageSubscriber();
		final MessageSubscriber plistener = new MessageSubscriber();

		Thread thread = new Thread(new Runnable() {
			public void run() {
				jedis.subscribe(listener, "mychannel");
			}
		});

		Thread pthread = new Thread(new Runnable() {
			public void run() {
				pjedis.psubscribe(plistener, "mychannel.*");
			}
		});

		thread.start();
		pthread.start();
	}
}
