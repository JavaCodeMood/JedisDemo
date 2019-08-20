package com.lhf.dataType;

import redis.clients.jedis.Jedis;

/**
 * Java操作Redis 字符串类型
 * 
 * @author liuhefei
 * 2018年9月17日
 */
public class RedisString {
	
	public static Jedis getJedis(){
		//连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1",6379);
		System.out.println("redis服务器连接成功！");
		return jedis;
	}
	
	/**
	 * Redis的key类型
	 */
	public void redisKey(){
		Jedis jedis = getJedis();
		jedis.set("mykey", "redis data type");
		System.out.println("查看键为mykey的是否存在："+jedis.exists("mykey"));
		System.out.println("键mykey的值为："+jedis.get("mykey"));
		System.out.println("查看键为mykey的类型："+jedis.type("mykey"));
		System.out.println("随机获得一个key:"+jedis.randomKey());
		System.out.println("将mykey重命名为mykey1:"+ jedis.rename("mykey", "mykey1"));
		System.out.println("删除key为mykey:"+jedis.del("mykey"));
	}
	
	/**
	 * Redis的String类型
	 */
	public void redisString(){
		Jedis jedis = getJedis(); 
        System.out.println("设置name："+jedis.set("name", "小花"));  
        System.out.println("设置name1："+jedis.set("name1", "小花1"));  
        System.out.println("设置name2："+jedis.set("name2", "小花2"));  
        System.out.println("设置name，如果存在返回0："+jedis.setnx("name", "小花哈哈"));  
        System.out.println("获取key为name和name1的value值："+jedis.mget("name","name1"));  
        System.out.println("自增1："+jedis.incr("index"));  
        System.out.println("自增1："+jedis.incr("index"));  
        System.out.println("自增2："+jedis.incrBy("count", 2));  
        System.out.println("自增2："+jedis.incrBy("count", 2));  
        System.out.println("递减1："+jedis.decr("count"));  
        System.out.println("递减2："+jedis.decrBy("index",2));  
        System.out.println("在name后面添加String："+jedis.append("name", ",我爱你"));  
        System.out.println("获取key为name的值："+jedis.get("name"));  
	}
	
	public static void main(String[] args) {
		RedisString redis = new RedisString();
		redis.redisKey();
		redis.redisString();
	}

}
