package com.lhf.JedisDemo;

import redis.clients.jedis.Jedis;

/**
 * Jedis API操作实例
 * 
 * 
 * @author liuhefei
 * 2018年9月16日
 */
public class JedisAPITest {
	public static void main(String[] args) {
		//创建Jedis实例，连接Redis本地服务
		Jedis jedis = new Jedis("127.0.0.1",6379);
		//设置Redis数据库的密码
		//System.out.println(jedis.auth("123456"));
		//获取客户端信息
		System.out.println(jedis.getClient());
		//清空Redis数据库,相当于执行FLUSHALL命令
		System.out.println(jedis.flushAll());
		//查看Redis信息，相当于执行INFO命令
		System.out.println(jedis.info());
		//获取数据库中key的数量，相当于执行DBSIZE命令
		System.out.println(jedis.dbSize());
		//获取数据库名字
		System.out.println(jedis.getDB());
		//返回当前Redis服务器的时间，相当于执行TIME命令
		System.out.println(jedis.time());
	}
}
