package com.lhf.JedisDemo;

import redis.clients.jedis.Jedis;

/**
 * Java客户端Jedis连接Redis数据库
 * 
 * 
 * @author liuhefei
 * 2018年9月16日
 */
public class JedisTest {

	public static void main(String[] args) {
		//创建Jedis实例，连接本地Redis服务
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("连接成功");
	    //查看服务是否运行
	    System.out.println("服务正在运行: "+jedis.ping());
	}

}
