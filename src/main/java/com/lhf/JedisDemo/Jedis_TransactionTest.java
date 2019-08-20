package com.lhf.JedisDemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Jedis事务
 * 
 * 
 * @author liuhefei
 * 2018年9月16日
 */
public class Jedis_TransactionTest {

	public static void main(String[] args) {
		//创建Jedis实例，连接Redis本地服务
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("开启Redis事务");
		
		//1.使用MULTI命令开启事务
		Transaction transaction = jedis.multi();
		
		//2.事务命令入队
		transaction.set("userName", "liuhefei"); //设置键userName
		transaction.set("age", "24");  //设置键age
		transaction.set("city", "shenzhen");  //设置键city
		transaction.get("userName");  //获取键userName的值
		//将userName键所存储的值加上增量5，将会报错，事务执行失败
		//原因是：值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
		transaction.incrBy("userName", 5);  
		//将age键所存储的值加上增量5，事务正确执行
		transaction.incrBy("age", 5);
		
		//3.使用EXEC命令执行事务
		transaction.exec();
		//取消执行事务
		//transaction.discard();
		System.out.println("Redis事务执行结束");
		
		//获取事务中的值
		System.out.println("用户名："+jedis.get("userName"));
		System.out.println("年龄："+jedis.get("age"));
		System.out.println("所在城市："+jedis.get("city"));

	}
}
