package com.lhf.messageQueue;

import redis.clients.jedis.JedisPubSub;

/**
 * 消息订阅者
 * 监听器会对频道和模式的订阅、接收消息和退订等事件进行监听，然后进行相应的处理。
 * 
 * @author liuhefei
 * 2018年9月20日
 */
public class MessageSubscriber extends JedisPubSub {
	//消息订阅成功的处理
	public void onMessage(String channel, String message) {
		System.out.println("onMessage: " + channel + "=" + message);
		if (message.equals("quit"))
			this.unsubscribe(channel);
	}

	// 订阅初始化
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println("onSubscribe: " + channel + "=" + subscribedChannels);
	}

	// 取消订阅
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println("onUnsubscribe: " + channel + "=" + subscribedChannels);
	}

	// 按模式的方式订阅
	public void onPSubscribe(String pattern, int subscribedChannels) {
		System.out.println("onPSubscribe: " + pattern + "=" + subscribedChannels);
	}

	// 取消按模式的方式订阅
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		System.out.println("onPUnsubscribe: " + pattern + "=" + subscribedChannels);
	}

	// 处理消息订阅模式
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println("onPMessage: " + pattern + "=" + channel + "=" + message);
		if (message.equals("quit"))
			this.punsubscribe(pattern);
	}

}
