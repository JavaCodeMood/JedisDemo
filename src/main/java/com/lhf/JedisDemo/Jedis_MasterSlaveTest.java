package com.lhf.JedisDemo;

import redis.clients.jedis.Jedis;

/**
 * jedis实现主从复制
 * 
 * 
 * @author liuhefei
 * 2018年9月16日
 */
public class Jedis_MasterSlaveTest {

	public static void main(String[] args) {
		//创建Jedis实例，连接Redis本地服务
		Jedis jedis_master = new Jedis("127.0.0.1",6379);
		Jedis jedis_slave = new Jedis("127.0.0.1",6380);
		
		//设置6379服务器为主节点,使得6380为从节点
		jedis_slave.slaveof("127.0.0.1", 6379);
		
		//主节点写数据
		jedis_master.set("userName", "liuhefei");
		jedis_master.set("age", "24");
		
		//从节点读数据
		String userName = jedis_slave.get("userName");
		String age = jedis_slave.get("age");
		
		System.out.println("userName:"+userName+" ,age: " + age);

	}
}
